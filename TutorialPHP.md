# The Hamcrest Tutorial for PHP #

## Introduction ##

Hamcrest is a framework for writing matcher objects, allowing match rules to be defined declaratively. There are a number of situations where matchers are invaluble, such as UI validation, or data filtering, but it is in the area of writing flexible tests that matchers are most commonly used.

When writing tests, it is sometimes difficult to get the balance right between overspecifying the test (and making it brittle to changes), and not specifying enough (making the test less valuable since it continues to pass even when the thing being tested is broken). Having a tool that allows you to pick out precisely the aspect under test and describe the values it should have, to a controlled level of precision, helps greatly in writing tests that are "just right". Such tests fail when the behaviour of the aspect under test deviates from the expected behaviour, yet continue to pass when minor, unrelated changes to the behaviour are made.

## My first Hamcrest test ##

We'll start by writing a very simple PHPUnit test, but instead of using PHPUnit's `assertEquals` or `assertThat` methods, we use Hamcrest's `assertThat` function and its standard set of matchers:

```
<?php
require_once 'PHPUnit/Framework.php';
require_once 'Hamcrest.php';
 
class BiscuitTest extends PHPUnit_Framework_TestCase
{
    public function testEquals()
    {
        $theBiscuit = new Biscuit('Ginger');
        $myBiscuit = new Biscuit('Ginger');
        assertThat($theBiscuit, equalTo($myBiscuit));
    }
}
?>
```

The `assertThat` method is a stylized sentence for making a test assertion. In this example, the subject of the assertion is the object biscuit that is the first method parameter. The second method parameter is a matcher for `Biscuit` objects, here a matcher that checks one object is equal to another.

If you have more than one assertion in your test you can include an identifier for the tested value in the assertion:

```
assertThat('chocolate chips', $theBiscuit->getChocolateChipCount(), equalTo(10));
assertThat('hazelnuts', $theBiscuit->getHazelnutCount(), equalTo(3));
```

## Sugar ##

Hamcrest strives to make your tests as readable as possible. For example, the `is` matcher is a wrapper that doesn't add any extra behavior to the underlying matcher. The following assertions are all equivalent:

```
assertThat($theBiscuit, equalTo($myBiscuit));
assertThat($theBiscuit, is(equalTo($myBiscuit)));
assertThat($theBiscuit, is($myBiscuit));
```

## A tour of common matchers ##

Hamcrest comes with a library of useful matchers. Here are the ones that are already ported to PHP:

  * Core
    * `anything` - always matches, useful if you don't care what the object under test is
    * `describedAs` - decorator to adding custom failure description
    * `is` - decorator to improve readability - see "Sugar", above
  * Logical
    * `allOf` - matches if all matchers match, short circuits (like PHP `&&`)
    * `anyOf` - matches if any matchers match, short circuits (like PHP `||`)
    * `not` - matches if the wrapped matcher doesn't match and vice versa
  * Object
    * `equalTo` - test object equality using the `==` operator
    * `anInstanceOf` - test type
    * `notNullValue`, `nullValue` - test for null
    * `sameInstance` - test object identity using the `===` operator
    * `identicalTo` - alias of `sameInstance`, but with similar semantics to `equalTo`
  * Number
    * `closeTo` - test floating point values are close to a given value
    * `greaterThan`, `greaterThanOrEqualTo`, `lessThan`, `lessThanOrEqualTo` - test ordering
  * Collections
    * `hasItem`, `hasItems` - test if an array contains one or more items
  * Text
    * `equalToIgnoringCase` - test string equality ignoring case
    * `equalToIgnoringWhiteSpace` - test string equality ignoring differences in runs of whitespace
    * `containsString`, `endsWith`, `startsWith` - test string matching
  * Combinations
    * `both` and `either`, for example:
      * `both(startsWith('a'))->andAlso(endsWith('b'))`
      * `either(startsWith('x'))->orElse(endsWith('y'))`

_--Sebastian Bergmann_<br>
<i>--Chris Corbyn</i>