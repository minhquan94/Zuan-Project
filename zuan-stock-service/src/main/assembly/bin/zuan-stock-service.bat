@echo OFF
title zuan-stock-service Server
set RUNDIR=%~dp0
cd %RUNDIR%\..
%JAVA_HOME%\bin\java -Djava.net.preferIPv4Stack=true -DPIDFILE=bin\zuan-eureka-server.pid -jar lib\zuan-stock-service.jar