# DAQ HAT Java library for Raspberry Pi

Currently it is only a PoC of wrapping C++ code with JNI for interaction with MCC134 Hats.

## Usage

### Installation

Installation of this library is included as an optional step in the root `./install.sh`.
It can be also initiated manually, by executing in bush `sudo ./install.sh` from the current directory (`${workspaceFolder}/daqhats-java`).  
Correspondingly, Java library uninstallation is performed by `sudo ./uninstall.sh`.

### Adding library as a Maven dependency

After the installation step, both JNI native shared library is installed under `usr/local/lib` and the Java library API is installed locally as a Maven dependency (to `~/.m2/repository`).
To use it in your local Java Maven project, just add the following dependency to the pom.xml:

```xml
    <dependency>
      <groupId>com.github.hpopov.daqhats</groupId>
      <artifactId>daqhats-java</artifactId>
      <version>1.0</version>
    </dependency>
```

### Playing around with JNI on Windows

To run JNI demo project, it has to be compiled first:

1. From bash, execute `make -C jni all-win-debug` -- to compile demo C++ JNI implementation and bundle it into Windows DLL shared library
2. Launch `com.github.hpopov.daqhats.test.JavaServer` it from VSCode by clicking Run/Debug or pressing F5

## Development process

Native library is not versioned unless breaking changes are introduced. In this case, native lib versioning should follow the version in POM, so that whenever a new version of java library is installed with `mvn install`, it doesn't affect existing native libraries, so that programs, relying on older java library version (before breaking changes in native lib) may still work.

## TO DO

Move JNI native library out of Pom.xml (to ./install.sh) and reorganize this README. Maybe even exclude mvn install from the ./install.sh? Since it is not mandatory to have Maven installed on Pi to install this native library.
