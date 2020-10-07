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
import edu.ucsb.cseweb.classes.cse131a.lexer.Token;

public class Lexer implements ILexer {
  Reader reader;

  List<Token> TokensList = new ArrayList<Token>();

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

    return null;
  }

}
