#!/bin/bash

if [ "$(id -u)" != "0" ]; then
   echo "This script must be run as root, i.e 'sudo ./install.sh'" 1>&2
   exit 1
fi

echo "Building and installing Java library"
echo
make -C jni all
mvn clean install
make -C jni clean
echo
echo "Java lib installation completed successfully"