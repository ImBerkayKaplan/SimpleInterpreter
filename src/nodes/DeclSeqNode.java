package nodes;

import java.io.IOException;

import main.Token;
import main.Tokenizer;

class DeclSeqNode {
	
	private DeclNode nd; // DeclNode as a child node
	private DeclSeqNode ds; // Declaration sequence node for parsing and printing
	private int alt; // The alternative of this node
	
	// Declare the parser and the DeclNode
	DeclSeqNode() throws IOException{
		this.nd = new DeclNode();
		this.alt = 1;
	}
	
	// Parse the DeclSeq
	void parseDeclseq(Tokenizer t) throws IOException{
		
		// Start by parsing the Decl that is the first child no matter what
		this.nd.parseDecl(t);
		
		// Get the current token
		Token token = t.currentToken();
		
		/* 
		* Check if the next token is int to recursively call parseDeclSeq again. 
		* If it is begin, do nothing, and if neither, exit program.
		*/
		if(token.value.equals("int")) {
			this.alt = 2;
			this.ds = new DeclSeqNode();
			this.ds.parseDeclseq(t);
		}else if(token.value.equals("begin")) {
			
		}else {
			System.err.println("Expected int or begin but have: " + token.value + " at line " + token.linenumber);
			System.exit(1);
		}
	}
	
	// Print the declaration sequence
	void printDeclSeq() {
		
		// Print one declaration
		this.nd.printDecl();
		
		// If there is a declaration sequence, print it
		if(this.alt == 2) {
			this.ds.printDeclSeq();
		}
	}
}
