package edu.wallawalla.cs.classes.cptr354.lexer;

import static org.junit.Assert.assertEquals;

import edu.ucsb.cseweb.classes.cse131a.lexer.Token;

public class TestUtils {

	public static void assertLexerSingleToken(String input, int type) {
		Lexer lexer = new Lexer(input);
		assertToken(lexer.getToken(), input, type);
	}

	public static void assertToken(Token token, String expected, int type) {
		assertEquals(type, token.GetType());
		assertEquals(expected, token.GetLexeme());
	}

}