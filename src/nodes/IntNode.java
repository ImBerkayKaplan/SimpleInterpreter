package nodes;

import java.io.IOException;

import main.Tokenizer;

class IntNode {
	
	private int value; // The integer value that this class stores
	
	// The constructor that sets the integer value
	IntNode(int value){
		this.value = value;
	}
	
	// Get the value of this integer node
	int getValue(){
		return this.value;
	}
	
	// Print the value of this integer
	void printInt() {
		System.out.print(this.value);
	}
	
	// Parse the int
	static void parseInt(Tokenizer t) throws IOException {
		
		// Consume the token
		t.nextToken();
	}
}
