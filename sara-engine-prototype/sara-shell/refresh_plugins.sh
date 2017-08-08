#!/bin/bash

echo "Starting copying of plugins: "

for i in $(ls ../ |grep -i plugin);
do
    $(cp ../$i/dist/*.jar ./plugins)
    if [ $? -eq 0 ]
        then
        echo ../$i " done"
    else
         echo ../$i " fail"
    fi
done

for i in $(ls ../../ |grep -i plugin);
do
    $(cp ../../$i/dist/*.jar ./plugins)
    if [ $? -eq 0 ]
        then
        echo ../$i " done"
    else
         echo ../$i " fail"
    fi
done  
