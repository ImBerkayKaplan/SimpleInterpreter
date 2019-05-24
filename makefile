# make file for for the parse tree and the pretty print

JAVAC=javac
SOURCE=src
JAVA = *.java
CLASS = *.class
BIN = bin

all: project

project:
	mkdir -p bin
	$(JAVAC) -d $(BIN) $(SOURCE)/*/$(JAVA)

clean:
	rm -r $(BIN)/*
