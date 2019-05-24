package nodes;

import java.io.IOException;

import main.Tokenizer;

class TermNode {
	
	private FacNode fn; // Fac Node to be used as a child
	private TermNode tn; // Term Node to be used as a child for recursion
	private int alt, linenumber; // The alternative of this node and the line number of the term
	
	// Initialize the facNode and the alternative
	TermNode(){
		this.fn = new FacNode();
		this.alt = 1;
	}
	
	// Parse the term
	void parseTerm(Tokenizer t) throws IOException{
		
		// Get the line number, and Parse the fac term
		this.linenumber = t.currentToken().linenumber;
		this.fn.parseFac(t);
		
		// Get the current token as a string
		String token = t.currentToken().value;
		
		// Check which alternative to proceed with
		if(token.equals("*")) {
			this.alt = 2;
			this.tn = new TermNode();
			t.nextToken();
			this.tn.parseTerm(t);
		}
	}
	
	// Print this term
	void printTerm() {
		
		// Print the fac
		this.fn.printFac();
		
		// Print the multiplication and the term recursively
		if(this.alt == 2) {
			System.out.print(" * ");
			this.tn.printTerm();
		}
	}
	
	// Evaluate a term
	int evalTerm() {
		
		// Evaluate the fac regardless
		int result = this.fn.evalFac();
		
		// If the alternative is 2, evaluate the term and perform the multiplication
		if(this.alt == 2) {
			int term = this.tn.evalTerm();
			
			try {
			    result = Math.multiplyExact(term, result);
			} catch(ArithmeticException e) {
			    System.err.println("Runtime Error: The multiplication of " + result + " and " + term + " at line " + this.linenumber + " is out of the integer range. Program exiting.");
			    System.exit(1);
			}
		}
		return result;
	}

}
