# SimpleInterpreter

This program is an interpreter for an elementary language CORE. The syntax is included and contains only fundamental functions.

## Main Files

**Main**: The file reads one input file by command line argument and runs the Tokenizer class to output the corresponding codes to the screen.

**Token**: The file reads the input by line and tokenizes each token to return one token at a time to the Main.java with currentToken().

**Tokenizer**: The file serves as a data structure for all tokens and contains multiple information about each token, such as their line number, for error-checking purposes.

**Parser**: The program that contains the utility functions, such as matchConsume() for the ParseTree in node representation

## Linux Compilation

Download this repository to your local machine with the ```git clone https://github.com/ImBerkayKaplan/SimpleInterpreter.git``` command in your terminal. Then, type ```cd SimpleInterpreter``` to navigate to this repository's root directory.

Once you're in this repository's root directory, use the make file by typing ```make``` to the terminal in the project directory. Make will generate the bin folder that has all the class files. 

## Linux Run

While inside the root directory, type ```java -cp bin main.Main <input file>```, and make sure the ```<input file>``` is in the root directory. ```exampleCOREProgram.CORE``` is an example file that can be used as input. You can type ```java -cp bin main.Main exampleCOREProgram.CORE``` to run the project without any changes.
