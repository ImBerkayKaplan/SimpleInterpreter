package main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nodes.ProgramNode;

/*
 * The Main sets the alphabet of the core language and 
 * uses the Tokenizer object to parse an input file. It calls
 * currentToken() from the Tokenizer object repeatedly until it 
 * returns null, which indicate the file has ended. It gets the 
 * next token and classifies it to print the corresponding code 
 * to the screen. It reports errors with the appropriate message.
 * 
 * @author Berkay Kaplan
 */
public class Main {
	public static void main(String[] args) throws IOException {
		
		//-----------------------------------------------Tokenizer--------------------------------------------
		
		// Exit if the user doesn't enter the input file name
		if(args.length!=1) {
			System.err.println("Please enter the name of the input file for core program only (program <input-file>)");
			System.exit(0);
		}
		
		// Check if the input file exist and is file
		File file = new File(args[0]);
		if(!file.exists() || !file.isFile()) {
			System.err.println("File does not exist. Please enter a valid file name.");
			System.exit(0);
		}
		
		// Open the input file, declare the map, create the code array, and add the language to the map
		BufferedReader input = new BufferedReader(new FileReader(file));
	    Map<String,Integer> alphabet = new HashMap<String,Integer>(); 
	    addWords(alphabet);
	    ArrayList<Integer> codes = new ArrayList<Integer>();
	    
	    // Initialize patterns for regex and the tokenizer
	    String identifier = "[A-Z]+[0-9]*", number = "([1-9]\\d*)|0";
	    Tokenizer tokenizer = new Tokenizer(input);
	    
	    // Iterate until the token is null, which indicates the file is ended
	    while(tokenizer.currentToken().value != null) {
	    	
	    	// Get the current token
	    	String token = tokenizer.currentToken().value;
	    	
	    	// Check the type of the token
	    	if(token.matches(identifier)) {

	    		// Print a specific error message if the identifier exceeds the maximum size 8
	    		if(token.length() > 8) {
	    	    	System.err.println("Error:  [Line " + tokenizer.currentToken().linenumber + "] Length of the identifier exceeds the maximum length 8 " + tokenizer.currentToken().value);
	    	    	System.exit(0);
	    		}
	    		codes.add(32);
	    	}else if(token.matches(number)) {

				// Print a specific error message if the number exceeds the maximum size 8
	    		if(token.length() > 8) {
	    	    	System.err.println("Error:  [Line " + tokenizer.currentToken().linenumber + "] Length of the number exceeds the maximum length 8 " + tokenizer.currentToken().value);
	    	    	System.exit(0);
	    		}
	    		codes.add(31);
	    	}else {
	    		// print an error message if the token does not exist in the alphabet
	    		if(!alphabet.containsKey(token)) {
	    	    	System.err.println("Error:  [Line " + tokenizer.currentToken().linenumber + "] Invalid ID token " + tokenizer.currentToken().value);
	    	    	System.exit(0);
	    		}
	    		codes.add(alphabet.get(token));
	    	}
	    	
	    	// Get the next token
	    	tokenizer.nextToken();
	    }
	    
	    // Write the EOF
	    codes.add(33);
	    
	    // Close the input file
	    input.close();
	    
	    //----------------------------------Parser-----------------------------------------------------------------
	    
	    // Reopen the file again as file and input parser
	    BufferedReader inputParser = new BufferedReader(new FileReader(file));
	    
	    // Parse the program
	    ProgramNode program = new ProgramNode();
	    program.parseProgram(new Tokenizer(inputParser));
	    
	    // Closing the parser
	    inputParser.close();
	    
	    //--------------------------------Execute------------------------------------------------------
	    program.execProgram();
	    
	}
	
	/*
	 * Adds the words from CORE language and their corresponding codes to the map.
	 * 
	 * @param alhpabet An empty map that will contain all the CORE symbols at the end of the method
	 */
	private static void addWords(Map<String,Integer> alphabet) {
		alphabet.put("program", 1);
		alphabet.put("begin", 2);
		alphabet.put("end", 3);
		alphabet.put("int", 4);
		alphabet.put("if", 5);
		alphabet.put("then", 6);
		alphabet.put("else", 7);
		alphabet.put("while", 8);
		alphabet.put("loop", 9);
		alphabet.put("read", 10);
		alphabet.put("write", 11);
		alphabet.put("and", 12);
		alphabet.put("or", 13);
		alphabet.put(";", 14);
		alphabet.put(",", 15);
		alphabet.put("=", 16);
		alphabet.put("!", 17);
		alphabet.put("[", 18);
		alphabet.put("]", 19);
		alphabet.put("(", 20);
		alphabet.put(")", 21);
		alphabet.put("+", 22);
		alphabet.put("-", 23);
		alphabet.put("*", 24);
		alphabet.put("!=", 25);
		alphabet.put("==", 26);
		alphabet.put(">=", 27);
		alphabet.put("<=", 28);
		alphabet.put(">", 29);
		alphabet.put("<", 30);
	}
}
