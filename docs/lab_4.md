# Java and JUnit Introduction

Modified from http://cseweb.ucsd.edu/classes/fa00/cse131a/

## Java and JUnit

The primary purpose of this lab is to learn more about java using by writing a portion of a Oberon Lexer.

If you are unfamiliar with Java, you can find a long list of tutorials at Guru99 (<https://www.guru99.com/java-tutorial.html>). At least take a look at the difference between [Java and C++](https://www.guru99.com/cpp-vs-java.html). I will leave it to your discretion on how much time you want to play with these tutorials.

For this lab you will need to complete the lexer for **Keywords**, **Punctuation**, **Comments**, and **Strings**.
The other Tokens are not required to be completed.

## Lexical Analysis

For this assignment, we will be building the lexical analysis component of a compiler, often referred to as a _lexer_.
A lexer performs three basic functions:

1. Recognize patterns in the input stream (such patterns are called _lexemes_), and pass internal representations of lexemes (called tokens) on for later processing.
   Examples of tokens are keywords (such as **IF**, **ELSE**, **RETURN**, etc), punctuation symbols (such as semicolons, periods, commas, also two-character symbols like assignment ( := ) or greater-than-or-equal ( >= )), literals (such as **44**, **7.0**, or **&quot;Hello&quot;**), and user-defined identifiers.
   The [Oberon Token Specification](tokens.md) specifies the complete set of Oberon tokens and their patterns.
1. Discard input characters that are not part of any lexeme.
   (In other words, ignore characters that we don't care about).
   This would include comments, whitespace characters (spaces, tabs, newlines) and any illegal characters that may be read.
1. Report any lexical errors encountered.

### Specification

The lexer must be implemented as a class called **Lexer** which contains a public method called **getToken()**.
This method returns an object of type **Token**.

The specification for **getToken** is:

```Java
public Token getToken()
```

We provide four files -- _sym.java_, containing the valid token codes, _Token.java_, containing the Token class to be returned by **GetToken()**, _ILexer.java_, the Lexer interface, and _LexerTester.java_, a test program for your lexer.
A program class (**Oberon**), whose main() method instantiates a **Lexer** instance and then passes that object to a newly instantiated **LexerTester** instance (as shown in _LexerTester.java_).

### Notes

* Oberon is case-sensitive.
All Oberon keywords consist solely of uppercase letters.
Thus, the lexeme **ELSE** should be recognized as the else keyword, while the lexeme **else** should be considered an identifier.
* Comments in Oberon are delimited by two pairs of strings, (\* and \*).
Comments can be nested, thus the input ** (\* this is (\* one \*) comment \*) ** is indeed a single comment and should be treated as such.
Also note that a comment is considered a separator -- thus, an input consisting of **ELS(\*comment\*)IF** (with no spaces) should be processed as two tokens (the identifier ELS and the keyword IF) and not the single keyword ELSIF.

### Extra Credit

Extra credit will be given to students to who improve the project tests.
You can get extra credit for:

* Improving documentation or assignment description
* Improving a test
* Fixing a test that is incorrect
* Adding a test to increase the test coverage (add a missing LEXEME)

The improvement (example: new test) should be submitted through Gitlab's merge request with only the improvement code change.
Only if the improvement is accepted will the student get extra credit.

### Turn In Instructions

You will turn in your source code (not your executables) to us electronically via gitlab, and we will build and run your project.
Therefore, it is very important that you adhere to the following.

* Make sure all the test are passing before in for project.
* Do not change the way **Oberon** main class functions.

Once your project is submitted for grading, the autograder will run it by executing the command **oberon**.
