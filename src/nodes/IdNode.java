package nodes;

import java.io.IOException;
import java.util.HashMap;

import main.Tokenizer;

class IdNode {
	private String name; // The name of this id. Every IdNode must have a name
	private int value; // The value of this id
	private boolean initialized; // If this identifier is initialized
	private boolean declared; // If this identifier is declared
	static HashMap<String, IdNode> SymbolTable; // Symbol table to keep track of the identifiers and their values
	
	// Initialize the parser, the name, and the initialized of this identifier
	IdNode(String name) throws IOException{
		this.name = name;
		this.initialized = false;
		this.declared = false;
	}
	
	// Initialize the Symbol table one time
	static void initSymbolTable() {
		SymbolTable = new HashMap<String, IdNode>();
	}
	
	// Set the variable of this identifier
	void setValue(int value) {
		this.value = value;
		this.initialized = true;
	}
	
	// Return the value of this identifier
	int getValue() {
		return this.value;
	}
	
	// Return the name of this identifier
	String getName() {
		return this.name;
	}
	
	// Check if this node is initialized
	boolean isInitialized() {
		return this.initialized;
	}
	
	// If this IdNode is declared
	boolean isDeclared() {
		return this.declared;
	}
	
	// Declare this IdNode
	void setDeclared() {
		this.declared = true;
	}
	
	// Parse the id by adding it to the symbol table and consume it
	static IdNode parseId(Tokenizer t) throws IOException{
		
		// Add the IdNode to the symbol table with the current token or print an error message if it already exists in the symbol table
		String token = t.currentToken().value;
		
		// Consume the identifier
		t.nextToken();
		
		// Add the token into the symbol table if it is not already in it
		if(!IdNode.SymbolTable.containsKey(token)) {
			IdNode.SymbolTable.put(token, new IdNode(token));
		}
		
		// Return the IdNode
		return IdNode.SymbolTable.get(token);
	}
	
	// Print the name of this id
	void printId() {
		System.out.print(this.name);
	}
}
