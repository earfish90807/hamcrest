# How to make a Hamcrest release #

Assuming you have the following variables set:

```
VERSION=1.0         <-- CHANGE ME
PROJECT=hamcrest-java
SVNROOT=https://hamcrest.googlecode.com/svn
```

Note: if using Windows, replace $X with %X% in the scripts below.

You also need to be in the hamcrest-java directory.

## Tag the release in Subversion ##

Update CHANGES.txt with today's date.

```
svn copy -m "Tagged $VERSION" $SVNROOT/trunk/$PROJECT $SVNROOT/tags/$PROJECT-$VERSION
```


## Perform a clean build ##

```
svn switch $SVNROOT/tags/$PROJECT-$VERSION
ant -Dversion=$VERSION
```

Check that the build was successful!

## Verify the distribution ##

```
mkdir tmp
cd tmp
tar xvzf ../build/hamcrest-$VERSION.tgz
cd hamcrest-$VERSION
ant
```

Check that the build was successful!

## Upload to Google Code ##

The following files should be uploaded from the build directory to Google Code:
```
hamcrest-$VERSION.tgz
hamcrest-$VERSION.zip
hamcrest-all-$VERSION.jar
hamcrest-core-$VERSION.jar
hamcrest-library-$VERSION.jar
hamcrest-integration-$VERSION.jar
hamcrest-generator-$VERSION.jar
```

Add 'Featured' tag to hamcrest.tgz, hamcrest.zip, hamcrest-all.jar and hamcrest-core.jar.

Remove 'Featured' tag from previous versions and add 'Deprecated' tag.

## Clean up ##

```
cd ../../
rm -rf tmp
rm -rf build
svn switch $SVNROOT/trunk/$PROJECT
```

## All done! ##

  1. Announce to the world.
  1. Go to pub.