package nodes;
import java.io.IOException;

import main.Token;
import main.Tokenizer;

public class ProgramNode {
	
	private DeclSeqNode ds; // DeclSeq as the first child node
	private StmtSeqNode ss; // StmtSeq as the second child node
	private Parser parser; // Parser for the matchConsume
	
	// Initalize the child nodes and the parser to use matchConsume
	public ProgramNode() throws IOException{
		this.ds = new DeclSeqNode();
		this.ss = new StmtSeqNode();
		this.parser = new Parser();
		
		//Initialize the Symbol Table once
		IdNode.initSymbolTable();
	}
	
	// Parse the program
	public void parseProgram(Tokenizer t) throws IOException{
		
		// Consume constants and run the method for decl-seq and stmtseq
		this.parser.matchConsume("program", t);
		this.ds.parseDeclseq(t);
		this.parser.matchConsume("begin", t);
		this.ss.parseStmtseq(t);
		this.parser.matchConsume("end", t);
		
		// Get the current token as a token
		Token token = t.currentToken();
		
		// Make sure the junk is cleared at the end of the program
		if(token.value != null) {
			System.err.println("Expected the end of the program but have: " + token.value + " at line " + token.linenumber);
			System.exit(1);
		}
	}
	
	
	// Print the program
	public void printProgram() {
		System.out.println("program ");
		this.ds.printDeclSeq();
		System.out.println("  begin");
		this.ss.printStmtSeq("    ");
		System.out.println("  end");
	}
	
	// Execute the whole program
	public void execProgram() {
		this.ss.execStmtSeq();
	}
}
