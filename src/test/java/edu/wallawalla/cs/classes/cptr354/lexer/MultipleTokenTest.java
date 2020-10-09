package edu.wallawalla.cs.classes.cptr354.lexer;

import org.junit.Test;

import edu.ucsb.cseweb.classes.cse131a.lexer.Sym;

public class MultipleTokenTest {

	@Test
	public void testGetTokenKeyword() {
		Lexer lexer = new Lexer("ARRAY;");
		TestUtils.assertToken(lexer.getToken(), "ARRAY", Sym.T_ARRAY);
		TestUtils.assertToken(lexer.getToken(), ";", Sym.T_SEMI);
	}

	@Test
	public void testGetTokenInclude() {
		Lexer lexer = new Lexer("MODULE \"src/test/resources/inputTest.txt\";");
		TestUtils.assertToken(lexer.getToken(), "MODULE", Sym.T_MODULE);
		TestUtils.assertToken(lexer.getToken(), "src/test/resources/inputTest.txt", Sym.T_STR_LITERAL);
		TestUtils.assertToken(lexer.getToken(), ";", Sym.T_SEMI);

	}

}