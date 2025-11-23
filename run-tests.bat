@echo off
echo ========================================
echo Setting up JUnit and running tests
echo ========================================

REM Create lib directory if it doesn't exist
if not exist lib mkdir lib

REM Download JUnit 5 JARs if they don't exist
if not exist lib\junit-jupiter-api-5.9.3.jar (
    echo Downloading JUnit JARs...
    curl -L -o lib\junit-jupiter-api-5.9.3.jar https://repo1.maven.org/maven2/org/junit/jupiter/junit-jupiter-api/5.9.3/junit-jupiter-api-5.9.3.jar
    curl -L -o lib\junit-jupiter-engine-5.9.3.jar https://repo1.maven.org/maven2/org/junit/jupiter/junit-jupiter-engine/5.9.3/junit-jupiter-engine-5.9.3.jar
    curl -L -o lib\junit-platform-console-standalone-1.9.3.jar https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.9.3/junit-platform-console-standalone-1.9.3.jar
)

echo.
echo ========================================
echo Compiling source code...
echo ========================================
javac -d bin src\*.java
if errorlevel 1 (
    echo Compilation of source failed!
    pause
    exit /b 1
)

echo.
echo ========================================
echo Compiling tests...
echo ========================================
javac -cp "bin;lib\*" -d bin test\*.java
if errorlevel 1 (
    echo Compilation of tests failed!
    pause
    exit /b 1
)

echo.
echo ========================================
echo Running tests...
echo ========================================
java -jar lib\junit-platform-console-standalone-1.9.3.jar --class-path bin --scan-class-path

echo.
echo ========================================
echo Tests completed!
echo ========================================
pause
