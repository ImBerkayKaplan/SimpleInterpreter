package nodes;

import java.io.IOException;

import main.Token;
import main.Tokenizer;

class IfNode {
	
	private Parser parser; // Parser for the match consume
	private StmtSeqNode ss1, ss2; // Statement seq for the children
	private CondNode cn; // Condition node for the if statement
	private int alt; // The alternative of this node
	
	// Initalize the parser, the statement sequence node, and the condition node
	IfNode() throws IOException{
		this.parser = new Parser();
		this.ss1 = new StmtSeqNode();
		this.cn = new CondNode();
		this.alt = 1;
	}
	
	// Parse this if node
	void parseIf(Tokenizer t) throws IOException{
		
		// Consume the constants, evaluate the condition, and parse the statement sequence
		this.parser.matchConsume("if", t);
		this.cn.parseCond(t);
		this.parser.matchConsume("then", t);
		this.ss1.parseStmtseq(t);
		
		// Get the current token as a string
		Token token = t.currentToken();
		
		// Decide if this if statement has an else portion
		if(token.value.equals("else")) {
			this.alt = 2;
			this.ss2 = new StmtSeqNode();
			t.nextToken();
			this.ss2.parseStmtseq(t);
			this.parser.matchConsume("end", t);
		} else if(token.value.equals("end")) {
			t.nextToken();
		} else {
			System.err.println("Expected an else, or end but have: " + token.value + " at line " + token.linenumber);
			System.exit(1);
		}
		
		// Consume the final semicolon
		this.parser.matchConsume(";", t);
	}
	
	// Print the if statement
	void printIf(String indentation) {
		
		// Print the if, the condition, and the then
		System.out.print(indentation + "if ");
		this.cn.printCond();
		System.out.println(" then");

		// Print the statement sequence, the end, and add spaces to the indentation
		this.ss1.printStmtSeq("  "+indentation);
		if(this.alt == 2) {
			System.out.println(indentation+"else");
			this.ss2.printStmtSeq("  "+indentation);
		}
		System.out.println(indentation+"end;");
	}
	
	// Execute the if statement
	void execIf() {
		
		// Execute the first if statement regardless
		if(this.cn.evalCond()) {
			this.ss1.execStmtSeq();
		}else if(this.alt == 2) {
			this.ss2.execStmtSeq();
		}
	}
}
