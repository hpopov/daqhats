#!/bin/bash

if [ "$(id -u)" != "0" ]; then
   echo "This script must be run as root, i.e 'sudo ./install.sh'" 1>&2
   exit 1
fi

# Installing JNI shared library
echo "Building and installing Java library"
echo
make -C jni all
make -C jni install
make -C jni clean
echo

# Installing Java library to local Maven repository
echo "Installing Java API library to be used by java examples"
echo
mvn clean install
echo

echo "Java lib installation completed successfully"