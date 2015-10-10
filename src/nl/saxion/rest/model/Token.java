package nl.saxion.rest.model;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Token {
	private String token;
	public static final String TOKEN_HEADER = "token";
	
	public Token() {
		token = new BigInteger(130, new SecureRandom()).toString(32);
	}
	
	/**
	 * this constructor is here to enable easy testing with postman,
	 * constructor should be deleted when application goes live
	 * @param testToken the test token.
	 */
	public Token(String testToken){
		this.token = testToken;
	}
	
	@Override
	public String toString() {
		return token;
	}
}
