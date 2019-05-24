package nodes;

import java.io.IOException;

import main.Tokenizer;

class DeclNode {
	
	private Parser parser; // Parser for consumeToken()
	private IdListNode il; // The idlist node as a child
	
	// Initialize the parser and the idlistNode
	DeclNode() throws IOException{
		this.parser = new Parser();
		this.il = new IdListNode();
	}
	
	// Parse the Decl
	void parseDecl(Tokenizer t) throws IOException{
		
		// Consume the int
		this.parser.matchConsume("int", t);
		
		// Parse the idList
		this.il.parseidList(t, true);
		
		// Consume the semicolon
		this.parser.matchConsume(";", t);
		
	}
	
	// Print the declaration
	void printDecl() {
		
		// Print the declarations as a id list
		System.out.print("  int ");
		this.il.printIdList();
		System.out.println(";");
	}
}
