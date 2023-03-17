# DAQ HAT Java library for Raspberry Pi

Currently it is onlt a PoC of wrapping C++ code with Java using JNI.

## Trying out an example

To build the project, execute the following:

1. `mvn clean package` -- to generate C++ header file compatible with `native` method declared in Java (and assemble Jar)
2. `make -C jni all-win-debug` -- to (re-)compile Test C++ JNI implementation and bundle it into Windows DLL shared library. For full library bundling for RaspberryPI use `make -C jni all`

To run the project, launch it from VSCode or execute `java -Djava.library.path=./jni -classpath ./target/daqhats-java-1.0-SNAPSHOT.jar:. com.github.hpopov.daqhats.test.JavaServer` from the `daqhats-jni` folder.

## TO DO

1. Consider adding tests? Not sure, though, how to implement them for integrating with Raspbian native lib
2. Implement API discussed with Simon for MCC134.
3. Integrate this project into the daqhats installation/uninstallation scripts
