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
  String sourceString = "";
  int startString = 0;

  private void readerToString() {
    try {
      // Get the character
      // to be read from the stream
      int ch = 0;

      // Read the first character
      // to this reader using read() method
      while (ch != -1) {
        ch = reader.read();
        if (ch != -1)
          sourceString += (char) ch;
        System.out.println(sourceString);
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  /**
   * Constructors
   */
  public Lexer(Reader input) {
    reader = new BufferedReader(input);
    readerToString();
  }

  public Lexer(Path filepath) throws FileNotFoundException {
    this(new FileReader(filepath.toString()));
    readerToString();
  }

  public Lexer(String inputString) {
    this(new StringReader(inputString));
    readerToString();
  }

  public Lexer() {
    this(new InputStreamReader(System.in));
    readerToString();
  }

  private Token smartSwitch(int offset, int endString, String fullMatch, int smallToken, int largeToken) {
    if (endString+offset <= sourceString.length() && sourceString.substring(startString, endString+offset).equals(fullMatch)) { 
      return new Token(largeToken, sourceString.substring(startString, endString+offset));
    }
    else {
      return new Token(smallToken, sourceString.substring(startString, endString));
    }
  }

  public Token getToken() {
    int endString = startString+1;
    String tokenString;
    while (endString <= sourceString.length()) {
      try {
        tokenString = sourceString.substring(startString, endString);
        // Sample for checking a single character Token.
        switch (tokenString) {
          // Keywords
          case "ARRAY":
            return new Token(Sym.T_ARRAY, tokenString);
          case "BEGIN":
            return new Token(Sym.T_BEGIN, tokenString);
          case "BY":
            return new Token(Sym.T_BY, tokenString);
          case "CASE":
            return new Token(Sym.T_CASE, tokenString);
          case "CONST":
            return new Token(Sym.T_CONST, tokenString);
          case "DIV":
            return new Token(Sym.T_DIV, tokenString);
          case "DO":
            return new Token(Sym.T_DO, tokenString);
          case "ELSE":
            return new Token(Sym.T_ELSE, tokenString);
          case "ELSIF":
            return new Token(Sym.T_ELSIF, tokenString);
          case "END":
            return new Token(Sym.T_END, tokenString);
          case "EXIT":
            return new Token(Sym.T_EXIT, tokenString);
          case "FOR":
            return new Token(Sym.T_FOR, tokenString);
          case "IF":
            return new Token(Sym.T_IF, tokenString);
          case "IMPORT":
            return new Token(Sym.T_IMPORT, tokenString);
          case "IN":
            return smartSwitch(5, endString, "INTEGER", Sym.T_IN, Sym.T_INTEGER);
          case "IS":
            return new Token(Sym.T_IS, tokenString);
          case "LOOP":
            return new Token(Sym.T_LOOP, tokenString);
          case "MOD":
            return smartSwitch(3, endString, "MODULE", Sym.T_MOD, Sym.T_MODULE);
          case "NIL":
            return new Token(Sym.T_NIL, tokenString);
          case "OF":
            return new Token(Sym.T_OF, tokenString);
          case "OR":
            return new Token(Sym.T_OR, tokenString);
          case "POINTER":
            return new Token(Sym.T_POINTER, tokenString);
          case "PROCEDURE":
            return new Token(Sym.T_PROCEDURE, tokenString);
          case "RECORD":
            return new Token(Sym.T_RECORD, tokenString);
          case "REPEAT":
            return new Token(Sym.T_REPEAT, tokenString);
          case "RETURN":
            return new Token(Sym.T_RETURN, tokenString);
          case "THEN":
            return new Token(Sym.T_THEN, tokenString);
          case "TO":
            return new Token(Sym.T_TO, tokenString);
          case "TYPE":
            return new Token(Sym.T_TYPE, tokenString);
          case "UNTIL":
            return new Token(Sym.T_UNTIL, tokenString);
          case "VAR":
            return new Token(Sym.T_VAR, tokenString);
          case "WHILE":
            return new Token(Sym.T_WHILE, tokenString);
          case "WITH":
            return new Token(Sym.T_WITH, tokenString);
          // Predeclared identifiers
          case "BOOLEAN":
            return new Token(Sym.T_BOOLEAN, tokenString);
          case "CHAR":
            return new Token(Sym.T_CHAR, tokenString);
          case "FALSE":
            return new Token(Sym.T_FALSE, tokenString);
          case "NEW":
            return new Token(Sym.T_NEW, tokenString);
          case "REAL":
            return new Token(Sym.T_REAL, tokenString);
          case "TRUE":
            return new Token(Sym.T_TRUE, tokenString);
          // Punctuation
          case "&":
            return new Token(Sym.T_AMPERSAND, tokenString);
          case "^":
            return new Token(Sym.T_ARROW, tokenString);
          case ":":
          return smartSwitch(1, endString, ":=", Sym.T_COLON, Sym.T_ASSIGN);
          // return new Token(Sym.T_COLON, tokenString);
          case "|":
            return new Token(Sym.T_BAR, tokenString);
          case ",":
            return new Token(Sym.T_COMMA, tokenString);
          case ".":
            return smartSwitch(1, endString, "..", Sym.T_DOT, Sym.T_DOTDOT);
            // return new Token(Sym.T_DOT, tokenString);
          case "..":
            return new Token(Sym.T_DOTDOT, tokenString);
          case "=":
            return new Token(Sym.T_EQU, tokenString);
          case ">":
            return smartSwitch(1, endString, ">=", Sym.T_GT, Sym.T_GTE);
          case "{":
            return new Token(Sym.T_LBRACE, tokenString);
          case "[":
            return new Token(Sym.T_LBRACKET, tokenString);
          case "(":
            return new Token(Sym.T_LPAREN, tokenString);
          case "<":
            return smartSwitch(1, endString, "<=", Sym.T_LT, Sym.T_LTE);
          case "-":
            return new Token(Sym.T_MINUS, tokenString);
          case "#":
            return new Token(Sym.T_NEQ, tokenString);
          case "+":
            return new Token(Sym.T_PLUS, tokenString);
          case "}":
            return new Token(Sym.T_RBRACE, tokenString);
          case "]":
            return new Token(Sym.T_RBRACKET, tokenString);
          case ")":
            return new Token(Sym.T_RPAREN, tokenString);
          case ";":
            return new Token(Sym.T_SEMI, tokenString);
          case "~":
            return new Token(Sym.T_TILDE, tokenString);
          case "/":
            return new Token(Sym.T_SLASH, tokenString);
          case "*":
            return new Token(Sym.T_STAR, tokenString);

        }

      } catch (Exception e) {
        System.out.println(e);
      }
      endString++;
    }
    startString = endString;
    return null;
  }

}
