# Jenkins plugin for using the [GVM](http://gvmtool.net/) tool.

Currently provides the functionality to create Groovy/Grails/Gradle installations based on
a local GVM installation.  It will install the latest version, but always uses whatever has
been selected within GVM.

## Assumptions

* gvm is installed in the home directory of the user running the jenkins server.

## TODO:

* Support using a specific versions
* Test on Windows using Cygwin
* Add Griffon Support
* Test with Master/Slave Jenkins installations.