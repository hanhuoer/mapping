@echo off

if not exist "%JAVA_HOME%\bin\java.exe" echo Please set the JAVA_HOME variable in your environment, We need java(x64)! jdk8 or later is better! & EXIT /B 1
set "JAVA=%JAVA_HOME%\bin\java.exe"

setlocal enabledelayedexpansion

set BASE_DIR=%~dp0
rem added double quotation marks to avoid the issue caused by the folder names containing spaces.
rem removed the last 5 chars(which means \bin\) to get the base DIR.
set BASE_DIR="%BASE_DIR:~0,-5%"

rem set custom conf
set CUSTOM_SEARCH_LOCATIONS=file:%BASE_DIR%/conf/

rem set mapping options
set SERVER=mapping-client
set "MAPPING_OPTS=%MAPPING_OPTS% -Dmapping.home=%BASE_DIR%"
set "MAPPING_OPTS=%MAPPING_OPTS% -jar %BASE_DIR%\target\%SERVER%.jar"

rem set mapping jvm options
set "MAPPING_JVM_OPTS=-Xms512m -Xmx512m -Xmn256m"

rem set mapping spring config location
set "MAPPING_CONFIG_OPTS=--spring.config.additional-location=%CUSTOM_SEARCH_LOCATIONS%"

rem set mapping log4j file location
set "MAPPING_LOG4J_OPTS=--logging.config=%BASE_DIR%\conf\mapping-logback.xml"


set COMMAND="%JAVA%" %MAPPING_JVM_OPTS% %MAPPING_OPTS% %MAPPING_CONFIG_OPTS% %MAPPING_LOG4J_OPTS% mapping.client %*

echo %COMMAND%

rem start mapping command
%COMMAND%
