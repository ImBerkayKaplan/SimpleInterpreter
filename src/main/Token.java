package main;
/*
 * The token object that stores the string value of the token
 * and the linenumber, which the token is found in
 */
public class Token {
	
	// The global variables
	public String value;
	public int linenumber;
	
	/*
	 * The constructor that initializes the global variables
	 * String value is the string value of the token
	 * int linenumber is which line the token is in
	 */
	Token(String value, int linenumber){
		this.value = value;
		this.linenumber = linenumber;
	}
}
