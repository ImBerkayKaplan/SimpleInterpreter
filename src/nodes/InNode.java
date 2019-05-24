package nodes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import main.Tokenizer;

class InNode {
	
	private Parser parser; // Parser for the match consume
	private IdListNode idlist; // The Id list to be used as a child when the variables are scanned
	private int linenumber; // Line number for error reporting purposes
	
	// Inialize the parser, the statement sequence node, and the condition node
	InNode() throws IOException{
		this.parser = new Parser();
		this.idlist = new IdListNode();
	}
	
	// Parse the read statement
	void parseIn(Tokenizer t) throws IOException{
		
		// Consume the constants, and parse the idList
		this.linenumber = t.currentToken().linenumber;
		this.parser.matchConsume("read", t);
		this.idlist.parseidList(t, false);
		this.parser.matchConsume(";", t);
		
	}
	
	// Print the read statement
	void printIn(String indentation) {
		
		// Print the read statement as well as the IdList and the semicolon at the end
		System.out.print(indentation + "read ");
		this.idlist.printIdList();
		System.out.println(";");
	}
	
	// Execute the read statement
	void execIn() {
		
		// Get the id list and initialize the scanner
		ArrayList<IdNode> nodes = this.idlist.returnIdList();
		Scanner in = new Scanner(System.in);
		
		// Print out all the values with the correct format
		for(int i = 0; i < nodes.size(); i++) {
			IdNode temp = nodes.get(i);
			int value = 0;
			
			// Check if the id is valid
			if(!temp.isDeclared()) {
				System.err.println("Runtime Error: " + temp.getName() +" is not declared at line " + this.linenumber + ". Program exiting.");
				System.exit(1);
			}
			System.out.print(temp.getName() + " =? ");
			
			// Get the integer and do error checking
			String val = in.nextLine();
			try {
			value = Integer.parseInt(val);
			}catch(Exception e) {
				System.err.println("Runtime Error: " + val +" exceeds the integer range at line " + this.linenumber + ". Program exiting.");
				System.exit(1);
			}
			
			// Set the value of the integer
			temp.setValue(value);
		}
		
	}
}
