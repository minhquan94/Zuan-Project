@echo OFF
title Discovery Service
set RUNDIR=%~dp0
cd %RUNDIR%\..
%JAVA_HOME%\bin\java -Djava.net.preferIPv4Stack=true -DPIDFILE=bin\zuan-discovery-service.pid -Dspring.profiles.active=local -jar lib\zuan-discovery-service.jar