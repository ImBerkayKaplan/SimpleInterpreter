package nodes;

import java.io.IOException;
import java.util.ArrayList;

import main.Token;
import main.Tokenizer;

class IdListNode {
	private Parser parser; // Parser for consumeToken()
	private IdNode id; // The idlist node as a child
	private IdListNode idList;
	private int alt; // The alternative of this node
	
	// Initialize the parser and the idlistNode
	IdListNode() throws IOException{
		this.parser = new Parser();
		this.alt = 1;
	}
	
	// Parse the id list
	void parseidList(Tokenizer t, boolean declare) throws IOException{
		
		// Parse the ID
		this.id = IdNode.parseId(t);
		
		// Check if it is a declare to avoid double declarations
		if(declare) {
			if(this.id.isDeclared()) {
				System.err.println("Error at line " + t.currentToken().linenumber + ". " + this.id.getName() + " already declared. Program exiting.");
				System.exit(1);
			}else {
				this.id.setDeclared();
			}
		}
		
		// Get the current token
		Token token = t.currentToken();
		
		/*
		 * Check if the current token is a comma to proceed. 
		 * If it is a semicolon, do nothing. 
		 * If it is neither, exit the program gracefully.
		 */
		if(token.value.equals(",")) {
			this.alt = 2;
			this.parser.matchConsume(",", t);
			this.idList = new IdListNode();
			this.idList.parseidList(t, declare);
		}else if(token.value.equals(";")) {
			
		}else {
			System.err.println("Expected , or ; but have: " + token.value + " at line " + token.linenumber);
			System.exit(1);
		}
		
	}
	
	// Print the idList
	void printIdList() {
		
		// Print the id 
		this.id.printId();
		
		// If there is another id list, print it with a comma. Otherwise print a comma to end the line
		if(this.alt == 2) {
			System.out.print(", ");
			this.idList.printIdList();
		}
	}
	
	// Returns all the IdNodes parsed by the idListNode as an array list
	ArrayList<IdNode> returnIdList(){
		
		// Declare the arrayList that holds all nodes and add the first id to the list
		ArrayList<IdNode> result = new ArrayList<IdNode>();
		result.add(this.id);
		
		if(this.alt == 2) {
			result.addAll(this.idList.returnIdList());
		}
		return result;
	}
}
