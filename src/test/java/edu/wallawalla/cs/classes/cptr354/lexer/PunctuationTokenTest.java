package edu.wallawalla.cs.classes.cptr354.lexer;

import org.junit.Test;

import edu.ucsb.cseweb.classes.cse131a.lexer.Sym;

public class PunctuationTokenTest {

	@Test
	public void testGetTokenAmp() {
		TestUtils.assertLexerSingleToken("&", Sym.T_AMPERSAND);
	}

	@Test
	public void testGetTokenArrow() {
		TestUtils.assertLexerSingleToken("^", Sym.T_ARROW);
	}

	@Test
	public void testGetTokenAssign() {
		TestUtils.assertLexerSingleToken(":=", Sym.T_ASSIGN);
	}

	@Test
	public void testGetTokenColon() {
		TestUtils.assertLexerSingleToken(":", Sym.T_COLON);
	}

	@Test
	public void testGetTokenComma() {
		TestUtils.assertLexerSingleToken(",", Sym.T_COMMA);
	}

	@Test
	public void testGetTokenDotdot() {
		TestUtils.assertLexerSingleToken("..", Sym.T_DOTDOT);
	}

	@Test
	public void testGetTokenDot() {
		TestUtils.assertLexerSingleToken(".", Sym.T_DOT);
	}

	@Test
	public void testGetTokenEqu() {
		TestUtils.assertLexerSingleToken("=", Sym.T_EQU);
	}

	@Test
	public void testGetTokenGt() {
		TestUtils.assertLexerSingleToken(">", Sym.T_GT);
	}

	@Test
	public void testGetTokenGte() {
		TestUtils.assertLexerSingleToken(">=", Sym.T_GTE);
	}

	@Test
	public void testGetTokenLbrace() {
		TestUtils.assertLexerSingleToken("{", Sym.T_LBRACE);
	}

	@Test
	public void testGetTokenLbracket() {
		TestUtils.assertLexerSingleToken("[", Sym.T_LBRACKET);
	}

	@Test
	public void testGetTokenLparen() {
		TestUtils.assertLexerSingleToken("(", Sym.T_LPAREN);
	}

	@Test
	public void testGetTokenLt() {
		TestUtils.assertLexerSingleToken("<", Sym.T_LT);
	}

	@Test
	public void testGetTokenLte() {
		TestUtils.assertLexerSingleToken("<=", Sym.T_LTE);
	}

	@Test
	public void testGetTokenMinus() {
		TestUtils.assertLexerSingleToken("-", Sym.T_MINUS);
	}

	@Test
	public void testGetTokenNeq() {
		TestUtils.assertLexerSingleToken("#", Sym.T_NEQ);
	}

	@Test
	public void testGetTokenPlus() {
		TestUtils.assertLexerSingleToken("+", Sym.T_PLUS);
	}

	@Test
	public void testGetTokenRbrace() {
		TestUtils.assertLexerSingleToken("}", Sym.T_RBRACE);
	}

	@Test
	public void testGetTokenRbracket() {
		TestUtils.assertLexerSingleToken("]", Sym.T_RBRACKET);
	}

	@Test
	public void testGetTokenRparen() {
		TestUtils.assertLexerSingleToken(")", Sym.T_RPAREN);
	}

	@Test
	public void testGetTokenSemi() {
		TestUtils.assertLexerSingleToken(";", Sym.T_SEMI);
	}

	@Test
	public void testGetTokenTilde() {
		TestUtils.assertLexerSingleToken("~", Sym.T_TILDE);
	}

	@Test
	public void testGetTokenSlash() {
		TestUtils.assertLexerSingleToken("/", Sym.T_SLASH);
	}

	@Test
	public void testGetTokenStar() {
		TestUtils.assertLexerSingleToken("*", Sym.T_STAR);
	}
}