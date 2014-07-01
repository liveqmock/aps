@echo off

echo Using JAVA_HOME:        "%JAVA_HOME%"
if not exist "%JAVA_HOME%\bin\javaws.exe" goto end

set "JRE_HOME=%JAVA_HOME%"
set _RUNJAVA="%JRE_HOME%\bin\javaws"
set  TITLE=autoprinter
set _EXECJAVA=start "%TITLE%" %_RUNJAVA%
rem  ****************please change to your real IP ,port, appid. *****************
set _URL=https://127.0.0.1
echo Using URL:              "%_URL%"

%_EXECJAVA% %_URL%/deploy/client.jnlp

:end