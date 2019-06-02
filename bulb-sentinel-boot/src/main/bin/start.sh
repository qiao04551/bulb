#!/bin/bash
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`
CONF_DIR=$DEPLOY_DIR/conf

PIDS=`ps  --no-heading -C java -f --width 1000 | grep "$DEPLOY_DIR" |awk '{print $2}'`
if [ -n "$PIDS" ]; then
    echo "ERROR: The service already started!"
    echo "PID: $PIDS"
    exit 1
fi

LOGS_DIR=""
if [ -n "$LOGS_FILE" ]; then
	LOGS_DIR=`dirname $LOGS_FILE`
else
	LOGS_DIR=$DEPLOY_DIR/logs
fi
if [ ! -d $LOGS_DIR ]; then
	mkdir $LOGS_DIR
fi
STDOUT_FILE=$LOGS_DIR/stdout.log

LIB_DIR=$DEPLOY_DIR/lib
LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`

JAVA_OPTS=" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true "
JAVA_DEBUG_OPTS=""
JAVA_JMX_OPTS=""
JAVA_MEM_OPTS=" -server -Xmx256m -Xms256m -XX:PermSize=64m -XX:SurvivorRatio=2 -XX:+UseParallelGC "

echo -e "Starting the service ...\c"
nohup java $JAVA_OPTS $JAVA_MEM_OPTS $JAVA_DEBUG_OPTS $JAVA_JMX_OPTS -classpath $CONF_DIR:$LIB_JARS com.maxzuo.AppApplication > $STDOUT_FILE 2>&1 &

COUNT=0
while [ $COUNT -lt 1 ]; do
    echo -e ".\c"
    sleep 1
    COUNT=`ps  --no-heading -C java -f --width 1000 | grep "$DEPLOY_DIR" | awk '{print $2}' | wc -l`
	if [ $COUNT -gt 0 ]; then
		break
	fi
done

echo "OK!"
PIDS=`ps  --no-heading -C java -f --width 1000 | grep "$DEPLOY_DIR" | awk '{print $2}'`
echo "PID: $PIDS"
echo "STDOUT: $STDOUT_FILE"
