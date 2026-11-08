@echo off
set "MAVEN_PROJECTBASEDIR=%~dp0"
if not "%MAVEN_HOME%"=="" goto runMaven
where mvn >nul 2>nul
if %ERRORLEVEL%==0 goto runMaven
echo Maven is not installed. Install Maven or use your IDE Maven runner.
exit /b 1
:runMaven
mvn %*
