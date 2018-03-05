language = java
build_path = build
mkdir = mkdir -p
src_path = src
shell_path = sara-shell
interfaces_path = sara-interfaces
jar = jar cfe
compiler = javac
interfaces_classpath = :./$(interfaces_path)/$(interfaces_path).jar
json_lib = json-simple-1.1.1.jar
json_lib_url = "http://central.maven.org/maven2/com/googlecode/json-simple/json-simple/1.1.1/$(json_lib)"

clear-all: 
	for folder in $$(ls -d */); do rm -rf $$folder/$(build_path); done

interfaces:
	$(mkdir) ./$(interfaces_path)/$(build_path)
	$(compiler) -d $(interfaces_path)/$(build_path) $$(find $(interfaces_path)/$(src_path) -name *.java)
	$(jar) ./$(interfaces_path)/$(interfaces_path).jar $(interfaces_path)/$(src_path)/* -C $(interfaces_path)/$(build_path)/ .

shell:
	$(mkdir) ./$(shell_path)/$(build_path)
	$(mkdir) ./$(shell_path)/plugins
	rm ./$(shell_path)/plugins/*.jar || true
	$(mkdir) ./$(shell_path)/libs
	wget $(json_lib_url) -P ./$(shell_path)/libs
	$(compiler) -classpath .$(shell_path)/$(interfaces_classpath):./$(shell_path)/libs/$(json_lib) -d $(shell_path)/$(build_path) $$(find $(shell_path)/$(src_path) -name *.java)
	$(jar) ./$(shell_path)/$(shell_path).jar $(shell_path)/$(src_path)/* -C $(shell_path)/$(build_path)/ .


plugins:
	for plugin in $$(ls ./ |grep -i plugin); do \
		$(mkdir) $$plugin/$(build_path);\
		$(compiler) -classpath .$$plugin/$(interfaces_classpath) -d $$plugin/$(build_path) $$(find $$plugin/$(src_path) -name *.java);\
		$(jar) ./$$plugin/$$plugin.jar $$plugin/$(src_path)/* -C $$plugin/$(build_path) .;\
	done
	

refresh-plugins:
	for plugin in $$(ls . |grep -i plugin); do cp ./$$plugin/*.jar ./$(shell_path)/plugins/; done

run:
	echo "To do..."


all: clear-all interfaces shell plugins refresh-plugins
