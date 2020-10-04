package edu.wallawalla.cs.classes.cptr354.lexer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.ucsb.cseweb.classes.cse131a.lexer.Sym;
import edu.ucsb.cseweb.classes.cse131a.lexer.Token;

public class MultipleTokenTest {

	private Token token;

	@Test
	public void testGetTokenKeyword() {
		Lexer lexer = new Lexer("ARRAY;");

		token = lexer.getToken();
		assertEquals(token.GetType(), Sym.T_ARRAY);
		assertEquals(token.GetLexeme(), "ARRAY");

		token = lexer.getToken();
		assertEquals(token.GetType(), Sym.T_SEMI);
		assertEquals(token.GetLexeme(), ";");
	}

	@Test
	public void testGetTokenInclude() {
		Lexer lexer = new Lexer("MODULE \"src/test/resources/inputTest.txt\";");

		token = lexer.getToken();
		assertEquals(token.GetType(), Sym.T_MODULE);
		assertEquals(token.GetLexeme(), "MODULE");

		token = lexer.getToken();
		assertEquals(token.GetType(), Sym.T_STR_LITERAL);
		assertEquals(token.GetLexeme(), "src/test/resources/inputTest.txt");

		token = lexer.getToken();
		assertEquals(token.GetType(), Sym.T_SEMI);
		assertEquals(token.GetLexeme(), ";");
	}

}