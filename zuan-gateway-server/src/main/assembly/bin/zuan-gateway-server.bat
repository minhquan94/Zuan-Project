@echo OFF
title Gateway Server
set RUNDIR=%~dp0
cd %RUNDIR%\..
%JAVA_HOME%\bin\java -Djava.net.preferIPv4Stack=true -DPIDFILE=bin\zuan-gateway-server.pid -jar lib\zuan-gateway-server.jar