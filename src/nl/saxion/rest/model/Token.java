package nl.saxion.rest.model;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Token {
	private String token;
	
	public Token() {
		token = new BigInteger(130, new SecureRandom()).toString(32);
	}
	
	@Override
	public String toString() {
		return token;
	}
}
