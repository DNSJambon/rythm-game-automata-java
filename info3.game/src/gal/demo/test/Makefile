JAVAPROJECT = ../../../..
CLASSPATH = $(JAVAPROJECT)/bin
AUTOMATES = $(JAVAPROJECT)/game/info3/game/model/Automates
PARSER = gal.parser.Parser

OUTPUT_DIR = ../output

help:
	@echo "1. INSTALL JavaCC with command: apt get install javacc"
	@echo "2. RUN     JavaCC with command: make parser"
	@echo "3. PARSE   file.gal with command: make file.parsing, make file.aut, make file.ast"  

parser:
	@make -C $(JAVAPROJECT)/src/gal/parser/

%.txt: ../../../../game/info3/game/model/Automates/%.gal 
	java -cp $(CLASSPATH) $(PARSER) -file $< -txt	

%.aut.dot: ../../../../game/info3/game/model/Automates/%.gal 
	@java -cp $(CLASSPATH) $(PARSER) -file $< -aut > $@

%.ast.dot: ../../../../game/info3/game/model/Automates/%.gal 
	@java -cp $(CLASSPATH) $(PARSER) -file $< -ast > $@

%.jpg: %.dot
	@dot -Tjpg $< > $@
	@open $@
	
clean:
	-@rm -f *.jpg *.dot