package nodes;

import java.io.IOException;

import main.Token;
import main.Tokenizer;

class FacNode {
	
	private Parser parser; // Parser to be used for matchConsumed
	private ExprNode en; // Expr Node to be used as a child
	private IdNode id; // Id Node to be used as a child
	private IntNode intd; // The value if the fac is an int
	private int alt, linenumber; // The line number of the token and the alternative of this node
	
	// Initialize the parser
	FacNode(){
		this.parser = new Parser();
	}
	
	// Parse the fac
	void parseFac(Tokenizer t) throws IOException{
		
		// Get the current token as a string and consume the token
		Token token = t.currentToken();
		
		// Decide which alternative is the current token
		if(token.value.equals("(")) {
			this.alt = 3;
			t.nextToken();
			this.en = new ExprNode();
			this.en.parseExpr(t);
			this.parser.matchConsume(")", t);
		}else if(token.value.matches("([1-9]\\d*)|0")) {
			this.alt = 1;
			this.intd = new IntNode(Integer.parseInt(token.value));
			IntNode.parseInt(t);
		}else if(token.value.matches("[A-Z]+[0-9]*")) {
			this.alt = 2;
			this.id = IdNode.parseId(t);
			this.linenumber = token.linenumber;
		}else {
			System.err.println("Expected an identifier, (, or a number but have: " + token.value + " at line " + token.linenumber);
			System.exit(1);
		}
	}
	
	// Print the fac
	void printFac() {
		
		// Find the alternative to print out
		if(this.alt == 2) {
			this.id.printId();
		}else if(this.alt == 1) {
			this.intd.printInt();
		}else if(this.alt == 3) {
			System.out.print("( ");
			this.en.printExpr();
			System.out.print(" )");
		}
	}
	
	// Evaluate the Fac statement
	int evalFac() {
		int result = 0;
		
		// Assign result to the correct method
		if(this.alt == 1) {
			result = this.intd.getValue();
		}else if(this.alt == 2) {
			
			// Check if the id is initialized
			if(!IdNode.SymbolTable.get(this.id.getName()).isInitialized()) {
				System.err.println("Runtime Error: " + this.id.getName() + " is not initialized at line " + this.linenumber);
				System.exit(1);
			}
			
			result = IdNode.SymbolTable.get(this.id.getName()).getValue();
		}else if(this.alt == 3) {
			result = this.en.evalExpr();
		}
		
		return result;
	}
}
