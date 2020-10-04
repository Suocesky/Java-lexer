package edu.wallawalla.cs.classes.cptr354.lexer;

import static org.junit.Assert.assertEquals;

import edu.ucsb.cseweb.classes.cse131a.lexer.Sym;
import edu.ucsb.cseweb.classes.cse131a.lexer.Token;
import org.junit.Test;

public class SpecialTokenStringTest {

  private Token token;

  @Test
  public void testGetTokenStringTooLong() {
    Lexer lexer = new Lexer(
        "'This test string is much, much longer that 80 characters. Would you not agree? I would.';");
    token = lexer.getToken();
    assertEquals(token.GetType(), Sym.T_STR_LITERAL);
    assertEquals(
        token.GetLexeme(),
        "This test string is much, much longer that 80 characters. Would you not agree? ");
  }

  @Test
  public void testGetTokenStringEOF() {
    Lexer lexer = new Lexer(
        "'This test string is much, much longer that 80 characters. Would you not agree? I would. EOF;");
    token = lexer.getToken();
    assertEquals(token.GetType(), Sym.T_STR_LITERAL);
    assertEquals(
        token.GetLexeme(),
        "This test string is much, much longer that 80 characters. Would you not agree? ");
  }

  @Test
  public void testGetTokenStringNewLine() {
    Lexer lexer = new Lexer(
        "'This test string is much, much longer that 80 characters. Would you not agree? I would.\n;");
    token = lexer.getToken();
    assertEquals(token.GetType(), Sym.T_STR_LITERAL);
    assertEquals(
        token.GetLexeme(),
        "This test string is much, much longer that 80 characters. Would you not agree? ");
  }

  @Test
  public void testGetTokenStringDoubleSingle() {
    Lexer lexer = new Lexer("\"test 'in single quotes.'\";");
    token = lexer.getToken();
    assertEquals(token.GetType(), Sym.T_STR_LITERAL);
    assertEquals(token.GetLexeme(), "test 'in single quotes.'");
  }

  @Test
  public void testGetTokenStringSingleDouble() {
    Lexer lexer = new Lexer("'test \"in double quotes.\"';");
    token = lexer.getToken();
    assertEquals(token.GetType(), Sym.T_STR_LITERAL);
    assertEquals(token.GetLexeme(), "test \"in double quotes.\"");
  }

}