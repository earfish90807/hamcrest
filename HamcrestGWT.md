# Introduction #

So, you've been working on this killer new AJAX web app and - understandably - you decided to use the Google Web Toolkit (GWT). Now, because you are a good programmer, you are writing unit tests for your GWT widgets... Who could imagine writing unit tests without Hamcrest?

Do your widgets use validation? Have you considered using the Hamcrest matchers to match your input?

Are you worried that Hamcrest will not cross compile to JavaScript? Well, it does and it is fairly easy to use the Hamcrest libraries in GWT.

# Details #

Here are a few simple steps to getting Hamcrest into your GWT application:
  1. Download the jars. Download the Hamcrest jars for the core, library and the gwt component (i.e. hamcrest-core-x.y.jar, hamcrest-library-x.y.jar and hamcrest-gwt-x.y.jar)
  1. Put them all in your GWT application's class path
  1. Inherit the "org.hamcrest.Hamcrest" module in your GWT module XML
  1. Match away with your favorite core and library Hamcrest matchers!

Phew, that was easy...