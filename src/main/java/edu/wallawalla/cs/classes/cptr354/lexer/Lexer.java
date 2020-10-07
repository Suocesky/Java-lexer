package edu.wallawalla.cs.classes.cptr354.lexer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import edu.ucsb.cseweb.classes.cse131a.lexer.ILexer;
import edu.ucsb.cseweb.classes.cse131a.lexer.Sym;
import edu.ucsb.cseweb.classes.cse131a.lexer.Token;

public class Lexer implements ILexer {
  Reader reader;

  /**
   * Constructors
   */
  public Lexer(Reader input) { reader = new BufferedReader(input); }

  public Lexer(Path filepath) throws FileNotFoundException {
    this(new FileReader(filepath.toString()));
  }

  public Lexer(String inputString) { this(new StringReader(inputString)); }

  public Lexer() { this(new InputStreamReader(System.in)); }

  public Token getToken() {
    // TODO add your call to the parser to get individual tokens.
    String tokenString = "";

    try { 
      // Get the character 
      // to be read from the stream 
      int ch; 
  
      // Read the first character 
      // to this reader using read() method 
      ch = reader.read(); 
      tokenString += (char)ch;
      System.out.println(tokenString);

      // Sample for checking a single character Token.
      if (tokenString.equals(";")) {
    	  return new Token(Sym.T_SEMI, tokenString);
      }
    } 
    catch (Exception e) { 
      System.out.println(e); 
    }

    return null;
  }

}
