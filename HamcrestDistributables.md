# Introduction #
Hamcrest consists of different jars matching the different needs of applications. This document describes these distributables and the functionality contained in each of them.


# Overview of distributables #

## hamcrest-core.jar ##
This is the core API to be used by third-party framework providers. This includes a foundation set of matcher implementations for common operations.
This API is stable and will rarely change. You will need
this library as a minimum.

## hamcrest-library.jar ##
The ever-growing library of Matcher implementations which are based on the core functionality in hamcrest-core.jar.
This will grow between releases.

## hamcrest-generator.jar ##
A tool to allow many Matcher implementations to be combined into a single class with static methods returning the different matchers so users don't have to remember many classes/packages to import. Generates code. This library is only used internally at compile time. It is not necessary for the use of any of the other hamcrest libraries at runtime.

## hamcrest-integration.jar ##
Provides integration between Hamcrest and other testing tools, including JUnit (3 and 4), TestNG, jMock and EasyMock.
Uses hamcrest-core.jar and hamcrest-library.jar.

## hamcrest-all.jar ##
One jar containing all classes of all the other jars.

# Maven-Versions #
The description above also applies to the hamcrest Maven artifacts. The dependencies of the jars (hamcrest-integration uses hamcrest-library which uses hamcrest-core) is represented by the Maven dependency mechanism.
There is no hamcrest-all library in the Maven repo. Just use hamcrest-integration which references all the other hamcrest libraries.