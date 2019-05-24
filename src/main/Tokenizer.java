package main;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
 * The Tokenizer reads the input file line by line and manipulates the lines
 * with regular expressions to correctly return each token. It also keeps track
 * of the line number of each token as well.
 * 
 * @author Berkay Kaplan
 */
public class Tokenizer {
	
	// The global variables declarations, and their explanations are in the constructor header
	private BufferedReader input;
	private int linenumber;
	private String line;
	private Token token;
	private ArrayList<String> tokens;
	
	/*
	 * Initialize the input, where the CORE language will be read from
	 * The token, where the current token will be stored at
	 * line, where a line of the input file will be stored at
	 * tokens, where a data structure to store all tokens of a line
	 * linenumber, where a variable to let the user know which line the error occured
	 * 
	 * @param input The input file that will be read line by line
	 */
	Tokenizer(BufferedReader input) throws IOException{
		this.input = input;
		line = "";
		this.tokens = new ArrayList<String>();
		this.linenumber = 0;
		this.token = new Token("", this.linenumber);
		this.nextToken(); // Skip the blank element
	}
	
	/*
	 * This is a preprocessing stage.
	 * The method adds space before and after each special character.
	 * So, the split method can separate the tokens using spaces.
	 * 
	 * @param line The line that the spaces will be added to before and after each special character
	 * 
	 * @return String a space is added before and after each special character
	 */
	private String tokenize(String line) {
		
		line=line.replaceAll("=="," == ");
		line=line.replaceAll("!="," != ");
		line=line.replaceAll(">="," >= ");
		line=line.replaceAll("<="," <= ");
		line=line.replaceAll(";", " ; ");
		line=line.replaceAll("\\+"," \\+ ");
		line=line.replaceAll("-"," - ");
		line=line.replaceAll("\\*"," \\* ");
		line=line.replaceAll("\\("," \\( ");
		line=line.replaceAll("\\)"," \\) ");
		line=line.replaceAll("\\["," \\[ ");
		line=line.replaceAll("\\]"," \\] ");
		line=line.replaceAll(","," , ");
		
		// A special case is needed for = to prevent separating <= etc from the < side.
	    Pattern pattern1 = Pattern.compile("[^<>!= ]=");
	    Matcher m1 = pattern1.matcher(line);
	    while (m1.find()) {
	    	line = line.substring(0,m1.start()+1) + " = " + line.substring(m1.end()); 
	    	m1 = pattern1.matcher(line);
	    }
	    
	    // A special case is needed for =, >, <, and ! characters to prevent separating <= etc from the = side.
	    Pattern pattern2 = Pattern.compile("[<>!=][^= ]");
	    Matcher m2 = pattern2.matcher(line);
	    while (m2.find()) {
	    	line = line.substring(0,m2.start()) + " " + line.charAt(m2.start()) + " " + line.substring(m2.end()-1);
	    	m2 = pattern2.matcher(line);
	    }
	    
	    // Return the modified line
		return line;
	}
	
	/*
	 * Returns the current token
	 * 
	 * @return Token the current token that the Tokenizer is at
	 */
	public Token currentToken() {
		return this.token;
	}
	
	/*
	 * Sets the token to the next token in the input file line and reads
	 * lines from the input file as well as skipping the blank lines. Sets
	 * the current token to the next token if the tokens list is not empty 
	 * or the line is not blank. 
	 */
	public void nextToken() throws IOException {
		
		// Eliminate blank lines and provide more elements for tokens until the input file is read completely
		while(line.isEmpty()||tokens.isEmpty()) {
			
			// Read next line
			if((this.line = this.input.readLine()) == null) {
				tokens.add(null);
				break;
			}
			
			// Add spaces for all special characters
			this.line=tokenize(line);
			
			// Tokenize the line only if it is not empty
			if(!line.isEmpty()) {
				if(!line.trim().equals("")) {
					this.tokens.addAll(Arrays.asList(line.trim().split("\\s+")));
				}
			}
			
			// Increment the line number
			this.linenumber++;
		}
		
		// Remove the first token in the tokens list and return the token object
		this.token = new Token(tokens.remove(0), this.linenumber);
		
		
		
	}
}