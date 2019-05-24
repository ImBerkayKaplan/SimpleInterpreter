package nodes;

import java.io.IOException;

import main.Token;
import main.Tokenizer;

class StmtNode {
	
	private AssignNode an; // Assign node as a child for assignment
	private IfNode ifn; // If Node as a child for if
	private InNode in; // In Node as a child for read
	private OutNode on; // Out Node as a child for write
	private LoopNode ln; // Loop Node as a child for while
	private int alt; // The alternative of this node
	
	// Parse this statement
	void parseStmt(Tokenizer t) throws IOException{
		
		// Get the current token as a string
		Token token = t.currentToken();
		
		// Decide which method will be used.
		if(token.value.matches("[A-Z]+[0-9]*")) {
			this.alt = 1;
			this.an = new AssignNode();
			this.an.parseAssign(t);
		}else if(token.value.equals("if")) {
			this.alt = 2;
			this.ifn = new IfNode();
			this.ifn.parseIf(t);
		}else if(token.value.equals("while")) {
			this.alt = 3;
			this.ln = new LoopNode();
			this.ln.parseLoop(t);
		}else if(token.value.equals("read")) {
			this.alt = 4;
			this.in = new InNode();
			this.in.parseIn(t);
		}else if(token.value.equals("write")) {
			this.alt = 5;
			this.on = new OutNode();
			this.on.parseOut(t);
		}else{
			System.err.println("Expected an identifier, if, while, read, or write but have: " + token.value + " at line " + token.linenumber);
			System.exit(1);
		}
	}
	
	// Print the statement
	void printStmt(String indentation) {
		
		// Decide which statement to print
		if(this.alt == 1) {
			this.an.printAssign(indentation);
		}else if(this.alt == 2) {
			this.ifn.printIf(indentation);
		}else if(this.alt == 4) {
			this.in.printIn(indentation);
		}else if(this.alt == 5) {
			this.on.printOut(indentation);
		}else if(this.alt == 3) {
			this.ln.printLoop(indentation);
		}
	}
	
	// Execute Stmt depending on the alternative
	void execStmt() {
		
		// Decide which statement to execute
		if(this.alt == 1) {
			this.an.execAssign();
		}else if(this.alt == 2) {
			this.ifn.execIf();
		}else if(this.alt == 4) {
			this.in.execIn();
		}else if(this.alt == 5) {
			this.on.execOut();
		}else if(this.alt == 3) {
			this.ln.execLoop();
		}
	}
}
