# DAQ HAT Java library for Raspberry Pi

Currently it is only a PoC of wrapping C++ code with JNI for interaction with MCC134 Hats.

## On Raspberry PI

### Installation/Uninstallation

Installation of this library is included as an optional step in the root `install.sh`.  
It can be also initiated manually, by executing

```bash
sudo ./install.sh
```

from the current directory (`${workspaceFolder}/daqhats-java`).  
After this step, JNI native shared library is installed under `/usr/local/lib` on your Raspberry PI and Java API library is installed to your PI's local Maven repository.  

Correspondingly, Java library uninstallation is performed by

```bash
sudo ./uninstall.sh
```

### Running examples

Java examples are located under `${workspaceFolder}/examples/java` directory. Each class can be launched or debugged from VSCode.

## On development PC

### Install Maven dependency to local Maven repo

Before you can use the Java library API in your own project it has to be installed locally as a Maven dependency **on the computer you write code**.  
Execute the following from the current directory (`${workspaceFolder}/daqhats-java`):  

```bash
mvn clean install
```

### Add Maven dependency

To use the library in your local Java Maven project, just add the following dependency to the pom.xml:

```xml
    <dependency>
      <groupId>com.github.hpopov.daqhats</groupId>
      <artifactId>daqhats-java</artifactId>
      <version>1.0</version>
    </dependency>
```

## Information for library developers

### Playing around with JNI on Windows

To run JNI demo project on your Windows machine, it has to be compiled first:

1. From bash, execute `make -C jni all-win-debug` -- to compile demo C++ JNI implementation and bundle it into Windows DLL shared library
2. Launch `com.github.hpopov.daqhats.test.JavaServer` it from VSCode by clicking Run/Debug or pressing F5

### Versioning

Native library is not versioned unless breaking changes are introduced. In this case, native lib versioning should follow the version in POM, so that whenever a new version of java library is installed with `mvn install`, it doesn't affect existing native libraries, so that programs, relying on older java library version (before breaking changes in native lib) may still work.
