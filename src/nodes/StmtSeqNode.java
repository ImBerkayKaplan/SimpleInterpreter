package nodes;

import java.io.IOException;

import main.Token;
import main.Tokenizer;

class StmtSeqNode {
	
	private StmtNode sn; // StmtNode is needed for as a child node
	private StmtSeqNode ss; // StmtSeqNode is needed as a child for recursion
	private int alt; // The alternative of this node
	
	// Initalize the parser and the stmt node
	StmtSeqNode(){
		this.sn = new StmtNode();
		this.alt = 1;
	}
	
	// Parse the statement sequence
	void parseStmtseq(Tokenizer t) throws IOException{
		
		// Run the first statement
		this.sn.parseStmt(t);
		
		// Get the token as a string
		Token token = t.currentToken();
		
		// Decide if the next child is stmt-seq or and end. If neither, an error message will be printed
		if(token.value.matches("[A-Z]+[0-9]*") || token.value.equals("if") || token.value.equals("while") || token.value.equals("read") || token.value.equals("write")) {
			this.alt = 2;
			this.ss = new StmtSeqNode();
			this.ss.parseStmtseq(t);
		} else if(token.value.equals("end") || token.value.equals("else")) {

		}else{
			System.err.println("Expected an identifier, if, while, read, write, end, or else but have: " + token.value + " at line " + token.linenumber);
			System.exit(1);
		}
			
	}
	
	// Print the statement sequence
	void printStmtSeq(String indentation) {
		
		// Print the statement
		this.sn.printStmt(indentation);
		
		// If there is another statement sequence, print it
		if(this.alt == 2) {
			this.ss.printStmtSeq(indentation);
		}
	}
	
	// Execute the statement sequence
	void execStmtSeq() {
		
		// Execute the statement regardless
		this.sn.execStmt();
		
		// Execute another statement sequence if the alternative is 2
		if(this.alt == 2) {
			this.ss.execStmtSeq();
		}
	}
}
