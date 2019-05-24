JAVAC=javac
SOURCE=src
JAVA=*.java
BIN=bin

all: project

project:
	@mkdir -p bin
	@$(JAVAC) -d $(BIN) $(SOURCE)/*/$(JAVA)

clean:
	@rm -r $(BIN)/*
