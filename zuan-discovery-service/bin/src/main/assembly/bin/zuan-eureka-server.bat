@echo OFF
title Eureka Server
set RUNDIR=%~dp0
cd %RUNDIR%\..
%JAVA_HOME%\bin\java -Djava.net.preferIPv4Stack=true -DPIDFILE=bin\zuan-eureka-server.pid -Dspring.profiles.active=local -jar lib\zuan-eureka-server.jar