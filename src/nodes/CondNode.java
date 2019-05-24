package nodes;

import java.io.IOException;

import main.Token;
import main.Tokenizer;

class CondNode {
	
	private CompNode cn; // The comp node to be used as one of the alternatives
	private Parser parser; // Parser for the match consume
	private CondNode condn1, condn2; // The conditional nodes for the second, third and the fourth alternative 
	private String op; // The operator for the third and the fourth node
	private int alt; // The alternative of this node
	
	// Initialize the CondNode along with the parser to use MatchConsume()
	CondNode(){
		this.parser = new Parser();
	}
	
	// Parse the condition
	void parseCond(Tokenizer t) throws IOException {
		
		// Get the current token as a string
		Token token = t.currentToken();
		
		// Check the current token to find the alternative or print an error message
		if(token.value.equals("(")) {
			this.alt = 1;
			this.cn = new CompNode();
			this.cn.parseComp(t);
		}else if(token.value.equals("!")) {
			this.alt = 2;
			this.condn1 = new CondNode();
			t.nextToken();
			this.condn1.parseCond(t);
		}else if(token.value.equals("[")) {
			this.condn1 = new CondNode();
			t.nextToken();
			this.condn1.parseCond(t);
			if(t.currentToken().value.equals("and") || t.currentToken().value.equals("or")) {
				this.alt = 3;
				if(t.currentToken().value.equals("or")) {
					this.alt = 4;
				}
				this.op = t.currentToken().value;
				this.condn2 = new CondNode();
				t.nextToken();
				this.condn2.parseCond(t);
			}else {
				System.err.println("Expected an and or or but have: " + token.value + " at line " + token.linenumber);
				System.exit(1);
			}
			this.parser.matchConsume("]", t);
		}else {
			System.err.println("Expected a (, !, or [ but have: " + token.value + " at line " + token.linenumber);
			System.exit(1);
		}
	}
	
	// Print the condition
	void printCond() {
		
		// Decide the alternative and proceed
		if(this.alt == 1) {
			this.cn.printComp();
		}else if(this.alt == 2) {
			System.out.print("!");
			this.condn1.printCond();
		}else if(this.alt == 3 || this.alt == 4) {
			System.out.print("[ ");
			this.condn1.printCond();
			System.out.print(" "+this.op+" ");
			this.condn2.printCond();
			System.out.print(" ]");
		}
	}
	
	// Evaluate the condition statement
	boolean evalCond() {
		boolean result = false;
		
		// Decide the alternative and proceed
		if(this.alt == 1) {
			result = this.cn.evalComp();
		}else if(this.alt == 2) {
			result = !this.condn1.evalCond();
		}else if(this.alt == 3) {
			result = this.condn1.evalCond() && this.condn2.evalCond();
		}else if(this.alt == 4) {
			result = this.condn1.evalCond() || this.condn2.evalCond();
		}
		
		return result;
	}
}
