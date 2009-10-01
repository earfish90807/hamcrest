package org.hamcrest.generator;

import java.lang.reflect.Method;
import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Reads a list of Hamcrest factory methods from a class, using standard Java reflection.
 * <h3>Usage</h3>
 * <pre>
 * for (FactoryMethod method : new ReflectiveFactoryReader(MyMatchers.class)) {
 *   ...
 * }
 * </pre>
 * <p>All methods matching signature '@Factory public static Matcher<blah> blah(blah)' will be
 * treated as factory methods. To change this behavior, override {@link #isFactoryMethod(Method)}.
 * <p>Caveat: Reflection is hassle-free, but unfortunately cannot expose method parameter names or JavaDoc
 * comments, making the sugar slightly more obscure.
 *
 * @author Joe Walnes
 * @see SugarGenerator
 * @see FactoryMethod
 */
public class ReflectiveFactoryReader implements Iterable<FactoryMethod> {

    private final Class<?> cls;
    private final String target;

    private final ClassLoader classLoader;

    public ReflectiveFactoryReader(Class<?> cls, String target) {
        this.cls = cls;
        this.target = target;
        this.classLoader = cls.getClassLoader();
    }

    public Iterator<FactoryMethod> iterator() {
        return new Iterator<FactoryMethod>() {

            private int currentMethod = -1;
            private Method[] allMethods = cls.getMethods();

            public boolean hasNext() {
                while (true) {
                    currentMethod++;
                    if (currentMethod >= allMethods.length) {
                        return false;
                    } else if (isFactoryMethod(allMethods[currentMethod])) {
                        return true;
                    } // else carry on looping and try the next one.
                }
            }

            public FactoryMethod next() {
                if (currentMethod >= 0 && currentMethod < allMethods.length) {
                    return buildFactoryMethod(allMethods[currentMethod]);
                } else {
                    throw new IllegalStateException("next() called without hasNext() check.");
                }
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

        };
    }

    /**
     * Determine whether a particular method is classified as a matcher factory method.
     * <p/>
     * <p>The rules for determining this are:
     * 1. The method must be public static.
     * 2. It must have a return type of org.hamcrest.Matcher (or something that extends this).
     * 3. It must be marked with the org.hamcrest.Factory annotation.
     * <p/>
     * <p>To use another set of rules, override this method.
     */
    @SuppressWarnings({"unchecked"})
    protected boolean isFactoryMethod(Method javaMethod) {
        // We dynamically load these classes, to avoid a compile time
        // dependency on org.hamcrest.{Factory,Matcher}. This gets around
        // a circular bootstrap issue (because generator is required to
        // compile core).
        Class factoryCls;
        Class matcherCls;
        Method excludeMet;
        try {
            factoryCls = classLoader.loadClass("org.hamcrest.Factory");
            matcherCls = classLoader.loadClass("org.hamcrest.Matcher");
            excludeMet = factoryCls.getMethod("excludes");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot load hamcrest core", e);
        } catch (SecurityException e) {
            throw new RuntimeException("Cannot load hamcrest core", e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Cannot load hamcrest core", e);       
        }
        Annotation annot;
        return isStatic(javaMethod.getModifiers())
                && isPublic(javaMethod.getModifiers())
                && (annot = javaMethod.getAnnotation(factoryCls)) != null
                && checkExcludes(excludeMet, annot)
                && matcherCls.isAssignableFrom(javaMethod.getReturnType());
    }
    
    private boolean checkExcludes(Method m, Annotation annot) {
        try {
            String[] excludes = (String[]) m.invoke(annot);
            return excludes == null || !Arrays.asList(excludes).contains(target);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Cannot load hamcrest core", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot load hamcrest core", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Cannot load hamcrest core", e);
        }
    }

    private FactoryMethod buildFactoryMethod(Method javaMethod) {
        FactoryMethod result = new FactoryMethod(
                javaMethod.getDeclaringClass().getName(),
                javaMethod.getName(), 
                javaMethod.getReturnType().getName());

        for (TypeVariable<Method> typeVariable : javaMethod.getTypeParameters()) {
            boolean hasBound = false;
            StringBuilder s = new StringBuilder(typeVariable.getName());
            for (Type bound : typeVariable.getBounds()) {
                if (bound != Object.class) {
                    if (hasBound) {
                        s.append(" & ");
                    } else {
                        s.append(" extends ");
                        hasBound = true;
                    }
                    s.append(typeToString(bound));
                }
            }
            result.addGenericTypeParameter(s.toString());
        }
        Type returnType = javaMethod.getGenericReturnType();
        if (returnType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) returnType;
            Type generifiedType = parameterizedType.getActualTypeArguments()[0];
            result.setGenerifiedType(typeToString(generifiedType));
        }

        int paramNumber = 0;
        for (Type paramType : javaMethod.getGenericParameterTypes()) {
            String type = typeToString(paramType);
            // Special case for var args methods.... String[] -> String...
            if (javaMethod.isVarArgs()
                    && paramNumber == javaMethod.getParameterTypes().length - 1) {
                type = type.replaceFirst("\\[\\]$", "...");
            }
            result.addParameter(type, "param" + (++paramNumber));
        }

        for (Class<?> exception : javaMethod.getExceptionTypes()) {
            result.addException(typeToString(exception));
        }

        return result;
    }

    /*
     * Get String representation of Type (e.g. java.lang.String or Map&lt;Stuff,? extends Cheese&gt;).
     * <p/>
     * Annoyingly this method wouldn't be needed if java.lang.reflect.Type.toString() behaved consistently
     * across implementations. Rock on Liskov.
     */
    private static String typeToString(Type type) {
        return type instanceof Class<?> ?  classToString((Class<?>) type): type.toString();
    }

    private static String classToString(Class<?> cls) {
      return cls.isArray() ? cls.getComponentType().getName() + "[]" : cls.getName();
    }

}