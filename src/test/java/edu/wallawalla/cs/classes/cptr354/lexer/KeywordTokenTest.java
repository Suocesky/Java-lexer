package edu.wallawalla.cs.classes.cptr354.lexer;

import org.junit.Test;

import edu.ucsb.cseweb.classes.cse131a.lexer.Sym;

public class KeywordTokenTest {

	@Test
	public void testGetTokenKeywordARRAY() {
		TestUtils.assertLexerSingleToken("ARRAY", Sym.T_ARRAY);
	}
	
	@Test
	public void testGetTokenKeywordBEGIN() {
		TestUtils.assertLexerSingleToken("BEGIN", Sym.T_BEGIN);
	}	

    @Test
	public void testGetTokenKeywordBY() {
		TestUtils.assertLexerSingleToken("BY", Sym.T_BY);
	}
	
	@Test
	public void testGetTokenKeywordCASE() {
		TestUtils.assertLexerSingleToken("CASE", Sym.T_CASE);
	}
	
	@Test
	public void testGetTokenKeywordCONST() {
		TestUtils.assertLexerSingleToken("CONST", Sym.T_CONST);
	}
	
	@Test
	public void testGetTokenKeywordDIV() {
		TestUtils.assertLexerSingleToken("DIV", Sym.T_DIV);
	}
	
	@Test
	public void testGetTokenKeywordDO() {
		TestUtils.assertLexerSingleToken("DO", Sym.T_DO);
	}
	
	@Test
	public void testGetTokenKeywordELSE() {
		TestUtils.assertLexerSingleToken("ELSE", Sym.T_ELSE);
	}
	
	@Test
	public void testGetTokenKeywordELSIF() {
		TestUtils.assertLexerSingleToken("ELSIF", Sym.T_ELSIF);
	}
	
	@Test
	public void testGetTokenKeywordEND() {
		TestUtils.assertLexerSingleToken("END", Sym.T_END);
	}
	
	@Test
	public void testGetTokenKeywordEXIT() {
		TestUtils.assertLexerSingleToken("EXIT", Sym.T_EXIT);
	}
	
	@Test
	public void testGetTokenKeywordFOR() {
		TestUtils.assertLexerSingleToken("FOR", Sym.T_FOR);
	}
	
	@Test
	public void testGetTokenKeywordIF() {
		TestUtils.assertLexerSingleToken("IF", Sym.T_IF);
	}
	
	@Test
	public void testGetTokenKeywordIMPORT() {
		TestUtils.assertLexerSingleToken("IMPORT", Sym.T_IMPORT);
	}
	
	@Test
	public void testGetTokenKeywordIN() {
		TestUtils.assertLexerSingleToken("IN", Sym.T_IN);
	}
	
	@Test
	public void testGetTokenKeywordIS() {
		TestUtils.assertLexerSingleToken("IS", Sym.T_IS);
	}
	
	@Test
	public void testGetTokenKeywordLOOP() {
		TestUtils.assertLexerSingleToken("LOOP", Sym.T_LOOP);
	}
	
	@Test
	public void testGetTokenKeywordMOD() {
		TestUtils.assertLexerSingleToken("MOD", Sym.T_MOD);
	}
	
	@Test
	public void testGetTokenKeywordMODULE() {
		TestUtils.assertLexerSingleToken("MODULE", Sym.T_MODULE);
	}
	
	@Test
	public void testGetTokenKeywordNIL() {
		TestUtils.assertLexerSingleToken("NIL", Sym.T_NIL);
	}
	
	@Test
	public void testGetTokenKeywordOF() {
		TestUtils.assertLexerSingleToken("OF", Sym.T_OF);
	}
	
	@Test
	public void testGetTokenKeywordOR() {
		TestUtils.assertLexerSingleToken("OR", Sym.T_OR);
	}
	
	@Test
	public void testGetTokenKeywordPOINTER() {
		TestUtils.assertLexerSingleToken("POINTER", Sym.T_POINTER);
	}
	
	@Test
	public void testGetTokenKeywordPROCEDURE() {
		TestUtils.assertLexerSingleToken("PROCEDURE", Sym.T_PROCEDURE);
	}
		
	@Test
	public void testGetTokenKeywordRECORD() {
		TestUtils.assertLexerSingleToken("RECORD", Sym.T_RECORD);
	}
		
	@Test
	public void testGetTokenKeywordREPEAT() {
		TestUtils.assertLexerSingleToken("REPEAT", Sym.T_REPEAT);
	}
		
	@Test
	public void testGetTokenKeywordRETURN() {
		TestUtils.assertLexerSingleToken("RETURN", Sym.T_RETURN);
	}
		
	@Test
	public void testGetTokenKeywordTHEN() {
		TestUtils.assertLexerSingleToken("THEN", Sym.T_THEN);
	}
		
	@Test
	public void testGetTokenKeywordTO() {
		TestUtils.assertLexerSingleToken("TO", Sym.T_TO);
	}
		
	@Test
	public void testGetTokenKeywordTYPE() {
		TestUtils.assertLexerSingleToken("TYPE", Sym.T_TYPE);
	}
		
	@Test
	public void testGetTokenKeywordUNTIL() {
		TestUtils.assertLexerSingleToken("UNTIL", Sym.T_UNTIL);
	}
		
	@Test
	public void testGetTokenKeywordVAR() {
		TestUtils.assertLexerSingleToken("VAR", Sym.T_VAR);
	}
		
	@Test
	public void testGetTokenKeywordWHILE() {
		TestUtils.assertLexerSingleToken("WHILE", Sym.T_WHILE);
	}
		
	@Test
	public void testGetTokenKeywordWITH() {
		TestUtils.assertLexerSingleToken("WITH", Sym.T_WITH);
	}
		
	@Test
	public void testGetTokenKeywordBOOLEAN() {
		TestUtils.assertLexerSingleToken("BOOLEAN", Sym.T_BOOLEAN);
	}
		
	@Test
	public void testGetTokenKeywordCHAR() {
		TestUtils.assertLexerSingleToken("CHAR", Sym.T_CHAR);
	}
		
	@Test
	public void testGetTokenKeywordFALSE() {
		TestUtils.assertLexerSingleToken("FALSE", Sym.T_FALSE);
	}
		
	@Test
	public void testGetTokenKeywordINTEGER() {
		TestUtils.assertLexerSingleToken("INTEGER", Sym.T_INTEGER);
	}
		
	@Test
	public void testGetTokenKeywordNEW() {
		TestUtils.assertLexerSingleToken("NEW", Sym.T_NEW);
	}
		
	@Test
	public void testGetTokenKeywordREAL() {
		TestUtils.assertLexerSingleToken("REAL", Sym.T_REAL);
	}
		
	@Test
	public void testGetTokenKeywordTRUE() {
		TestUtils.assertLexerSingleToken("TRUE", Sym.T_TRUE);
	}
}