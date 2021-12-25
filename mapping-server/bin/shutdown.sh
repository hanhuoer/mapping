#!/bin/bash

cd `dirname $0`/../target
target_dir=`pwd`

pid=`ps ax | grep -i 'mapping.server' | grep ${target_dir} | grep java | grep -v grep | awk '{print $1}'`
if [ -z "$pid" ] ; then
        echo "No mappingServer running."
        exit -1;
fi

echo "The mappingServer(${pid}) is running..."

kill ${pid}

echo "Send shutdown request to mappingServer(${pid}) OK."
