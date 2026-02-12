#!/bin/bash

# qcadoo MES Build Script
# Supports Windows, Linux, and macOS

set -e

echo "================================"
echo "qcadoo MES Build Script"
echo "================================"

# Check Java version
echo "Checking Java version..."
java -version

# Check Maven version
echo "Checking Maven version..."
mvn -version

echo "================================"
echo "Build Options:"
echo "1. Build with tests"
echo "2. Build without tests"
echo "3. Build and package for release"
echo "4. Clean project"
echo "5. Build with dependency fixes"
echo "================================"

read -p "Enter your choice: " choice

echo "================================"

case $choice in
    1)
        echo "Building with tests..."
        mvn clean install
        ;;
    2)
        echo "Building without tests..."
        mvn clean install -DskipTests
        ;;
    3)
        echo "Building and packaging for release..."
        # Update version in pom.xml if needed
        read -p "Enter release version (e.g., 1.5.0): " version
        
        # Replace version in pom.xml
        sed -i "s/<version>1.5-SNAPSHOT<\/version>/<version>$version<\/version>/g" pom.xml
        sed -i "s/<qcadoo.version>\${project.version}<\/qcadoo.version>/<qcadoo.version>$version<\/qcadoo.version>/g" pom.xml
        
        echo "Building release version $version..."
        mvn clean install -DskipTests
        
        # Create release package
        echo "Creating release package..."
        mkdir -p target/release
        cp mes-application/target/mes-application.war target/release/
        cp README.md target/release/
        cp LICENSE.txt target/release/
        cp build.sh target/release/
        cp build.bat target/release/
        
        # Create zip archive
        cd target/release
        zip -r qcadoo-mes-$version.zip .
        cd ../..
        
        echo "Release package created: target/release/qcadoo-mes-$version.zip"
        ;;
    4)
        echo "Cleaning project..."
        mvn clean
        ;;
    5)
        echo "Building with dependency fixes..."
        echo "This option will rebuild with fresh dependencies"
        
        echo "Building with dependency fixes..."
        mvn clean install -DskipTests -U
        ;;
    *)
        echo "Invalid choice. Exiting..."
        exit 1
        ;;
esac

echo "================================"
echo "Build completed successfully!"
echo "================================"
