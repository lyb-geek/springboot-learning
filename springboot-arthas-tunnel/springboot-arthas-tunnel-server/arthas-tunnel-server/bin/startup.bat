@echo off
if not exist "%JAVA_HOME%\bin\java.exe" echo Please set the JAVA_HOME variable in your environment, We need java(x64)! jdk8 or later is better! & EXIT /B 1
set "JAVA=%JAVA_HOME%\bin\java.exe"

set BASE_DIR=%~dp0
rem added double quotation marks to avoid the issue caused by the folder names containing spaces.
rem removed the last 5 chars(which means \bin\) to get the base DIR.
set BASE_DIR=%BASE_DIR:~0,-5%
set CUSTOM_SEARCH_LOCATIONS=file:%BASE_DIR%/conf/
set SERVER=arthas-tunnel-server



 
set ARTHAS_TUNNEL_SERVER_JVM_OPTS=-Xms512m -Xmx512m -Xmn256m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=%BASE_DIR%\logs\java_heapdump.hprof
set ARTHAS_TUNNEL_SERVER_OPTS=%ARTHAS_TUNNEL_SERVER_OPTS% -Dtunnelserver.home=%BASE_DIR%
set ARTHAS_TUNNEL_SERVER_OPTS=%ARTHAS_TUNNEL_SERVER_OPTS% -jar %BASE_DIR%\target\%SERVER%.jar
set ARTHAS_TUNNEL_SERVER_CONFIG_OPTS=--spring.config.additional-location=%CUSTOM_SEARCH_LOCATIONS%
set ARTHAS_TUNNEL_SERVER_LOG4J_OPTS=--logging.config=%BASE_DIR%/conf/logback-spring.xml


set COMMAND=%JAVA% %ARTHAS_TUNNEL_SERVER_JVM_OPTS% %ARTHAS_TUNNEL_SERVER_OPTS% %ARTHAS_TUNNEL_SERVER_ARGS_OPTS% %ARTHAS_TUNNEL_SERVER_CONFIG_OPTS% %ARTHAS_TUNNEL_SERVER_LOG4J_OPTS% 
call %COMMAND%

pause

