2STREAM
=======

2STREAM is a application that allows you to live stream video. It is still in alpha but please don't hesitate to test it.

Jenskins build status: [![Build Status](http://jenkins.marlinc.nl/job/2STREAM/badge/icon)](http://jenkins.marlinc.nl/job/2STREAM/)

You can find our Jenkins Build-Bot job over [here](http://jenkins.marlinc.nl/job/2STREAM/).
We don't have a Travis-CI test-set yet but this may come in the future.

Compiling from the command line
-----------

You can compile 2STREAM from the command line using Ant.
At the moment we have 4 build targets:

* linux-x86
* linux-x86_64
* windows-x86
* windows-x86_64

Building a target is done by simply executing:

```ant linux-x86```

This will automatically clean the build, compile the source and create a jar with all the necessary libraries.



Downloads
-----------

* [Latest Linux x86](http://jenkins.marlinc.nl/job/2STREAM/jdk=OpenJDK%207/lastSuccessfulBuild/artifact/build/2stream-linux-x86.jar)
* [Latest Linux x86_64](http://jenkins.marlinc.nl/job/2STREAM/jdk=OpenJDK%207/lastSuccessfulBuild/artifact/build/2stream-linux-x86_64.jar)
* [Latest Windows x86](http://jenkins.marlinc.nl/job/2STREAM/jdk=OpenJDK%207/lastSuccessfulBuild/artifact/build/2stream-windows-x86.jar)
* [Latest Windows x86_64](http://jenkins.marlinc.nl/job/2STREAM/jdk=OpenJDK%207/lastSuccessfulBuild/artifact/build/2stream-windows-x86_64.jar)

License
-----------

EPL
