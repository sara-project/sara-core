#!/bin/bash

echo "Download libs: "

#Download Lib Json Simple
if [ $(ls ./libs |grep -i json-simple | wc -l) -eq 0 ]
    then
    wget "http://central.maven.org/maven2/com/googlecode/json-simple/json-simple/1.1.1/json-simple-1.1.1.jar" -P ./libs
fi
