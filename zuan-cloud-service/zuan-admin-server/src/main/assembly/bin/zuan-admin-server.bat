@echo OFF
title Discovery Service
set RUNDIR=%~dp0
cd %RUNDIR%\..
%JAVA_HOME%\bin\java -Djava.net.preferIPv4Stack=true -DPIDFILE=bin\zuan-admin-server.pid -Dspring.profiles.active=prodcution -jar lib\zuan-admin-server.jar