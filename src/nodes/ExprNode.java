package nodes;

import java.io.IOException;

import main.Token;
import main.Tokenizer;

class ExprNode {
	
	private TermNode tn; // Term node to be used as a child
	private ExprNode en; // Expr Node to be used as a child recursively
	private int alt, linenumber; // The alternative of this node and the line number
	
	// Initialize the term node
	ExprNode(){
		this.tn = new TermNode();
		this.alt = 1;
	}
	
	// Parse the expression
	void parseExpr(Tokenizer t) throws IOException{
		
		// Parse the term and record the line number
		this.linenumber = t.currentToken().linenumber;
		this.tn.parseTerm(t);
		
		// Get the current token as a string
		Token token = t.currentToken();
		
		// If the next token is a plus or a minus, recursively parse the expression
		if(token.value.equals("+")) {
			this.alt = 2;
			this.en = new ExprNode();
			t.nextToken();
			this.en.parseExpr(t);
		}else if(token.value.equals("-")) {
			this.alt = 3;
			this.en = new ExprNode();
			t.nextToken();
			this.en.parseExpr(t);
		}
	}
	
	// Print the expression
	void printExpr() {
		
		// Print the term
		this.tn.printTerm();
		
		// Print the expression if it is not null
		if(this.alt == 2) {
			System.out.print(" + ");
			this.en.printExpr();
		} if(this.alt == 3) {
			System.out.print(" - ");
			this.en.printExpr();
		}
	}
	
	// Evaluate the expression
	int evalExpr() {
		int result = this.tn.evalTerm();
		int expr = 0;
		
		// Proceed with the correct operation according to the alternative
		if (this.alt == 2) {
			expr = this.en.evalExpr();
			try {
			    result = Math.addExact(expr, result);
			} catch(ArithmeticException e) {
			    System.err.println("Runtime Error: The addition of " + result + " and " + expr + " at line " + this.linenumber + " will cause an overflow. Program exiting.");
			    System.exit(1);
			}
		}else if(this.alt == 3) {
			expr = this.en.evalExpr();
			try {
			    result = Math.subtractExact(result, expr);
			} catch(ArithmeticException e) {
			    System.err.println("Runtime Error: The subtraction of " + result + " and " + expr + " at line " + this.linenumber + " will cause an overflow. Program exiting.");
			    System.exit(1);
			}
		}
		
		return result;
	}
}
