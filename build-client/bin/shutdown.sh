#!/bin/bash

cd `dirname $0`/../target
target_dir=`pwd`

pid=`ps ax | grep -i 'mapping.client' | grep ${target_dir} | grep java | grep -v grep | awk '{print $1}'`
if [ -z "$pid" ] ; then
        echo "No mappingClient running."
        exit -1;
fi

echo "The mappingClient(${pid}) is running..."

kill ${pid}

echo "Send shutdown request to mappingClient(${pid}) OK."
