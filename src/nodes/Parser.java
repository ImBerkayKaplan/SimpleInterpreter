package nodes;

import java.io.IOException;

import main.Token;
import main.Tokenizer;

class Parser {
	
	// Consume the current token if it matches the expected value
	void matchConsume(String expected, Tokenizer t) throws IOException {
		
		Token token = t.currentToken();
		// Check if current token matches expected. If it doesn't, exit the program.
		if(!expected.equals(token.value)) {
			System.err.println("Expected " + expected + ", but token is " + token.value + " at line " + token.linenumber + ". Program terminating.");
			System.exit(1);
		}
		
		// Consume token
		t.nextToken();
	}
}
