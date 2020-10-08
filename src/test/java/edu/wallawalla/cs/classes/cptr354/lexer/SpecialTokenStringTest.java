package edu.wallawalla.cs.classes.cptr354.lexer;

import org.junit.Test;

import edu.ucsb.cseweb.classes.cse131a.lexer.Sym;

public class SpecialTokenStringTest {

	@Test
	public void testGetTokenStringTooLong() {
		String input = "'This test string is much, much longer that 80 characters. Would you not agree? I would.';";
		String expected = "This test string is much, much longer that 80 characters. Would you not agree? ";
		Lexer lexer = new Lexer(input);
		TestUtils.assertToken(lexer.getToken(), expected, Sym.T_STR_LITERAL);
	}

	@Test
	public void testGetTokenStringEOF() {
		String input = "'This test string is much, much longer that 80 characters. Would you not agree? I would. EOF;";
		String expected = "This test string is much, much longer that 80 characters. Would you not agree? ";
		Lexer lexer = new Lexer(input);
		TestUtils.assertToken(lexer.getToken(), expected, Sym.T_STR_LITERAL);
	}

	@Test
	public void testGetTokenStringNewLine() {
		String input = "'This test string is much, much longer that 80 characters. Would you not agree? I would.\n;";
		String expected = "This test string is much, much longer that 80 characters. Would you not agree? ";
		Lexer lexer = new Lexer(input);
		TestUtils.assertToken(lexer.getToken(), expected, Sym.T_STR_LITERAL);
	}

	@Test
	public void testGetTokenStringDoubleSingle() {
		String input = "\"test 'in single quotes.'\";";
		String expected = "test 'in single quotes.'";
		Lexer lexer = new Lexer(input);
		TestUtils.assertToken(lexer.getToken(), expected, Sym.T_STR_LITERAL);
	}

	@Test
	public void testGetTokenStringSingleDouble() {
		String input = "'test \"in double quotes.\"';";
		String expected = "test \"in double quotes.\"";
		Lexer lexer = new Lexer(input);
		TestUtils.assertToken(lexer.getToken(), expected, Sym.T_STR_LITERAL);
	}

}