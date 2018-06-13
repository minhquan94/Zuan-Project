@echo OFF
title Gateway Service
set RUNDIR=%~dp0
cd %RUNDIR%\..
%JAVA_HOME%\bin\java -Djava.net.preferIPv4Stack=true -DPIDFILE=bin\zuan-gateway-service.pid -jar lib\zuan-gateway-service.jar