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
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
        if (ch != -1) {
          sourceString += (char) ch;
          System.out.println(sourceString);
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  private void removeUnessarySpaces() {
    String regex = "\\s+(?=((\\\\[\\\\\"]|[^\\\\\"])*\"(\\\\[\\\\\"]|[^\\\\\"])*\")*(\\\\[\\\\\"]|[^\\\\\"])*$)";
    sourceString = sourceString.replaceAll(regex, "");
  }

  private String removecom(){
    String newString = "";

    if (sourceString.contains("\"")) {
      if (sourceString.indexOf("\"") != 0) {

        String[] stringParts = sourceString.split("\"");

        for (int i = 0; i < stringParts.length; i++) {

          if ((i & 1) == 0) {
            Pattern commentaryPattern = Pattern.compile("(/\\*((.|\n)*?)\\*/)|//.*");

            Matcher m = commentaryPattern.matcher(stringParts[i]);

            sourceString += m.replaceAll("");
          } else {
            sourceString += "\"" + stringParts[i] + "\"";
          }
        }
      }
    } else {
      Pattern commentaryPattern = Pattern.compile("(/\\*((.|\n)*?)\\*/)|//.*");

      Matcher m = commentaryPattern.matcher(sourceString);

      newString += m.replaceAll("");
    }
    return newString;
  }

  /**
   * Constructors
   */
  public Lexer(Reader input) {
    reader = new BufferedReader(input);
    readerToString();
    removecom();
    removeUnessarySpaces();
  }

  public Lexer(Path filepath) throws FileNotFoundException {
    this(new FileReader(filepath.toString()));
    readerToString();
    removecom();
    removeUnessarySpaces();
  }

  public Lexer(String inputString) {
    this(new StringReader(inputString));
    readerToString();
    removecom();
    removeUnessarySpaces();
  }

  public Lexer() {
    this(new InputStreamReader(System.in));
    readerToString();
    removecom();
    removeUnessarySpaces();
  }

  private Token smartSwitch(int offset, int endString, String fullMatch, int smallToken, int largeToken) {
    if (endString + offset <= sourceString.length()
        && sourceString.substring(startString, endString + offset).equals(fullMatch)) {
      return new Token(largeToken, sourceString.substring(startString, endString + offset));
    } else {
      return new Token(smallToken, sourceString.substring(startString, endString));
    }
  }

  public Token getToken() {
    int endString = startString + 1;
    String tokenString;
    Token returnToken = null;
    while (endString <= sourceString.length()) {
      try {
        tokenString = sourceString.substring(startString, endString);
        switch (tokenString) {
          // Keywords
          case "ARRAY":
            returnToken = new Token(Sym.T_ARRAY, tokenString);
            break;
          case "BEGIN":
            returnToken = new Token(Sym.T_BEGIN, tokenString);
            break;
          case "BY":
            returnToken = new Token(Sym.T_BY, tokenString);
            break;
          case "CASE":
            returnToken = new Token(Sym.T_CASE, tokenString);
            break;
          case "CONST":
            returnToken = new Token(Sym.T_CONST, tokenString);
            break;
          case "DIV":
            returnToken = new Token(Sym.T_DIV, tokenString);
            break;
          case "DO":
            returnToken = new Token(Sym.T_DO, tokenString);
            break;
          case "ELSE":
            returnToken = new Token(Sym.T_ELSE, tokenString);
            break;
          case "ELSIF":
            returnToken = new Token(Sym.T_ELSIF, tokenString);
            break;
          case "END":
            returnToken = new Token(Sym.T_END, tokenString);
            break;
          case "EXIT":
            returnToken = new Token(Sym.T_EXIT, tokenString);
            break;
          case "FOR":
            returnToken = new Token(Sym.T_FOR, tokenString);
            break;
          case "IF":
            returnToken = new Token(Sym.T_IF, tokenString);
            break;
          case "IMPORT":
            returnToken = new Token(Sym.T_IMPORT, tokenString);
            break;
          case "IN":
            returnToken = smartSwitch(5, endString, "INTEGER", Sym.T_IN, Sym.T_INTEGER);
            break;
          case "IS":
            returnToken = new Token(Sym.T_IS, tokenString);
            break;
          case "LOOP":
            returnToken = new Token(Sym.T_LOOP, tokenString);
            break;
          case "MOD":
            returnToken = smartSwitch(3, endString, "MODULE", Sym.T_MOD, Sym.T_MODULE);
            break;
          case "NIL":
            returnToken = new Token(Sym.T_NIL, tokenString);
            break;
          case "OF":
            returnToken = new Token(Sym.T_OF, tokenString);
            break;
          case "OR":
            returnToken = new Token(Sym.T_OR, tokenString);
            break;
          case "POINTER":
            returnToken = new Token(Sym.T_POINTER, tokenString);
            break;
          case "PROCEDURE":
            returnToken = new Token(Sym.T_PROCEDURE, tokenString);
            break;
          case "RECORD":
            returnToken = new Token(Sym.T_RECORD, tokenString);
            break;
          case "REPEAT":
            returnToken = new Token(Sym.T_REPEAT, tokenString);
            break;
          case "RETURN":
            returnToken = new Token(Sym.T_RETURN, tokenString);
            break;
          case "THEN":
            returnToken = new Token(Sym.T_THEN, tokenString);
            break;
          case "TO":
            returnToken = new Token(Sym.T_TO, tokenString);
            break;
          case "TYPE":
            returnToken = new Token(Sym.T_TYPE, tokenString);
            break;
          case "UNTIL":
            returnToken = new Token(Sym.T_UNTIL, tokenString);
            break;
          case "VAR":
            returnToken = new Token(Sym.T_VAR, tokenString);
            break;
          case "WHILE":
            returnToken = new Token(Sym.T_WHILE, tokenString);
            break;
          case "WITH":
            returnToken = new Token(Sym.T_WITH, tokenString);
            break;
          // Predeclared identifiers
          case "BOOLEAN":
            returnToken = new Token(Sym.T_BOOLEAN, tokenString);
            break;
          case "CHAR":
            returnToken = new Token(Sym.T_CHAR, tokenString);
            break;
          case "FALSE":
            returnToken = new Token(Sym.T_FALSE, tokenString);
            break;
          case "NEW":
            returnToken = new Token(Sym.T_NEW, tokenString);
            break;
          case "REAL":
            returnToken = new Token(Sym.T_REAL, tokenString);
            break;
          case "TRUE":
            returnToken = new Token(Sym.T_TRUE, tokenString);
            break;
          // Punctuation
          case "&":
            returnToken = new Token(Sym.T_AMPERSAND, tokenString);
            break;
          case "^":
            returnToken = new Token(Sym.T_ARROW, tokenString);
            break;
          case ":":
            returnToken = smartSwitch(1, endString, ":=", Sym.T_COLON, Sym.T_ASSIGN);
            break;
          case "|":
            returnToken = new Token(Sym.T_BAR, tokenString);
            break;
          case ",":
            returnToken = new Token(Sym.T_COMMA, tokenString);
            break;
          case ".":
            returnToken = smartSwitch(1, endString, "..", Sym.T_DOT, Sym.T_DOTDOT);
            break;
          case "..":
            returnToken = new Token(Sym.T_DOTDOT, tokenString);
            break;
          case "=":
            returnToken = new Token(Sym.T_EQU, tokenString);
            break;
          case ">":
            returnToken = smartSwitch(1, endString, ">=", Sym.T_GT, Sym.T_GTE);
            break;
          case "{":
            returnToken = new Token(Sym.T_LBRACE, tokenString);
            break;
          case "[":
            returnToken = new Token(Sym.T_LBRACKET, tokenString);
            break;
          case "(":
            returnToken = new Token(Sym.T_LPAREN, tokenString);
            break;
          case "<":
            returnToken = smartSwitch(1, endString, "<=", Sym.T_LT, Sym.T_LTE);
            break;
          case "-":
            returnToken = new Token(Sym.T_MINUS, tokenString);
            break;
          case "#":
            returnToken = new Token(Sym.T_NEQ, tokenString);
            break;
          case "+":
            returnToken = new Token(Sym.T_PLUS, tokenString);
            break;
          case "}":
            returnToken = new Token(Sym.T_RBRACE, tokenString);
            break;
          case "]":
            returnToken = new Token(Sym.T_RBRACKET, tokenString);
            break;
          case ")":
            returnToken = new Token(Sym.T_RPAREN, tokenString);
            break;
          case ";":
            returnToken = new Token(Sym.T_SEMI, tokenString);
            break;
          case "~":
            returnToken = new Token(Sym.T_TILDE, tokenString);
            break;
          case "/":
            returnToken = new Token(Sym.T_SLASH, tokenString);
            break;
          case "*":
            returnToken = new Token(Sym.T_STAR, tokenString);
            break;
          // Special tokens
          default:
            // String literals
            // ISSUE: string methods such as substring() and charAt() seem to change tokenString's length
            if (tokenString == "''") {
              return new Token(Sym.T_STR_LITERAL, "");
            } else if (tokenString.substring(0, 1) == "'" && (tokenString.length() < 80)) {
              return new Token(Sym.T_STR_LITERAL, tokenString);
            } else if (tokenString == "\"\"") {
              return new Token(Sym.T_STR_LITERAL, "");
            } else if (tokenString.substring(0, 1) == "\"" && (tokenString.length() < 80)) {
              return new Token(Sym.T_STR_LITERAL, tokenString);
            } else {
              if (tokenString.indexOf("EOF") != -1) {
                System.out.print("unterminated literal, EOF in string");
                return new Token(Sym.T_STR_LITERAL, tokenString.substring(1, 80));
              } else if (tokenString.indexOf("\n") != -1) {
                System.out.print("unterminated literal, newline in string");
                return new Token(Sym.T_STR_LITERAL, tokenString.substring(1, 80));
              } else {
                System.out.print("unterminated literal");
                return new Token(Sym.T_STR_LITERAL, tokenString.substring(1, 80));
              }
            }
        }
      } catch (Exception e) {
        System.out.println(e);
      }
      endString++;
    }
    startString = endString - 2;
    return returnToken;
  }

}
