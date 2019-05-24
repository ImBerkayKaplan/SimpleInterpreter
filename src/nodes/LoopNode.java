package nodes;

import java.io.IOException;

import main.Tokenizer;

class LoopNode {
	
	private Parser parser; // Parser for the match consume
	private StmtSeqNode ss; // Statement seq for the children
	private CondNode cn; // Condition node for the if statement
	
	// Inialize the parser, the statement sequence node, and the condition node
	LoopNode() throws IOException{
		this.parser = new Parser();
		this.ss = new StmtSeqNode();
		this.cn = new CondNode();
	}
	
	// Parse the loop
	void parseLoop(Tokenizer t) throws IOException{
		
		// Consume the constants, evaluate the condition, and parse the statement sequence
		this.parser.matchConsume("while", t);
		this.cn.parseCond(t);
		this.parser.matchConsume("loop", t);
		this.ss.parseStmtseq(t);
		this.parser.matchConsume("end", t);
		this.parser.matchConsume(";", t);
	}
	
	// Print the loop
	void printLoop(String indentation) {
		
		// Print the first line of the while
		System.out.print(indentation + "while ");
		this.cn.printCond();
		System.out.println(" loop");
		
		// Print the statement sequence inside the loop and the end
		this.ss.printStmtSeq("  "+indentation);
		System.out.println(indentation + "end;");
		
	}
	
	// Execute the loop statement
	void execLoop() {
		
		// Execute statement sequences until the condition is false
		while(this.cn.evalCond()) {
			this.ss.execStmtSeq();
		}
	}
}
