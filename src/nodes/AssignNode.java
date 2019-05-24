package nodes;

import java.io.IOException;

import main.Tokenizer;

class AssignNode {
	
	private Parser parser; // Parser to be used for matchConsumed
	private IdNode id; // IdNode to be used as a child
	private ExprNode expr; // Expression to be evaluated
	
	// Initialize the parser and the ExpNode
	AssignNode() throws IOException{
		parser = new Parser();
		this.expr = new ExprNode();
	}
	
	// Parse the Assign statement
	void parseAssign(Tokenizer t) throws IOException{
		
		// Parse the Id, the constants, and the expression
		this.id = IdNode.parseId(t);
		if(!this.id.isDeclared()) {
			System.err.println("Error at line " + t.currentToken().linenumber + ". " + this.id.getName() + " is not declared. Program exiting.");
			System.exit(1);
		}
		
		// Consume the constants and parse the expressions
		parser.matchConsume("=", t);
		this.expr.parseExpr(t);
		parser.matchConsume(";", t);
	}
	
	// Print the AssignNode
	void printAssign(String indentation){
		System.out.print(indentation);
		this.id.printId();
		System.out.print(" = ");
		this.expr.printExpr();
		System.out.println(";");
	}
	
	// Execute the statement and set the value of the identifier
	void execAssign() {
		int value = this.expr.evalExpr();
		this.id.setValue(value);
	}
}
