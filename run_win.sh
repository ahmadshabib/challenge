#!/bin/bash

JARFILE=./shopping-cart-app/target/shopping-cart-app-1.0-SNAPSHOT.jar

# Build the application and generate the executable
if  mvn -v | egrep "\S+\s+Maven"; then
        mvn -B -T 4C clean install -DskipTests
    else
        echo "Maven is not installed, please make sure to setup maven"
fi

# Check if java exists
if java -version 2>&1 >/dev/null | egrep "\S+\s+version"; then
    if [ -f $JARFILE ] ; then
        java -jar $JARFILE
    else
        echo "File was not found"
    fi
    else
        echo  "Java is not installed please install then run again"
fi
wait