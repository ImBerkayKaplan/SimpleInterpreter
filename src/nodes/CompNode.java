package nodes;

import java.io.IOException;

import main.Token;
import main.Tokenizer;

class CompNode {
	
	private Parser parser; // Parser for the match consume
	private FacNode fn1, fn2; // Fac Node to be used as a child
	private String op; // The operator of the comparison
	
	// Initialize the parser and the FacNode
	CompNode(){
		this.parser = new Parser();
		this.fn1 = new FacNode();
		this.fn2 = new FacNode();
	}
	
	// Parse the comparison statement
	void parseComp(Tokenizer t) throws IOException{
		
		// Consume the paranthesis, parse the fac, and get the current token
		this.parser.matchConsume("(", t);
		this.fn1.parseFac(t);
		Token token = t.currentToken();
		
		// Check if the comp-operator is a valid symbol
		if(!token.value.matches("!=|==|<|>|<=|>=")) {
			System.err.println("Expected a !=, ==, <, >, <=, or >= but have: " + token.value + " at line " + token.linenumber);
			System.exit(1);
		}
		
		// Get the operator for printing
		this.op = token.value;
		
		// Consume the comp operator. parse the fac, and consume the paranthesis
		t.nextToken();
		this.fn2.parseFac(t);
		this.parser.matchConsume(")", t);
	}
	
	// Print comp
	void printComp() {
		
		// Print the comp with paranthesis and the operator with facs
		System.out.print("( ");
		this.fn1.printFac();
		System.out.print(" " + this.op + " ");
		this.fn2.printFac();
		System.out.print(" )");
	}
	
	// Evaluate the comparison statement
	boolean evalComp() {
		boolean result = false;
		
		// Evaluate the comparison with the correct operator
		if(this.op.equals("!=")) {
			result = this.fn1.evalFac() != this.fn2.evalFac();
		}else if(this.op.equals("==")) {
			result = this.fn1.evalFac() == this.fn2.evalFac();
		}else if(this.op.equals("<")) {
			result = this.fn1.evalFac() < this.fn2.evalFac();
		}else if(this.op.equals(">")) {
			result = this.fn1.evalFac() > this.fn2.evalFac();
		}else if(this.op.equals("<=")) {
			result = this.fn1.evalFac() <= this.fn2.evalFac();
		}else if(this.op.equals(">=")) {
			result = this.fn1.evalFac() >= this.fn2.evalFac();
		}
		
		return result;
	}
}
