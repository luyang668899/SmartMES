@echo off

REM qcadoo MES Build Script for Windows
REM Supports Windows, Linux, and macOS

echo ================================
echo qcadoo MES Build Script (Windows)
echo ================================

REM Check Java version
echo Checking Java version...
java -version

REM Check Maven version
echo Checking Maven version...
mvn -version

echo ================================
echo Build Options:
echo 1. Build with tests
echo 2. Build without tests
echo 3. Build and package for release
echo 4. Clean project
echo 5. Build with dependency fixes
echo ================================

set /p choice=Enter your choice: 

echo ================================

if "%choice%"=="1" (
    echo Building with tests...
    mvn clean install
) else if "%choice%"=="2" (
    echo Building without tests...
    mvn clean install -DskipTests
) else if "%choice%"=="3" (
    echo Building and packaging for release...
    set /p version=Enter release version (e.g., 1.5.0): 
    
    echo Building release version %version%...
    mvn clean install -DskipTests
    
    echo Creating release package...
    mkdir target\release 2>nul
    copy mes-application\target\mes-application.war target\release\ 2>nul
    copy README.md target\release\ 2>nul
    copy LICENSE.txt target\release\ 2>nul
    copy build.sh target\release\ 2>nul
    copy build.bat target\release\ 2>nul
    
    echo Release package created in target\release
) else if "%choice%"=="4" (
    echo Cleaning project...
    mvn clean
) else if "%choice%"=="5" (
    echo Building with dependency fixes...
    echo This option will rebuild with fresh dependencies
    
    echo Building with dependency fixes...
    mvn clean install -DskipTests -U
) else (
    echo Invalid choice. Exiting...
    exit /b 1
)

echo ================================
echo Build completed successfully!
echo ================================
