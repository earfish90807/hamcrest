## JValidation ##

JValidations is a framework to express and exercise validation rules for java objects. Its defining characteristics are: validation is performed by the objects themselves, not in external "validator" classes, preserving encapsulation; validation rules are expressed in declarative java using Hamcrest matchers, not in XML or annotations or what have you; how a validation failure is handled is entirely up to the caller through the use of callback interfaces; extensible in that custom validations can be coded, and the DSL syntax can be modified to suit your needs

  * Project: http://jvalidations.codehaus.org/
  * Tutorial: http://jvalidations.codehaus.org/tutorial/TwoMinuteTutorial.html

## Window Licker ##

Window Licker is a framework for the test-driven development of systems with Java Swing rich clients or AJAX web GUIs.  It uses Hamcrest matchers to describe the expected state of the user interface.

  * Project: http://code.google.com/p/windowlicker

## Processing Collections ##

LambdaJ aims to partially eliminate the burden of writing (often nested and poorly readable) loops for iterating over collections. In particular it allows to iterate collections in order to filter items, convert items, extract a given property from each item, group or index the items on the value of one or more properties, invoke a method on each item, aggregate items or the values of one of their properties, and more!  It uses reflection to provide an extremely natural way of refering to object properties.
  * Project: http://code.google.com/p/lambdaj/
  * Getting Started: http://code.google.com/p/lambdaj/
  * Javadocs: http://lambdaj.googlecode.com/svn/trunk/html/apidocs/index.html

Sam Newman's Hamcrest Collections project uses Hamcrest to implement features such as `select`, `reject`, `map`, `reduce` and `zip` familiar from languages like Ruby and Python.
  * Project: http://code.google.com/p/hamcrest-collections/
  * Getting Started docs: http://code.google.com/p/hamcrest-collections/wiki/GettingStarted


Gareth Davis has implemented a similar collections API which also uses reflection to provide an extremely natural way of filtering by object properties:

```
smiths = select(people, where(Person.class).getLastName(),  equalToIgnoringCase("smith"));
```

  * Project: http://code.google.com/p/logicalpractice-collections/
  * Getting Started docs: http://code.google.com/p/logicalpractice-collections/wiki/GettingStarted


Both were inspired by an Håkan Råberg's article about using Hamcrest with iterators.

  * Blog post: http://jroller.com/page/ghettoJedi?entry=using_hamcrest_for_iterators

## Unit tests: assertThat() and assumeThat() in JUnit ##

As of JUnit 4.4, Hamcrest now comes with JUnit, along with `assertThat()` and `assumeThat()` methods.

```
assertThat(someList, containsItemWith(stringContaining("stuff"));
```

  * Release note: http://junit.sourceforge.net/doc/ReleaseNotes4.4.html

## Mock Objects ##

Hamcrest was originally spawned from the Constraints code in jMock1 (which came from the Predicates code in DynaMock. The latest version of jMock (jMock2) now uses Hamcrest directly to allow for precise expectations of arguments to be specified.

```
// Expect one call to mockThing.doSomething(String) with a string containing "hello". 
oneOf (mockThing).doSomething(with(stringContaining("hello")));

// Expect mockThing.doSomething(String) to never be called with a null string.
never (mockThing).doSomething(with(aNull(String.class)));
```

  * Project: http://jmock.org
  * Matcher docs: http://jmock.org/matchers.html


The Objective-C port of Hamcrest is used by OCMock:
  * Project: http://www.mulle-kybernetik.com/software/OCMock/
  * Example: http://svn.mulle-kybernetik.com/OCMock/trunk/Source/OCMockObjectHamcrestTests.mm


Mockito is a simplistic mock objects library for Java that uses Hamcrest matchers:
  * Project: http://code.google.com/p/mockito/


## Record-based Text File Processing with GetInLine ##

GetInLine is a simple embedded Java DSL for writing applications that process record-based type files.

  * Project: http://sourceforge.net/projects/getinline
  * Blog post: http://www.cocking.co.uk/blog/2006/10/dsl-for-file-processing-getinline.html

## Behavior Driven Development with JDave ##

Dave is a BDD framework for Java. It is inspired by rspec and integrates jMock2 as mocking framework and Hamcrest as matching library.

Expectations are set using specify() method.

```
specify(persons.get(0), is(Person.class));
specify(persons, where(new Each<Person>() {{ matches(item.getAge(), is(greaterThan(30))); }}));
```

  * Project: http://www.jdave.org/
  * Example: http://svn.laughingpanda.org/svn/jdave/trunk/jdave-examples/src/test/jdave/examples/HamcrestSampleSpec.java

## Web Testing ##

Robert Chatley has been combining Hamcrest with [WebDriver](http://code.google.com/p/webdriver/) in a [LiFT](https://lift.dev.java.net/)-like way to do web-testing.

```
public void testHasLotsOfLinks() throws Exception {
  goTo("http://some/url");
   
  assertPresenceOf(greaterThan(15), links());
  assertPresenceOf(atLeast(1), link().with(text(equalTo("Sign in"))));

  clickOn(link().with(text(equalTo("Sign in"))));

  assertPresenceOf(exactly(1), title().with(text(equalTo("Sign in page"))));		
}
```

## Babble ##

Nat Pryce has used Hamcrest to represent event subscription and advertisment filters in [Babble](http://www.doc.ic.ac.uk/~np2/babble.html), an event bus with subscription-based routing and dynamic federation.

```
session.subscribe(all(
    events(withAny(REQUEST),
           withAny(OWNER),
           with(LEASE,   greaterThanOrEqualTo(0))),
    events(withAny(RESIGN),
           withAny(OWNER),
           with(LEASE,   greaterThanOrEqualTo(0)))));
```

  * Project: http://www.doc.ic.ac.uk/~np2/babble.html


## Other Ideas ##

These are other uses of Hamcrest we've heard of in passing.

  * Robert Chatley has been using matchers to implement filters in logging infrastructure.
  * A team in an investment bank using Hamcrest to filter data in GUI tables.

Doing something else with Hamcrest? [Let us know!](http://groups.google.com/group/hamcrest-java)
