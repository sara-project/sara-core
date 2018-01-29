echo "Starting copying of plugins: "

del ./plugins/*.jar

for %%i in (dir ../ |grep -i plugin) do(
    $(cp ../$i/dist/*.jar ./plugins)
    if [ $? -eq 0 ]
        then
        echo ../$i " done"
    else
         echo ../$i " fail"
    fi
)

for %%i in (dir ../../ |grep -i plugin) do (
    $(xcopy ../../$i/dist/*.jar ./plugins)
    if [ $? -eq 0 ]
        then
        echo ../$i " done"
    else
         echo ../$i " fail"
    fi
)  
