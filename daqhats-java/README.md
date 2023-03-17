# DAQ HAT Java library for Raspberry Pi

Currently it is onlt a PoC of wrapping C++ code with Java using JNI.

## Trying out an example

To build the project, execute the following:

1. `mvn clean package` -- to generate C++ header file compatible with `native` method declared in Java and assemble Jar
2. `make -C jni all-windows` -- to compile C++ JNI implementation and bundle it into Windows DLL shared library. For Linux OS use `make -C jni all-linux`

To run the project, execute `java -Djava.library.path=./jni -classpath ./target/daqhats-java-1.0-SNAPSHOT.jar:. com.github.hpopov.daqhats.JavaServer`.

## TO DO

1. Log in Obsidian all the work/research performed to create this sample
2. Consider adding tests? Not sure, though, how to implement them for integrating with Raspbian native lib
3. Implement API discussed with Simon for MCC134.
4. Integrate this project into the daqhats installation/uninstallation scripts
