package nodes;

import java.io.IOException;
import java.util.ArrayList;

import main.Tokenizer;

class OutNode {
	
	private Parser parser; // Parser for the match consume
	private IdListNode idlist; // The id list to be used as a child when values are printed out
	private int linenumber; // The line number for error reporting purposes
	
	// Inialize the parser, the statement sequence node, and the condition node
	OutNode() throws IOException{
		this.parser = new Parser();
		this.idlist = new IdListNode();
	}
	
	// Parse this out node
	void parseOut(Tokenizer t) throws IOException{
		
		// Consume the constants, and parse the idList
		this.linenumber = t.currentToken().linenumber;
		this.parser.matchConsume("write", t);
		this.idlist.parseidList(t, false);
		this.parser.matchConsume(";", t);
		
	}
	
	// Print this out node
	void printOut(String indentation) {
		
		// Print the write statement as well as the IdList and the semicolon at the end
		System.out.print(indentation + "write ");
		this.idlist.printIdList();
		System.out.println(";");
	}
	
	// Execute the write statement
	void execOut() {
		ArrayList<IdNode> nodes = this.idlist.returnIdList();
		
		// Print out all the values with the correct format
		for(int i = 0; i < nodes.size(); i++) {
			IdNode temp = nodes.get(i);
			if(temp.isInitialized()) {
				System.out.println(temp.getName() + " = " + temp.getValue());
			}else {
				System.err.println("Runtime Error: " + temp.getName() +" is not initialized at line " + this.linenumber + ". Program exiting.");
				System.exit(1);
			}
		}
		
	}
}
