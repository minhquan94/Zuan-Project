#!/bin/bash
#
# Cloud Config Service Daemon
#
# chkconfig: 345 97 03
# description: Cloud Config Service
#
# processname: Cloud Config Service
#

SERVICE_NAME=zuan-admin-server
JAR_NAME=zuan-admin-server.jar
maxShutdownTime=10
args=${@:2}
vpid=

# resolve links - $0 may be a softlink
PRG="$0"

while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '.*/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

# Get standard environment variables
PRGDIR=`dirname "$PRG"`
HOMEPATH=`cd "$PRGDIR/.." ; pwd`

if [ -r "$HOMEPATH/bin/setenv.sh" ]; then
  . "$HOMEPATH/bin/setenv.sh"
fi

#check the exist of $JAVA_HOME
if test "x$JAVA_HOME" = "x"; then
  echo "JAVA_HOME is not defined."
  exit 1
fi;

APP_PARAM="-D$SERVICE_NAME.home=$HOMEPATH -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=logs -Djava.net.preferIPv4Stack=true -Djava.security.egd=file:/dev/./urandom -DPIDFILE=bin/$SERVICE_NAME.pid"

# Check the application status
#
# This function checks if the application is running
check_status() {

  # Running ps with some arguments to check if the PID exists
  # -C : specifies the command name
  # -o : determines how columns must be displayed
  # h : hides the data header
  vpid=`pgrep -f $SERVICE_NAME.home=$HOMEPATH`
}

# Starts the application
start() {

  # At first checks if the application is already started calling the check_status
  # function
  check_status

  if test "s$vpid" != "s" ; then
    echo "Application is already started."
    exit 1
  fi

  # If the application isn't running, starts it
  echo -n "Starting application: "

  if test "s$ZUAN_OPTS" != "s" ; then
    APP_PARAM="$APP_PARAM $ZUAN_OPTS"
  fi

  # Redirects default and error output to a log file
  cd $PRGDIR/..
  $JAVA_HOME/bin/java $APP_PARAM -jar lib/$JAR_NAME $args > logs/$SERVICE_NAME.out 2>&1 &
  echo "OK"
}

# Stops the application
stop() {

  # Like as the start function, checks the application status
  check_status

  if test "s$vpid" = "s" ; then
    echo "Application is not running."
  else
    # Kills the application process
    echo -n "Stopping application: "
    kill -15 $vpid
    sleep $maxShutdownTime

    if ps -p $vpid > /dev/null ; then
      echo "application is still existing. Force to stop."
      kill -9 $vpid
    else
      echo "OK"
    fi
  fi
}

# Show the application status
status() {

  # The check_status function, again...
  check_status

  # If the PID was returned means the application is running
  if test "s$vpid" = "s"; then
    echo "Application is not running."
  else
    echo "Application is running."
  fi;
}

# Main logic, a simple case to call functions
case "$1" in
  start)
    start
    exit 0
    ;;
  stop)
    stop
    exit 0
    ;;
  status)
    status
    exit 0
    ;;
  restart|reload)
    stop
    start
    exit 0
    ;;
  *)
    echo "Usage: $0 {start|stop|restart|reload|status}"
    exit 1
esac