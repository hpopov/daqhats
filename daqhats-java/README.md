# DAQ HAT Java library for Raspberry Pi

Currently it is only a PoC of wrapping C++ code with JNI for interaction with MCC134 Hats.

## Trying out an example

To build the project, execute the following from the current directory (`${workspaceFolder}/daqhats-java`):

1. `mvn clean compile` -- to generate C++ header file compatible with `native` method declared in Java
2. `make -C jni all-win-debug` -- to (re-)compile demo C++ JNI implementation and bundle it into Windows DLL shared library. For the real library bundling for RaspberryPI use `make -C jni all`

To run JNI demo project, launch `com.github.hpopov.daqhats.test.JavaServer` it from VSCode by clicking Run/Debug or pressing F5

## TO DO

1. Integrate this project into the daqhats installation/uninstallation scripts
