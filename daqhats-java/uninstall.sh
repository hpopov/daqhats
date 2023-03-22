#!/bin/bash

if [ "$(id -u)" != "0" ]; then
   echo "This script must be run as root, i.e 'sudo ./uninstall.sh'" 1>&2
   exit 1
fi

# Remove the JNI shared library
echo "Removing JNI shared library"
echo
make -C jni uninstall
echo