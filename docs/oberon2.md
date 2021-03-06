Taken from: https://cseweb.ucsd.edu/~wgg/CSE131B/oberon2.htm

# The Programming Language Oberon-2

<address>H. Mössenböck, N. Wirth<br>
Institut für Computersysteme, ETH Zürich<br>
October 1993</address>

## 1. Introduction

Oberon-2 is a general-purpose language in the tradition of Oberon and Modula-2.
Its most important features are block structure, modularity, separate
compilation, static typing with strong type checking (also across module
boundaries), and type extension with type-bound procedures.

Type extension makes Oberon-2 an object-oriented language. An object is a
variable of an abstract data type consisting of private data (its state) and
procedures that operate on this data. Abstract data types are declared as
extensible records. Oberon-2 covers most terms of object-oriented languages by
the established vocabulary of imperative languages in order to minimize the
number of notions for similar concepts.

This report is not intended as a programmer's tutorial. It is intentionally kept
concise. Its function is to serve as a reference for programmers, implementors,
and manual writers. What remains unsaid is mostly left so intentionally, either
because it can be derived from stated rules of the language, or because it would
require to commit the definition when a general commitment appears as unwise.

Appendix A defines some terms that are used to express the type checking rules of
Oberon-2. Where they appear in the text, they are written in italics to indicate
their special meaning (e.g. the _same_ type).

## 2. Syntax

An extended Backus-Naur Formalism (EBNF) is used to describe the syntax of
Oberon-2: Alternatives are separated by |. Brackets [ and ] denote optionality of
the enclosed expression, and braces { and } denote its repetition (possibly 0
times). Non-terminal symbols start with an upper-case letter (e.g. Statement).
Terminal symbols either start with a lower-case letter (e.g. ident), or are
written all in upper-case letters (e.g. BEGIN), or are denoted by strings (e.g.
&quot;:=&quot;).

## 3. Vocabulary and Representation

The representation of (terminal) symbols in terms of characters is defined using
the ASCII set. Symbols are identifiers, numbers, strings, operators, and
delimiters. The following lexical rules must be observed: Blanks and line breaks
must not occur within symbols (except in comments, and blanks in strings). They
are ignored unless they are essential to separate two consecutive symbols.
Capital and lower-case letters are considered as distinct.

1. _Identifiers_ are sequences of letters and digits. The first character
must be a letter.

<pre>ident = letter {letter | digit}.</pre>

Examples:
<pre>x
Scan
Oberon2
GetSymbol
firstLetter</pre>

2. _Numbers_ are (unsigned) integer or real constants. The type of an
integer constant is the minimal type to which the constant value belongs (<a HREF="#Sec6.1">see 6.1</a>). If the constant is specified with the suffix H,
the representation is hexadecimal otherwise the representation is decimal.

A real number always contains a decimal point. Optionally it may also contain a
decimal scale factor. The letter E (or D) means &quot;times ten to the power of&quot;. A
real number is of type REAL, unless it has a scale factor containing the letter
D. In this case it is of type LONGREAL.

<pre>number      = integer | real.
integer     = digit {digit} | digit {hexDigit} &quot;H&quot;.
real        = digit {digit} &quot;.&quot; {digit} [ScaleFactor].
ScaleFactor = (&quot;E&quot; | &quot;D&quot;) [&quot;+&quot; | &quot;-&quot;] digit {digit}.
hexDigit    = digit | &quot;A&quot; | &quot;B&quot; | &quot;C&quot; | &quot;D&quot; | &quot;E&quot; | &quot;F&quot;.
digit       = &quot;0&quot; | &quot;1&quot; | &quot;2&quot; | &quot;3&quot; | &quot;4&quot; | &quot;5&quot; | &quot;6&quot; | &quot;7&quot; | &quot;8&quot; | &quot;9&quot;.</pre>

Examples:

<pre>1991             INTEGER     1991
0DH              SHORTINT    13
12.3             REAL        12.3
4.567E8          REAL        456700000
0.57712566D-6    LONGREAL    0.00000057712566</pre>

3. _Character_ constants are denoted by the ordinal number of the character
in hexadecimal notation followed by the letter X.

<pre>character = digit {hexDigit} &quot;X&quot;.</pre>

4. _Strings_ are sequences of characters enclosed in single (') or double
(&quot;) quote marks. The opening quote must be the same as the closing quote and must
not occur within the string. The number of characters in a string is called its
_length_. A string of length 1 can be used wherever a character constant is
allowed and vice versa.

<pre>string = ' &quot; ' {char} ' &quot; ' | &quot; ' &quot; {char} &quot; ' &quot;.</pre>

Examples:

<pre>&quot;Oberon-2&quot;
&quot;Don't worry!&quot;
&quot;x&quot;</pre>

5. _Operators_ and _delimiters_ are the special characters, character
pairs, or reserved words listed below. The reserved words consist exclusively of
capital letters and cannot be used as identifiers.

<pre>+    :=    ARRAY    IMPORT       RETURN
-    ^     BEGIN    IN           THEN
*    =     BY       IS           TO
/    #     CASE     LOOP         TYPE
~    <const MOD UNTIL &>     DIV      MODULE       VAR
.    <= DO NIL WHILE ,>=    ELSE     OF           WITH
;    ..    ELSIF    OR
|    :     END      POINTER
(    )     EXIT     PROCEDURE
[    ]     FOR      RECORD
{    }     IF       REPEAT</pre>

6. _Comments_ may be inserted between any two symbols in a program. They are
arbitrary character sequences opened by the bracket (* and closed by *). Comments
may be nested. They do not affect the meaning of a program.

<a NAME="Cha4">## 4. Declarations and scope rules</a>

Every identifier occurring in a program must be introduced by a declaration,
unless it is a predeclared identifier. Declarations also specify certain
permanent properties of an object, such as whether it is a constant, a type, a
variable, or a procedure. The identifier is then used to refer to the associated
object.

The _scope_ of an object _x_ extends textually from the point of its
declaration to the end of the block (module, procedure, or record) to which the
declaration belongs and hence to which the object is _local_. It excludes
the scopes of equally named objects which are declared in nested blocks. The
scope rules are:

<ol>
<li>No identifier may denote more than one object within a given scope (i.e. no
identifier may be declared twice in a block);
<li>An object may only be referenced within its scope;
<li>A type_T_ of the form POINTER TO _T1_ (<a HREF="#Sec6.4">see
6.4</a>) can be declared at a point where _T1_ is still unknown. The
declaration of _T1_ must follow in the same block to which _T_ is
local;
<li>Identifiers denoting record fields (<a HREF="#Sec6.3">see 6.3</a>) or
type-bound procedures (<a HREF="#Sec10.2">see 10.2</a>) are valid in record
designators only.
</ol>

An identifier declared in a module block may be followed by an export mark (&quot; * &quot;
or &quot; - &quot;) in its declaration to indicate that it is exported. An identifier
_x_ exported by a module _M_ may be used in other modules, if they
import _M_ (<a HREF="#Cha11">see Ch.11</a>). The identifier is then
denoted as _M.x_ in these modules and is called a _qualified
identifier_. Identifiers marked with &quot; - &quot; in their declaration are
_read-only_ in importing modules.

<pre>Qualident = [ident &quot;.&quot;] ident.
IdentDef  = ident [&quot; * &quot; | &quot; - &quot;].</pre>

The following identifiers are predeclared; their meaning is defined in the
indicated sections:

<pre>ABS      (<a HREF="#Sec10.3">10.3</a>)    LEN   (<a HREF="#Sec10.3">10.3</a>)
ASH      (<a HREF="#Sec10.3">10.3</a>)    LONG  (<a HREF="#Sec10.3">10.3</a>)
BOOLEAN  (<a HREF="#Sec6.1">6.1</a>)     LONGINT  (<a HREF="#Sec6.1">6.1</a>)
CAP      (<a HREF="#Sec10.3">10.3</a>)    LONGREAL  (<a HREF="#Sec6.1">6.1</a>)
CHAR     (<a HREF="#Sec6.1">6.1</a>)     MAX  (<a HREF="#Sec10.3">10.3</a>)
CHR      (<a HREF="#Sec10.3">10.3</a>)    MIN  (<a HREF="#Sec10.3">10.3</a>)
COPY     (<a HREF="#Sec10.3">10.3</a>)    NEW  (<a HREF="#Sec10.3">10.3</a>)
DEC      (<a HREF="#Sec10.3">10.3</a>)    ODD  (<a HREF="#Sec10.3">10.3</a>)
ENTIER   (<a HREF="#Sec10.3">10.3</a>)    ORD  (<a HREF="#Sec10.3">10.3</a>)
EXCL     (<a HREF="#Sec10.3">10.3</a>)    REAL  (<a HREF="#Sec6.1">6.1</a>)
FALSE    (<a HREF="#Sec6.1">6.1</a>)     SET  (<a HREF="#Sec6.1">6.1</a>)
HALT     (<a HREF="#Sec10.3">10.3</a>)    SHORT  (<a HREF="#Sec10.3">10.3</a>)
INC      (<a HREF="#Sec10.3">10.3</a>)    SHORTINT  (<a HREF="#Sec6.1">6.1</a>)
INCL     (<a HREF="#Sec10.3">10.3</a>)    SIZE  (<a HREF="#Sec10.3">10.3</a>)
INTEGER  (<a HREF="#Sec6.1">6.1</a>)     TRUE  (<a HREF="#Sec6.1">6.1</a>)</pre>

## 5. Constant declarations

A constant declaration associates an identifier with a constant value.

<pre>ConstantDeclaration = IdentDef &quot;=&quot; ConstExpression.
ConstExpression     = Expression.</pre>

A constant expression is an expression that can be evaluated by a mere textual
scan without actually executing the program. Its operands are constants (<a HREF="#Cha8">Ch.8</a>) or predeclared functions (<a HREF="#Sec10.3">Ch.10.3</a>)
that can be evaluated at compile time. Examples of constant
declarations are:
<pre>N = 100
limit = 2*N - 1
fullSet = {MIN(SET) .. MAX(SET)}</pre>

<a NAME="Cha6">## 6. Type declarations</a>

A data type determines the set of values which variables of that type may assume,
and the operators that are applicable. A type declaration associates an
identifier with a type. In the case of structured types (arrays and records) it
also defines the structure of variables of this type. A structured type cannot
contain itself.

<pre>TypeDeclaration = IdentDef &quot;=&quot; Type.
Type            = Qualident | ArrayType | RecordType | PointerType
                | ProcedureType.</pre>

Examples:

<pre>Table = ARRAY N OF REAL
Tree = POINTER TO Node
Node = RECORD
  key: INTEGER;
  left, right: Tree
END
CenterTree = POINTER TO CenterNode
CenterNode = RECORD (Node)
  width: INTEGER;
  subnode: Tree
END
Function = PROCEDURE(x: INTEGER): INTEGER</pre>

<a NAME="Sec6.1"><h3>6.1 Basic types</h3></a>

The basic types are denoted by predeclared identifiers. The associated operators
are defined in <a HREF="#Sec8.2">8.2</a> and the predeclared function
procedures in <a HREF="#Sec10.3">10.3</a>. The values of the given basic
types are the following:

<pre>1. BOOLEAN     the truth values TRUE and FALSE
2. CHAR        the characters of the extended ASCII set (0X .. 0FFX)
3. SHORTINT    the integers between MIN(SHORTINT) and MAX(SHORTINT)
4. INTEGER     the integers between MIN(INTEGER) and MAX(INTEGER)
5. LONGINT     the integers between MIN(LONGINT) and MAX(LONGINT)
6. REAL        the real numbers between MIN(REAL) and MAX(REAL)
7. LONGREAL    the real numbers between MIN(LONGREAL) and MAX(LONGREAL)
8. SET         the sets of integers between 0 and MAX(SET)</pre>

Types 3 to 5 are _integer types_, types 6 and 7 are _real types_, and
together they are called _numeric types_. They form a hierarchy; the larger
type _includes_ (the values of) the smaller type:

LONGREAL &gt;= REAL &gt;= LONGINT &gt;= INTEGER &gt;= SHORTINT

<h3>6.2 Array types</h3>

An array is a structure consisting of a number of elements which are all of the
same type, called the _element type_. The number of elements of an array is
called its _length_. The elements of the array are designated by indices,
which are integers between 0 and the length minus 1.

<pre>ArrayType = ARRAY [Length {&quot;,&quot; Length}] OF Type.
Length    = ConstExpression.</pre>

A type of the form

```
ARRAY L0, L1, ..., Ln OF T
```

is understood as an abbreviation of

```
ARRAY L0 OF<br>
ARRAY L1 OF<br>
...<br>
ARRAY Ln OF T
```

Arrays declared without length are called _open arrays_. They are restricted
to pointer base types (<a HREF="#Sec6.4">see 6.4</a>), element types of open
array types, and formal parameter types (<a HREF="#Sec10.1">see 10.1</a>).
Examples:

<pre>ARRAY 10, N OF INTEGER
ARRAY OF CHAR</pre>

<a NAME="Sec6.3"><h3>6.3 Record types</h3></a>

A record type is a structure consisting of a fixed number of elements, called
_fields_, with possibly different types. The record type declaration
specifies the name and type of each field. The scope of the field identifiers
extends from the point of their declaration to the end of the record type, but
they are also visible within designators referring to elements of record
variables (<a HREF="#Sec8.1">see 8.1</a>). If a record type is exported,
field identifiers that are to be visible outside the declaring module must be
marked. They are called _public fields_; unmarked elements are called
_private fields_.

<pre>RecordType = RECORD [&quot;(&quot;BaseType&quot;)&quot;] FieldList {&quot;;&quot; FieldList} END.
BaseType   = Qualident.
FieldList  = [IdentList &quot;:&quot; Type ].</pre>

Record types are extensible, i.e. a record type can be declared as an extension
of another record type. In the example

```
T0 = RECORD x: INTEGER END<br> T1 = RECORD (T0) y: REAL END
```

_T1_ is a (direct) _extension_ of _T0_ and _T0_ is the
(direct) _base type_ of _T1_ (<a HREF="#AppA">see App. A</a>). An
extended type _T1_ consists of the fields of its base type and of the fields
which are declared in _T1_. All identifiers declared in the extended record
must be different from the identifiers declared in its base type record(s).

Examples of record type declarations:
<pre>RECORD
  day, month, year: INTEGER
END</pre>
<pre>RECORD
  name, firstname: ARRAY 32 OF CHAR;
  age: INTEGER;
  salary: REAL
END</pre>

<a NAME="Sec6.4"><h3>6.4 Pointer types</h3></a>

Variables of a pointer type _P_ assume as values pointers to variables of
some type _T_. _T_ is called the pointer base type of _P_ and must
be a record or array type. Pointer types adopt the extension relation of their
pointer base types: if a type _T1_ is an extension of _T_, and
_P1_ is of type POINTER TO _T1_, then _P1_ is also an extension of
_P_.

<pre>PointerType = POINTER TO Type.</pre>

If _p_ is a variable of type _P_ = POINTER TO_T_, a call of the
predeclared procedure _NEW(p)_ (<a HREF="#Sec10.3">see 10.3</a>)
allocates a variable of type _T_ in free storage. If _T_ is a record
type or an array type with fixed length, the allocation has to be done with
_NEW(p)_; if T is an n-dimensional open array type the allocation has to be
done with _NEW(p, e0, ..., en-1)_ where _T_ is allocated with lengths
given by the expressions _e0, ..., en-1_. In either case a pointer to the
allocated variable is assigned to _p_. _p_ is of type _P_. The
_referenced_ variable _p^_ (pronounced as _p-referenced_) is of
type _T_. Any pointer variable may assume the value NIL, which points to no
variable at all.

<h3>6.5 Procedure types</h3>

Variables of a procedure type _T_ have a procedure (or NIL) as value. If a
procedure _P_ is assigned to a variable of type _T_, the formal
parameter lists (<a HREF="#Sec10.1">see Ch. 10.1</a>) of _P_ and
_T_ must _match_ (<a HREF="#AppA">see App. A</a>). _P_ must
not be a predeclared or type-bound procedure nor may it be local to another
procedure.

<pre>ProcedureType = PROCEDURE [FormalParameters].</pre>

<a NAME="Cha7">## 7. Variable declarations</a>

Variable declarations introduce variables by defining an identifier and a data
type for them.

<pre>VariableDeclaration = IdentList &quot;:&quot; Type.</pre>

Record and pointer variables have both a _static type_ (the type with which
they are declared - simply called their type) and a_dynamic type_ (the type
of their value at run time). For pointers and variable parameters of record type
the dynamic type may be an extension of their static type. The static type
determines which fields of a record are accessible. The dynamic type is used to
call type-bound procedures (<a HREF="#Sec10.2">see 10.2</a>).

Examples of variable declarations (refer to examples in <a HREF="#Cha6">Ch.
6</a>):
<pre>i, j, k: INTEGER
x, y: REAL
p, q: BOOLEAN
s: SET
F: Function
a: ARRAY 100 OF REAL
w: ARRAY 16 OF RECORD
     name: ARRAY 32 OF CHAR;
     count: INTEGER
   END
t, c: Tree</pre>

<a NAME="Cha8">## 8. Expressions</a>

Expressions are constructs denoting rules of computation whereby constants and
current values of variables are combined to compute other values by the
application of operators and function procedures. Expressions consist of operands
and operators. Parentheses may be used to express specific associations of
operators and operands.

<a NAME="Sec8.1"><h3>8.1 Operands</h3></a>

With the exception of set constructors and literal constants (numbers, character
constants, or strings), operands are denoted by _designators_. A designator
consists of an identifier referring to a constant, variable, or procedure. This
identifier may possibly be qualified by a module identifier (<a HREF="#Cha4">see
Ch. 4</a> and <a HREF="#Cha11">11</a>) and may be followed by _selectors_
if the designated object is an element of a structure.

<pre>Designator     = Qualident {&quot;.&quot; ident | &quot;[&quot; ExpressionList &quot;]&quot; | &quot;^&quot;
               | &quot;(&quot; Qualident &quot;)&quot;}.
ExpressionList = Expression {&quot;,&quot; Expression}.</pre>

If a designates an array, then _a[e]_ denotes that element of a whose index
is the current value of the expression _e_. The type of _e_ must be an
integer type. A designator of the form _a[e0, e1, ..., en]_ stands for
_a[e0][e1]...[en]_. If _r_ designates a record, then _r.f_ denotes
the field _f_ of _r_ or the procedure _f_ bound to the dynamic
type of _r_ (<a HREF="#Sec10.2">Ch. 10.2</a>). If _p_ designates a
pointer, _p^_ denotes the variable which is referenced by _p_. The
designators _p^.f_ and _p^[e]_ may be abbreviated as _p.f_ and
_p[e]_, i.e. record and array selectors imply dereferencing. If _a_ or
_r_ are read-only, then also _a[e]_ and _r.f_ are read-only.

A _type guard v(T)_ asserts that the dynamic type of _v_ is _T_
(or an extension of _T_), i.e. program execution is aborted, if the dynamic
type of _v_ is not _T_ (or an extension of _T_). Within the
designator, _v_ is then regarded as having the static type _T_. The
guard is applicable, if

<ol>
<li>_v_ is a variable parameter of record type or _v_ is a pointer, and
if
<li>_T_ is an extension of the static type of _v_
</ol>

If the designated object is a constant or a variable, then the designator refers
to its current value. If it is a procedure, the designator refers to that
procedure unless it is followed by a (possibly empty) parameter list in which
case it implies an activation of that procedure and stands for the value
resulting from its execution. The actual parameters must correspond to the formal
parameters as in proper procedure calls (<a HREF="#Sec10.1">see 10.1</a>).

Examples of designators (refer to examples in <a HREF="#Cha7">Ch. 7</a>):

<pre>i                        (INTEGER)
a[i]                     (REAL)
w[3].name[i]             (CHAR)
t.left.right             (Tree)
t(CenterTree).subnode    (Tree)</pre>

<a NAME="Sec8.2"><h3>8.2 Operators</h3></a>

Four classes of operators with different precedences (binding strengths) are
syntactically distinguished in expressions. The operator ~ has the highest
precedence, followed by multiplication operators, addition operators, and
relations. Operators of the same precedence associate from left to right. For
example, x-y-z stands for (x-y)-z.

<pre>Expression       = SimpleExpression [Relation SimpleExpression].
SimpleExpression = [&quot;+&quot; | &quot;-&quot;] Term {AddOperator Term}.
Term             = Factor {MulOperator Factor}.
Factor           = Designator [ActualParameters] |
                   number | character | string | NIL | Set |
                   &quot;(&quot; Expression &quot;)&quot; | &quot;~&quot; Factor.
Set              = &quot;{&quot; [Element {&quot;,&quot; Element}] &quot;}&quot;.
Element          = Expression [&quot;..&quot; Expression].
ActualParameters = &quot;(&quot; [ExpressionList] &quot;)&quot;.
Relation         = &quot;=&quot; | &quot;#&quot; | &quot;&lt;&quot; | &quot;&lt;=&quot; | &quot;&gt;&quot; | &quot;&gt;=&quot; | IN | IS.
AddOperator      = &quot;+&quot; | &quot;-&quot; | OR.
MulOperator      = &quot;*&quot; | &quot;/&quot; | DIV | MOD | &quot;&amp;&quot;.</pre>

The available operators are listed in the following tables. Some operators are
applicable to operands of various types, denoting different operations. In these
cases, the actual operation is identified by the type of the operands. The
operands must be _expression compatible_ with respect to the operator (<a HREF="#AppA">see App. A</a>).

<h4>8.2.1 Logical operators</h4>

<pre>OR    logical disjunction    p OR q    &quot;if p then TRUE, else q&quot;
&amp;     logical conjunction    p &amp; q     &quot;if p then q, else FALSE&quot;
~     negation               ~ p       &quot;not p&quot;</pre>

These operators apply to BOOLEAN operands and yield a BOOLEAN result.

<h4>8.2.2 Arithmetic operators</h4>

<pre>+      sum
-      difference
*      product
/      real quotient
DIV    integer quotient
MOD    modulus</pre>

The operators +, -, *, and / apply to operands of numeric types. The type of the
result is the type of that operand which includes the type of the other operand,
except for division (/), where the result is the smallest real type which
includes both operand types. When used as monadic operators, - denotes sign
inversion and + denotes the identity operation. The operators DIV and MOD apply
to integer operands only. They are related by the following formulas defined for
any _x_ and positive divisors _y_:

```
x = (x DIV y) * y + (x MOD y)<br>
0 &lt;= (x MOD y) &lt; y
```

Examples:
<pre>x    y    x DIV y    x MOD y
5    3    1          2
-5   3    -2         1</pre>

<h4>8.2.3 Set Operators</h4>

<pre>+    union
-    difference (x - y = x * (-y))
*    intersection
/    symmetric set difference (x / y = (x-y) + (y-x))</pre>

Set operators apply to operands of type SET and yield a result of type SET. The
monadic minus sign denotes the complement of _x_, i.e. _-x_ denotes the
set of integers between 0 and MAX(SET) which are not elements of _x_. Set
operators are not associative ((a+b)-c # a+(b-c)).

A set constructor defines the value of a set by listing its elements between
curly brackets. The elements must be integers in the range 0..MAX(SET). A range
_a..b_ denotes all integers in the interval [_a_, _b_ ].

<h4>8.2.4 Relations</h4>

<pre>=     equal
#     unequal
&lt;     less
&lt;=    less or equal
&gt;     greater
&gt;=    greater or equal
IN    set membership
IS    type test</pre>

Relations yield a BOOLEAN result. The relations =, #, &lt;, &lt;=, &gt;, and &gt;=
apply to the numeric types, CHAR, strings, and character arrays containing 0X as
a terminator. The relations = and # also apply to BOOLEAN and SET, as well as to
pointer and procedure types (including the value NIL). _x IN s_ stands for
&quot;_x_ is) an element of _s_ &quot;. _x_ must be of an integer type, and
_s_ of type SET. _v IS T_ stands for &quot;the dynamic type of _v_ is
_T_ (or an extension of _T_ )&quot; and is called a _type test_. It is
applicable if

<ol>
<li>_v_ is a variable parameter of record type or _v_ is a pointer, and
if
<li>_T_ is an extension of the static type of _v_
</ol>

Examples of expressions (<a HREF="#Cha7">refer to examples in Ch.7</a>):

<pre>1991                   INTEGER
i DIV 3                INTEGER
~p OR q                BOOLEAN
(i+j) * (i-j)          INTEGER
s - {8, 9, 13}         SET
i + x                  REAL
a[i+j] * a[i-j]        REAL
(0&lt;=i) &amp; (i&lt;100)       BOOLEAN
t.key = 0              BOOLEAN
k IN {i..j-1}          BOOLEAN
w[i].name &lt;= &quot;John&quot;    BOOLEAN
t IS CenterTree        BOOLEAN</pre>

## 9. Statements

Statements denote actions. There are elementary and structured statements.
Elementary statements are not composed of any parts that are themselves
statements. They are the assignment, the procedure call, the return, and the exit
statement. Structured statements are composed of parts that are themselves
statements. They are used to express sequencing and conditional, selective, and
repetitive execution. A statement may also be empty, in which case it denotes no
action. The empty statement is included in order to relax punctuation rules in
statement sequences.

<pre>Statement =[ Assignment | ProcedureCall | IfStatement | CaseStatement
             | WhileStatement | RepeatStatement | ForStatement
             | LoopStatement | WithStatement | EXIT | RETURN [Expression] ].</pre>

<h3>9.1 Assignments</h3>

Assignments replace the current value of a variable by a new value specified by
an expression. The expression must be _assignment compatible_ with the
variable (<a HREF="#AppA">see App. A</a>). The assignment operator is
written as &quot;:=&quot; and pronounced as _becomes_.

<pre>Assignment = Designator &quot;:=&quot; Expression.</pre>

If an expression _e_ of type _Te_ is assigned to a variable _v_ of
type _Tv_, the following happens:

<ol>
<li>if _Tv_ and _Te_ are record types, only those fields of _Te_
are assigned which also belong to _Tv_ (_projection_); the dynamic type
of _v_ must be the _same_ as the static type of _v_ and is not
changed by the assignment;
<li>if _Tv_ and _Te_ are pointer types, the dynamic type of _v_
becomes the dynamic type of _e_;
<li>if _Tv_ is ARRAY n OF CHAR and _e_ is a string of length _m_
&lt;_n_, _v[i]_ becomes _ei_ for _i_ = 0.._m_ -1 and
_v[m]_ becomes 0X.
</ol>

Examples of assignments (<a HREF="#Cha7">refer to examples in Ch.7</a>):
<pre>i := 0
p := i = j
x := i + 1
k := log2(i+j)
F := log2	(* <a HREF="#Sec10.1">see 10.1</a> *)
s := {2, 3, 5, 7, 11, 13}
a[i] := (x+y) * (x-y)
t.key := i
w[i+1].name := &quot;John&quot;
t := c</pre>

<h3>9.2 Procedure calls</h3>

A procedure call activates a procedure. It may contain a list of actual
parameters which replace the corresponding formal parameters defined in the
procedure declaration (<a HREF="#Cha10">see Ch. 10</a>). The correspondence
is established by the positions of the parameters in the actual and formal
parameter lists. There are two kinds of parameters: _variable_ and _value
parameters_.

If a formal parameter is a variable parameter, the corresponding actual parameter
must be a designator denoting a variable. If it denotes an element of a
structured variable, the component selectors are evaluated when the formal/actual
parameter substitution takes place, i.e. before the execution of the procedure.
If a formal parameter is a value parameter, the corresponding actual parameter
must be an expression. This expression is evaluated before the procedure
activation, and the resulting value is assigned to the formal parameter (<a HREF="#Sec10.1">see also 10.1</a>).

<pre>ProcedureCall = Designator [ActualParameters].</pre>

Examples:
<pre>WriteInt(i*2+1)  (* <a HREF="#Sec10.1">see 10.1</a> *)
INC(w[k].count)
t.Insert(&quot;John&quot;)  (* <a HREF="#Cha11">see 11</a> *)</pre>

<h3>9.3 Statement sequences</h3>

Statement sequences denote the sequence of actions specified by the component
statements which are separated by semicolons.

<pre>StatementSequence = Statement {&quot;;&quot; Statement}.</pre>

<h3>9.4 If statements</h3>

<pre>IfStatement = IF Expression THEN StatementSequence
              {ELSIF Expression THEN StatementSequence}
              [ELSE StatementSequence]
              END.</pre>

If statements specify the conditional execution of guarded statement sequences.
The Boolean expression preceding a statement sequence is called its _guard_.
The guards are evaluated in sequence of occurrence, until one evaluates to TRUE,
whereafter its associated statement sequence is executed. If no guard is
satisfied, the statement sequence following the symbol ELSE is executed, if there
is one.

Example:

<pre>IF (ch &gt;= &quot;A&quot;) &amp; (ch &lt;= &quot;Z&quot;) THEN ReadIdentifier
ELSIF (ch &gt;= &quot;0&quot;) &amp; (ch &lt;= &quot;9&quot;) THEN ReadNumber
ELSIF (ch = &quot; ' &quot;) OR (ch = ' &quot; ') THEN ReadString
ELSE SpecialCharacter
END</pre>

<h3>9.5 Case statements</h3>

Case statements specify the selection and execution of a statement sequence
according to the value of an expression. First the case expression is evaluated,
then that statement sequence is executed whose case label list contains the
obtained value. The case expression must either be of an _integer type_ that
_includes_ the types of all case labels, or both the case expression and the
case labels must be of type CHAR. Case labels are constants, and no value must
occur more than once. If the value of the expression does not occur as a label of
any case, the statement sequence following the symbol ELSE is selected, if there
is one, otherwise the program is aborted.

<pre>CaseStatement = CASE Expression OF Case {&quot;|&quot; Case}
                [ELSE StatementSequence] END.
Case          = [CaseLabelList &quot;:&quot; StatementSequence].
CaseLabelList = CaseLabels {&quot;,&quot; CaseLabels}.
CaseLabels    = ConstExpression [&quot;..&quot; ConstExpression].</pre>

Example:
<pre>CASE ch OF
  &quot;A&quot; .. &quot;Z&quot;: ReadIdentifier
| &quot;0&quot; .. &quot;9&quot;: ReadNumber
| &quot;'&quot;, '&quot;': ReadString
ELSE SpecialCharacter
END</pre>

<h3>9.6 While statements</h3>

While statements specify the repeated execution of a statement sequence while the
Boolean expression (its _guard_) yields TRUE. The guard is checked before
every execution of the statement sequence.

<pre>WhileStatement = WHILE Expression DO StatementSequence END.</pre>

Examples:
<pre>WHILE i &gt; 0 DO i := i DIV 2; k := k + 1 END
WHILE (t # NIL) &amp; (t.key # i) DO t := t.left END</pre>

<h3>9.7 Repeat statements</h3>

A repeat statement specifies the repeated execution of a statement sequence until
a condition specified by a Boolean expression is satisfied. The statement
sequence is executed at least once.

<pre>RepeatStatement = REPEAT StatementSequence UNTIL Expression.</pre>

<h3>9.8 For statements</h3>

A for statement specifies the repeated execution of a statement sequence while a
progression of values is assigned to an integer variable called the _control
variable_ of the for statement.

<pre>ForStatement = FOR ident &quot;:=&quot; Expression TO Expression [BY ConstExpression] DO
               StatementSequence END.</pre>

The statement

```
FOR v := beg TO end BY step DO statements END
```

is equivalent to

```
temp := end; v := beg;
IF step > 0 THEN
WHILE v <= temp DO statements; v := v + step END
ELSE
WHILE v >= temp DO statements; v := v + step END
END
```

_temp_ has the _same_ type as _v_. _step_ must be a nonzero
constant expression. If _step_ is not specified, it is assumed to be 1.

Examples:
<pre>FOR i := 0 TO 79 DO k := k + a[i] END
FOR i := 79 TO 1 BY -1 DO a[i] := a[i-1] END</pre>

<h3>9.9 Loop statements</h3>

A loop statement specifies the repeated execution of a statement sequence. It is
terminated upon execution of an exit statement within that sequence (<a HREF="#Sec9.10">see 9.10</a>).

<pre>LoopStatement = LOOP StatementSequence END.</pre>

Example:
<pre>LOOP
  ReadInt(i);
  IF i &lt; 0 THEN EXIT END;
  WriteInt(i)
END</pre>

Loop statements are useful to express repetitions with several exit points or
cases where the exit condition is in the middle of the repeated statement
sequence.

<a NAME="Sec9.10"><h3>9.10 Return and exit statements</h3></a>

A return statement indicates the termination of a procedure. It is denoted by the
symbol RETURN, followed by an expression if the procedure is a function
procedure. The type of the expression must be _assignment compatible_ (<a HREF="#AppA">see App. A</a>) with the result type specified in the
procedure heading (<a HREF="#Cha10">see Ch.10</a>).

Function procedures require the presence of a return statement indicating the
result value. In proper procedures, a return statement is implied by the end of
the procedure body. Any explicit return statement therefore appears as an
additional (probably exceptional) termination point.

An exit statement is denoted by the symbol EXIT. It specifies termination of the
enclosing loop statement and continuation with the statement following that loop
statement. Exit statements are contextually, although not syntactically
associated with the loop statement which contains them.

<h3>9.11 With statements</h3>

With statements execute a statement sequence depending on the result of a type
test and apply a type guard to every occurrence of the tested variable within
this statement sequence.

<pre>WithStatement = WITH Guard DO StatementSequence
                {&quot;|&quot; Guard DO StatementSequence}
                [ELSE StatementSequence] END.
Guard         = Qualident &quot;:&quot; Qualident.</pre>

If _v_ is a variable parameter of record type or a pointer variable, and if
it is of a static type _T0_, the statement

```
WITH v: T1 DO S1 | v: T2 DO S2 ELSE S3 END
```

has the following meaning: if the dynamic type of _v_ is _T1_, then the
statement sequence _S1_ is executed where _v_ is regarded as if it had
the static type _T1_; else if the dynamic type of _v_ is _T2_,
then _S2_ is executed where _v_ is regarded as if it had the static
type _T2_; else _S3_ is executed. _T1_ and _T2_ must be
extensions of _T0_. If no type test is satisfied and if an else clause is
missing the program is aborted.

Example:
<pre>WITH t: CenterTree DO i := t.width; c := t.subnode END</pre>

<a NAME="Cha10">## 10. Procedure declarations</a>

A procedure declaration consists of a _procedure heading_ and a _procedure
body_. The heading specifies the procedure identifier and the _formal
parameters_. For type-bound procedures it also specifies the receiver
parameter. The body contains declarations and statements. The procedure
identifier is repeated at the end of the procedure declaration.

There are two kinds of procedures: _proper procedures_ and _function
procedures_. The latter are activated by a function designator as a
constituent of an expression and yield a result that is an operand of the
expression. Proper procedures are activated by a procedure call. A procedure is a
function procedure if its formal parameters specify a result type. The body of a
function procedure must contain a return statement which defines its result.

All constants, variables, types, and procedures declared within a procedure body
are _local_ to the procedure. Since procedures may be declared as local
objects too, procedure declarations may be nested. The call of a procedure within
its declaration implies recursive activation.

Objects declared in the environment of the procedure are also visible in those
parts of the procedure in which they are not concealed by a locally declared
object with the same name.

<pre>ProcedureDeclaration = ProcedureHeading &quot;;&quot; ProcedureBody ident.
ProcedureHeading     = PROCEDURE [Receiver] IdentDef [FormalParameters].
ProcedureBody        = DeclarationSequence [BEGIN StatementSequence] END.
DeclarationSequence  = {CONST {ConstantDeclaration &quot;;&quot;}
                        | TYPE {TypeDeclaration &quot;;&quot;}
                        | VAR {VariableDeclaration &quot;;&quot;} }
                       {ProcedureDeclaration &quot;;&quot; | ForwardDeclaration &quot;;&quot;}.
ForwardDeclaration   = PROCEDURE &quot; ^ &quot; [Receiver] IdentDef [FormalParameters].</pre>

If a procedure declaration specifies a _receiver_ parameter, the procedure
is considered to be bound to a type (<a HREF="#Sec10.2">see 10.2</a>). A
_forward declaration_ serves to allow forward references to a procedure
whose actual declaration appears later in the text. The formal parameter lists of
the forward declaration and the actual declaration must _match_ (<a HREF="#AppA">see App. A</a>).

<a NAME="Sec10.1"><h3>10.1 Formal parameters</h3></a>

Formal parameters are identifiers declared in the formal parameter list of a
procedure. They correspond to actual parameters specified in the procedure call.
The correspondence between formal and actual parameters is established when the
procedure is called. There are two kinds of parameters, _value_ and
_variable parameters_, indicated in the formal parameter list by the absence
or presence of the keyword VAR. Value parameters are local variables to which the
value of the corresponding actual parameter is assigned as an initial value.
Variable parameters correspond to actual parameters that are variables, and they
stand for these variables. The scope of a formal parameter extends from its
declaration to the end of the procedure block in which it is declared. A function
procedure without parameters must have an empty parameter list. It must be called
by a function designator whose actual parameter list is empty too. The result
type of a procedure can be neither a record nor an array.

<pre>FormalParameters = &quot;(&quot; [FPSection {&quot;;&quot; FPSection}] &quot;)&quot; [&quot;:&quot; Qualident].
FPSection        = [VAR] ident {&quot;,&quot; ident} &quot;:&quot; Type.</pre>

Let _Tf_ be the type of a formal parameter _f_ (not an open array) and
_Ta_ the type of the corresponding actual parameter _a_. For variable
parameters, _Ta_ must be the _same_ as _Tf_, or _Tf_ must be
a record type and _Ta_ an extension of _Tf_. For value parameters,
_a_ must be _assignment compatible_ with _f_ (<a HREF="#AppA">see
App. A</a>).

If _Tf_ is an open array, then _a_ must be _array compatible_ with
_f_ (<a HREF="#AppA">see App. A</a>). The lengths of _f_ are
taken from _a_.

Examples of procedure declarations:
<pre>PROCEDURE ReadInt(VAR x: INTEGER);
  VAR i: INTEGER; ch: CHAR;
BEGIN i := 0; Read(ch);
  WHILE (&quot;0&quot; &lt;= ch) &amp; (ch &lt;= &quot;9&quot;) DO
    i := 10*i + (ORD(ch)-ORD(&quot;0&quot;)); Read(ch)
  END;
  x := i
END ReadInt</pre>
<pre>PROCEDURE WriteInt(x: INTEGER); (*0 &lt;= x &lt;100000*)
VAR i: INTEGER; buf: ARRAY 5 OF INTEGER;
BEGIN i := 0;
  REPEAT buf[i] := x MOD 10; x := x DIV 10; INC(i) UNTIL x = 0;
  REPEAT DEC(i); Write(CHR(buf[i] + ORD(&quot;0&quot;))) UNTIL i = 0
END WriteInt</pre>
<pre>PROCEDURE WriteString(s: ARRAY OF CHAR);
  VAR i: INTEGER;
BEGIN i := 0;
  WHILE (i &lt; LEN(s)) &amp; (s[i] # 0X) DO Write(s[i]); INC(i) END
END WriteString;</pre>
<pre>PROCEDURE log2(x: INTEGER): INTEGER;
  VAR y: INTEGER; (*assume x&gt;0*)
BEGIN
  y := 0; WHILE x &gt; 1 DO x := x DIV 2; INC(y) END;
  RETURN y
END log2</pre>

<a NAME="Sec10.2"><h3>10.2 Type-bound procedures</h3></a>

Globally declared procedures may be associated with a record type declared in the
same module. The procedures are said to be _bound_ to the record type. The
binding is expressed by the type of the _receiver_ in the heading of a
procedure declaration. The receiver may be either a variable parameter of record
type _T_ or a value parameter of type POINTER TO _T_ (where _T_ is
a record type). The procedure is bound to the type _T_ and is considered
local to it.

<pre>ProcedureHeading = PROCEDURE [Receiver] IdentDef [FormalParameters].
Receiver         = &quot;(&quot; [VAR] ident &quot;:&quot; ident &quot;)&quot;.</pre>

If a procedure _P_ is bound to a type _T0_, it is implicitly also bound
to any type _T1_ which is an extension of _T0_. However, a procedure
_P'_ (with the same name as _P_) may be explicitly bound to _T1_
in which case it overrides the binding of _P_. _P'_ is considered a
_redefinition_ of _P_ for _T1_. The formal parameters of _P_
and _P'_ must _match_ (<a HREF="#AppA">see App. A</a>). If
_P_ and _T1_ are exported (<a HREF="#Cha4">see Chapter 4</a>)
_P'_ must be exported too.

If _v_ is a designator and _P_ is a type-bound procedure, then
_v.P_ denotes that procedure _P_ which is bound to the dynamic type of
_v_. Note, that this may be a different procedure than the one bound to the
static type of _v_. _v_ is passed to _P_ 's receiver according to
the parameter passing rules specified in <a HREF="#Sec10.1">Chapter 10.1</a>.

If _r_ is a receiver parameter declared with type _T_, _r.P^_
denotes the (redefined) procedure _P_ bound to the base type of _T_. In
a forward declaration of a type-bound procedure the receiver parameter must be of
the _same_ type as in the actual procedure declaration. The formal parameter
lists of both declarations must _match_ (<a HREF="#AppA">App. A</a>).

Examples:
<pre>PROCEDURE (t: Tree) Insert (node: Tree);
  VAR p, father: Tree;
BEGIN p := t;
  REPEAT father := p;
    IF node.key = p.key THEN RETURN END;
    IF node.key &lt; p.key THEN
      p := p.left
    ELSE
      p := p.right
    END
  UNTIL p = NIL;
  IF node.key &lt; father.key THEN
    father.left := node
  ELSE
    father.right := node
  END;
  node.left := NIL; node.right := NIL
END Insert;</pre>
<pre>PROCEDURE (t: CenterTree) Insert (node: Tree);  (*redefinition*)
BEGIN
  WriteInt(node(CenterTree).width);
  t.Insert^ (node)  (* calls the Insert procedure bound to Tree *)
END Insert;</pre>

<a NAME="Sec10.3"><h3>10.3 Predeclared procedures</h3></a>

The following table lists the predeclared procedures. Some are generic
procedures, i.e. they apply to several types of operands. _v_ stands for a
variable, _x_ and _n_ for expressions, and _T_ for a type.

<pre>Function Procedures
Name        Argument type        Result type    Function
ABS(x)      numeric type         type of x      absolute value
ASH(x, n)   x, n: integer type   LONGINT        arithmetic shift (x * 2^n)
CAP(x)      CHAR                 CHAR           x is letter: corresponding
                                                capital letter
CHR(x)      integer type         CHAR           character with ordinal
                                                number x
ENTIER(x)   real type            LONGINT        largest integer not greater
                                                than x
LEN(v, n)   v: array;            LONGINT        length of v in dimension n
            n: integer const.                   (first dimension = 0)
LEN(v)      v: array             LONGINT        equivalent to LEN(v, 0)
LONG(x)     SHORTINT             INTEGER        identity
            INTEGER              LONGINT
            REAL                 LONGREAL
MAX(T)      T = basic type       T              maximum value of type T
            T = SET              INTEGER        maximum element of a set
MIN(T)      T = basic type       T              minimum value of type T
            T = SET              INTEGER        0
ODD(x)      integer type         BOOLEAN        x MOD 2 = 1
ORD(x)      CHAR                 INTEGER        ordinal number of x
SHORT(x)    LONGINT              INTEGER        identity
            INTEGER              SHORTINT       identity
            LONGREAL             REAL           identity (truncation possible)
SIZE(T)     any type             integer type   number of bytes required by T</pre>

<pre>Proper procedures
Name               Argument types                Function
ASSERT(x)          x: Boolean expression         terminate program execution
                                                 if not x
ASSERT(x, n)       x: Boolean expression;        terminate program execution
                   n: integer constant           if not x
COPY(x, v)         x: character array, string;   v := x
                   v: character array
DEC(v)             integer type                  v := v - 1
DEC(v, n)          v, n: integer type            v := v - n
EXCL(v, x)         v: SET; x: integer type       v := v - {x}
HALT(n)            integer constant              terminate program execution
INC(v)             integer type                  v := v + 1
INC(v, n)          v, n: integer type            v := v + n
INCL(v, x)         v: SET; x: integer type       v := v + {x}
NEW(v)             pointer to record or          allocate v^
                   fixed array
NEW(v,x0,...,xn)   v: pointer to open array;     allocate v^ with lengths
                   xi: integer type              x0..xn</pre>

COPY allows the assignment of a string or a character array containing a
terminating 0X to another character array. If necessary, the assigned value is
truncated to the target length minus one. The target will always contain 0X as a
terminator. In ASSERT(x, n) and HALT(n), the interpretation of _n_ is left
to the underlying system implementation.

<a NAME="Cha11">## 11. Modules</a>

A module is a collection of declarations of constants, types, variables, and
procedures, together with a sequence of statements for the purpose of assigning
initial values to the variables. A module constitutes a text that is compilable
as a unit.

<pre>Module     = MODULE ident &quot;;&quot; [ImportList] DeclarationSequence
             [BEGIN StatementSequence] END ident &quot;.&quot;.
ImportList = IMPORT Import {&quot;,&quot; Import} &quot;;&quot;.
Import     = [ident &quot;:=&quot;] ident.</pre>

The import list specifies the names of the imported modules. If a module _A_
is imported by a module _M_ and _A_ exports an identifier _x_,
then _x_ is referred to as _A.x_ within _M_. If _A_ is
imported as _B := A_, the object _x_ must be referenced as _B.x_.
This allows short alias names in qualified identifiers. A module must not import
itself. Identifiers that are to be exported (i.e. that are to be visible in
client modules) must be marked by an export mark in their declaration (<a HREF="#Cha4">see Chapter 4</a>).

The statement sequence following the symbol BEGIN is executed when the module is
added to a system (loaded), which is done after the imported modules have been
loaded. It follows that cyclic import of modules is illegal. Individual
(parameterless and exported) procedures can be activated from the system, and
these procedures serve as _commands_ (<a HREF="#SecD1">see Appendix
D1</a>).

<pre>MODULE Trees;  (* exports: Tree, Node, Insert, Search, Write, Init *)
  IMPORT Texts, Oberon;  (* exports read-only: Node.name *)

  TYPE
    Tree* = POINTER TO Node;
    Node* = RECORD
      name-: POINTER TO ARRAY OF CHAR;
      left, right: Tree
    END;

  VAR w: Texts.Writer;

  PROCEDURE (t: Tree) Insert* (name: ARRAY OF CHAR);
    VAR p, father: Tree;
  BEGIN p := t;
    REPEAT father := p;
      IF name = p.name^ THEN RETURN END;
      IF name &lt; p.name^ THEN p := p.left ELSE p := p.right END
    UNTIL p = NIL;
    NEW(p); p.left := NIL; p.right := NIL;
    NEW(p.name, LEN(name)+1); COPY(name, p.name^);
    IF name &lt; father.name^ THEN
      father.left := p
    ELSE
      father.right := p
    END
  END Insert;

  PROCEDURE (t: Tree) Search* (name: ARRAY OF CHAR): Tree;
    VAR p: Tree;
  BEGIN p := t;
    WHILE (p # NIL) &amp; (name # p.name^) DO
      IF name &lt; p.name^ THEN p := p.left ELSE p := p.right END
    END;
    RETURN p
  END Search;

  PROCEDURE (t: Tree) Write*;
  BEGIN
    IF t.left # NIL THEN t.left.Write END;
    Texts.WriteString(w, t.name^); Texts.WriteLn(w);
    Texts.Append(Oberon.Log, w.buf);
    IF t.right # NIL THEN t.right.Write END
  END Write;

  PROCEDURE Init* (t: Tree);
  BEGIN NEW(t.name, 1); t.name[0] := 0X; t.left := NIL; t.right := NIL
  END Init;

BEGIN Texts.OpenWriter(w)
END Trees.</pre>

<a NAME="AppA">## Appendix A: Definition of terms</a>

<dl>
<dt><b>Integer types</b>
<dd>SHORTINT, INTEGER, LONGINT

<dt><b>Real types</b>
<dd>REAL, LONGREAL

<dt><b>Numeric types</b>
<dd>integer types, real types

<dt><b>Same types</b>
<dd>Two variables _a_ and _b_ with types _Ta_ and _Tb_ are of
the _same_ type if
<ol>
<li>_Ta_ and _Tb_ are both denoted by the same type identifier, or
<li>_Ta_ is declared to equal _Tb_ in a type declaration of the form
_Ta = Tb_, or
<li>_a_ and _b_ appear in the same identifier list in a variable,
record field, or formal parameter declaration and are not open arrays.
</ol>

<dt><b>Equal types</b>
<dd>Two types _Ta_ and _Tb_ are _equal_ if

* _Ta_ and _Tb_ are the _same_ type, or
* _Ta_ and _Tb_ are open array types with _equal_ element types, or
* _Ta_ and _Tb_ are procedure types whose formal parameter lists _match_.

<dt><b>Type inclusion</b>
<dd>Numeric types _include_ (the values of) smaller numeric types according
to the following hierarchy:

`LONGREAL >= REAL >= LONGINT >= INTEGER >= SHORTINT`

<dt><b>Type extension</b> (base type)
<dd> Given a type declaration _Tb = RECORD(Ta)...END_, _Tb_ is a
_direct extension_ of _Ta_, and _Ta_ is a _direct base type_
of _Tb_. A type _Tb_ is an _extension_ of a type _Ta_
(_Ta_ is a _base type_ of _Tb_) if

* _Ta_ and _Tb_ are the _same_ types, or
* _Tb_ is a _direct extension_ of an _extension_ of _Ta_

If _Pa = POINTER TO Ta_ and _Pb = POINTER TO Tb _, _Pb_ is an
_extension_ of _Pa_ (_Pa_ is) a _base type_ of _Pb_ ) if
_Tb_ is an _extension_ of _Ta_.

<dt><b>Assignment compatible</b>
<dd>An expression _e_ of type _Te_ is _assignment compatible_ with
a variable _v_ of type _Tv_ if one of the following conditions hold:
<ol>
<li> _Te_ and _Tv_ are the _same_ type;
<li>_Te_ and _Tv_ are numeric types and _Tv_ _includes_
_Te_;
<li>_Te_ and _Tv_ are record types and _Te_ is an _extension_
of _Tv_ and the dynamic type of _v_ is _Tv_;
<li>_Te_ and _Tv_ are pointer types and _Te_ is an
_extension_ of _Tv_;
<li>_Tv_ is a pointer or a procedure type and _e_ is NIL;
<li>_Tv_ is ARRAY n OF CHAR, _e_ is a string constant with _m_
characters, and _m_ <_n_;
<li>_Tv_ is a procedure type and _e_ is the name of a procedure whose
formal parameters _match_ those of _Tv_.
</ol>

<dt><b>Array compatible</b>
<dd>An actual parameter _a_ of type _Ta_ is _array compatible_
with a formal parameter _f_ of type _Tf_ if
<ol>
<li>_Tf_ and _Ta_ are the _same_ type, or
<li>_Tf_ is an open array, _Ta_ is any array, and their element types
are _array compatible_, or
<li>_Tf_ is ARRAY OF CHAR and _a_ is a string.
</ol>

<dt><b>Expression compatible</b>
<dd>For a given operator, the types of its operands are _expression
compatible_ if they conform to the following table (which shows also the
result type of the expression). Character arrays that are to be compared must
contain 0X as a terminator. Type T1 must be an extension of type T0:

<pre>operator  first operand       second operand      result type
+ - *     _numeric_             _numeric_             smallest _numeric_
                                                  type including
                                                  both operands
/         _numeric_             _numeric_             smallest _real_ type
                                                  type including
                                                  both operands
+ - * /   SET                 SET                 SET
DIV MOD   _integer_             _integer_             smallest _integer_ type
                                                  type including
                                                  both operands
OR &amp; ~    BOOLEAN             BOOLEAN             BOOLEAN
= # &lt;     _numeric_             _numeric_             BOOLEAN
&lt;= &gt; &gt;=   CHAR                CHAR                BOOLEAN
          character array,    character array,    BOOLEAN
          string              string
= #       BOOLEAN             BOOLEAN             BOOLEAN
          SET                 SET                 BOOLEAN
          NIL, pointer type   NIL, pointer type   BOOLEAN
          T0 or T1            T0 or T1
          procedure type T,   procedure type T,   BOOLEAN
          NIL                 NIL
IN        _integer_             SET                 BOOLEAN
IS        type T0             type T1             BOOLEAN</pre>

<dt><b>Matching formal parameter lists</b>
<dd>Two formal parameter lists _match_ if
<ol>
<li>they have the same number of parameters, and
<li>they have either the _same_ function result type or none, and
<li>parameters at corresponding positions have _equal_ types, and
<li>parameters at corresponding positions are both either value or variable
parameters.
</ol>
</dl>

## Appendix B: Syntax of Oberon-2

<pre>Module       = MODULE ident &quot;;&quot; [ImportList] DeclSeq
               [BEGIN StatementSeq] END ident &quot;.&quot;.
ImportList   = IMPORT [ident &quot;:=&quot;] ident {&quot;,&quot; [ident &quot;:=&quot;] ident} &quot;;&quot;.
DeclSeq      = { CONST {ConstDecl &quot;;&quot; } | TYPE {TypeDecl &quot;;&quot;}
                 | VAR {VarDecl &quot;;&quot;}} {ProcDecl &quot;;&quot; | ForwardDecl &quot;;&quot;}.
ConstDecl    = IdentDef &quot;=&quot; ConstExpr.
TypeDecl     = IdentDef &quot;=&quot; Type.
VarDecl      = IdentList &quot;:&quot; Type.
ProcDecl     = PROCEDURE [Receiver] IdentDef [FormalPars] &quot;;&quot; DeclSeq
               [BEGIN StatementSeq] END ident.
ForwardDecl  = PROCEDURE &quot;^&quot; [Receiver] IdentDef [FormalPars].
FormalPars   = &quot;(&quot; [FPSection {&quot;;&quot; FPSection}] &quot;)&quot; [&quot;:&quot; Qualident].
FPSection    = [VAR] ident {&quot;,&quot; ident} &quot;:&quot; Type.
Receiver     = &quot;(&quot; [VAR] ident &quot;:&quot; ident &quot;)&quot;.
Type         = Qualident
             | ARRAY [ConstExpr {&quot;,&quot; ConstExpr}] OF Type
             | RECORD [&quot;(&quot;Qualident&quot;)&quot;] FieldList {&quot;;&quot; FieldList} END
             | POINTER TO Type
             | PROCEDURE [FormalPars].
FieldList    = [IdentList &quot;:&quot; Type].
StatementSeq = Statement {&quot;;&quot; Statement}.
Statement    = [ Designator &quot;:=&quot; Expr 
             | Designator [&quot;(&quot; [ExprList] &quot;)&quot;] 
             | IF Expr THEN StatementSeq {ELSIF Expr THEN StatementSeq}
               [ELSE StatementSeq] END 
             | CASE Expr OF Case {&quot;|&quot; Case} [ELSE StatementSeq] END 
             | WHILE Expr DO StatementSeq END 
             | REPEAT StatementSeq UNTIL Expr 
             | FOR ident &quot;:=&quot; Expr TO Expr [BY ConstExpr] DO StatementSeq END 
             | LOOP StatementSeq END
             | WITH Guard DO StatementSeq {&quot;|&quot; Guard DO StatementSeq}
               [ELSE StatementSeq] END
             | EXIT 
             | RETURN [Expr]
             ].
Case         = [CaseLabels {&quot;,&quot; CaseLabels} &quot;:&quot; StatementSeq].
CaseLabels   = ConstExpr [&quot;..&quot; ConstExpr].
Guard        = Qualident &quot;:&quot; Qualident.
ConstExpr    = Expr.
Expr         = SimpleExpr [Relation SimpleExpr].
SimpleExpr   = [&quot;+&quot; | &quot;-&quot;] Term {AddOp Term}.
Term         = Factor {MulOp Factor}.
Factor       = Designator [&quot;(&quot; [ExprList] &quot;)&quot;] | number | character | string
               | NIL | Set | &quot;(&quot; Expr &quot;)&quot; | &quot; ~ &quot; Factor.
Set          = &quot;{&quot; [Element {&quot;,&quot; Element}] &quot;}&quot;.
Element      = Expr [&quot;..&quot; Expr].
Relation     = &quot;=&quot; | &quot;#&quot; | &quot;&lt;&quot; | &quot;&lt;=&quot; | &quot;&gt;&quot; | &quot;&gt;=&quot; | IN | IS.
AddOp        = &quot;+&quot; | &quot;-&quot; | OR.
MulOp        = &quot; * &quot; | &quot;/&quot; | DIV | MOD | &quot;&amp;&quot;.
Designator   = Qualident {&quot;.&quot; ident | &quot;[&quot; ExprList &quot;]&quot; | &quot; ^ &quot;
               | &quot;(&quot; Qualident &quot;)&quot;}.
ExprList     = Expr {&quot;,&quot; Expr}.
IdentList    = IdentDef {&quot;,&quot; IdentDef}.
Qualident    = [ident &quot;.&quot;] ident.
IdentDef     = <td>ident [&quot; * &quot; | &quot;-&quot;].</pre>

## Appendix C: The module SYSTEM

The module SYSTEM contains certain types and procedures that are necessary to
implement _low-level_ operations particular to a given computer and/or
implementation. These include for example facilities for accessing devices that
are controlled by the computer, and facilities to break the type compatibility
rules otherwise imposed by the language definition. It is strongly recommended to
restrict their use to specific modules (called _low-level_ modules). Such
modules are inherently non-portable, but easily recognized due to the identifier
SYSTEM appearing in their import list. The following specifications hold for the
implementation of Oberon-2 on the Ceres computer.

Module SYSTEM exports a type BYTE with the following characteristics: Variables
of type CHAR or SHORTINT can be assigned to variables of type BYTE. If a formal
variable parameter is of type ARRAY OF BYTE then the corresponding actual
parameter may be of any type.

Another type exported by module SYSTEM is the type PTR. Variables of any pointer
type may be assigned to variables of type PTR. If a formal variable parameter is
of type PTR, the actual parameter may be of any pointer type.

The procedures contained in module SYSTEM are listed in the following tables.
Most of them correspond to single instructions compiled as in-line code. For
details, the reader is referred to the processor manual. _v_ stands for a
variable, _x_, _y_, _a_, and _n_ for expressions, and
_T_ for a type.

<pre>_Function procedures_
Name         Argument types            Result type    Function
ADR(v)       any                       LONGINT        address of variable v
BIT(a, n)    a: LONGINT                BOOLEAN        bit n of Mem[a]
             n: _integer_
CC(n)        _integer_ constant          BOOLEAN        condition n
                                                      (0 &lt;= n &lt;= 15)
LSH(x, n)    x: _integer_, CHAR, BYTE    type of x      logical shift
             n: _integer_
ROT(x, n)    x: _integer_, CHAR, BYTE    type of x      rotation
             n: _integer_
VAL(T, x)    T, x: any type            T              x interpreted as of
                                                      type T</pre>

<pre>_Proper procedures_
Name             Argument types                    Function
GET(a, v)        a: LONGINT; v: any basic type,    v := Mem[a]
                 pointer, procedure type
PUT(a, x)        a: LONGINT; x: any basic type,    Mem[a] := x
                 pointer, procedure type
GETREG(n, v)     n: _integer_ constant;              v := Register n
                 v: any basic type, pointer,
                 procedure type
PUTREG(n, x)     n: _integer_ constant;              Register n := x
                 x: any basic type, pointer,
                 procedure type
MOVE(a0,a1,n)    a0, a1: LONGINT; n: _integer_       Mem[a1..a1+n-1] := Mem[a0.. a0+n-1])
NEW(v, n)        v: any pointer; n: _integer_        allocate storage block
                                                   of n bytes; assign its
                                                   address to v</pre>

## Appendix D: The Oberon Environment

Oberon-2 programs usually run in an environment that provides _command
activation_, _garbage collection_, _dynamic loading_of modules, and
certain _run time data structures_. Although not part of the language, this
environment contributes to the power of Oberon-2 and is to some degree implied by
the language definition. Appendix D describes the essential features of a typical
Oberon environment and provides implementation hints. More details can be found
in <a HREF="#Book1">[1]</a>, <a HREF="#Book2">[2]</a>, and <a HREF="#Book3">[3]</a>.

<a NAME="SecD1"><h3>D1. Commands</h3></a>

A command is any parameterless procedure _P_ that is exported from a module
_M_. It is denoted by _M.P_ and can be activated under this name from
the shell of the operating system. In Oberon, a user invokes commands instead of
programs or modules. This gives him a finer grain of control and allows modules
with multiple entry points. When a command _M.P_ is invoked, the module
_M_ is dynamically loaded unless it is already in memory (<a HREF="#SecD2">see D2</a>) and the procedure _P_ is executed. When _P_
terminates, _M_ remains loaded. All global variables and data structures
that can be reached from global pointer variables in _M_ retain their
values. When _P_ (or another command of _M_) is invoked again, it may
continue to use these values.

The following module demonstrates the use of commands. It implements an abstract
data structure _Counter_ that encapsulates a counter variable and provides
commands to increment and print its value.

<pre>MODULE Counter;
  IMPORT Texts, Oberon;
  VAR
    counter: LONGINT;
    w: Texts.Writer;

  PROCEDURE Add*;   (* takes a numeric argument from the command line *)
    VAR s: Texts.Scanner;
  BEGIN 
    Texts.OpenScanner(s, Oberon.Par.text, Oberon.Par.pos);
    Texts.Scan(s);
    IF s.class = Texts.Int THEN INC(counter, s.i) END
  END Add;

  PROCEDURE Write*;
  BEGIN
    Texts.WriteInt(w, counter, 5); Texts.WriteLn(w);
    Texts.Append(Oberon.Log, w.buf)
  END Write;

BEGIN counter := 0; Texts.OpenWriter(w)
END Counter.</pre>

The user may execute the following two commands:

<dl>
<dt><tt>Counter.Add</tt> _n_
	<dd>adds the value _n_ to the variable _counter_
<dt><tt>Counter.Write</tt>
	<dd>writes the current value of _counter_ to the screen
</dl>

Since commands are parameterless they have to get their arguments from the
operating system. In general, commands are free to take arguments from everywhere
(e.g. from the text following the command, from the most recent selection, or
from a marked viewer). The command _Add_ uses a scanner (a data type
provided by the Oberon system) to read the value that follows it on the command
line.

When _Counter.Add_ is invoked for the first time, the module _Counter_
is loaded and its body is executed. Every call of _Counter.Add n_ increments
the variable _counter_ by _n_. Every call of _Counter.Write_
writes the current value of _counter_ to the screen.

Since a module remains loaded after the execution of its commands, there must be
an explicit way to unload it (e.g. when the user wants to substitute the loaded
version by a recompiled version.) The Oberon system provides a command to do
that.

<a NAME="SecD2"><h3>D2. Dynamic Loading of Modules</h3></a>

A loaded module may invoke a command of a still unloaded module by specifying its
name as a string. The specified module is then dynamically loaded and the
designated command is executed. Dynamic loading allows the user to start a
program as a small set of basic modules and to extend it by adding further
modules at run time as the need becomes evident.

A module _M0_ may cause the dynamic loading of a module _M1_ without
importing it. _M1_ may of course import and use _M0_, but _M0_
need not know about the existence of _M1_. _M1_ can be a module that is
designed and implemented long after _M0_.

<h3>D3. Garbage Collection</h3>

In Oberon-2, the predeclared procedure NEW is used to allocate data blocks in
free memory. There is, however, no way to explicitly dispose an allocated block.
Rather, the Oberon environment uses a _garbage collector_ to find the blocks
that are not used any more and to make them available for allocation again. A
block is in use as long as it can be reached from a global pointer variable via a
pointer chain. Cutting this chain (e.g., setting a pointer to NIL) makes the
block collectable.

A garbage collector frees a programmer from the non-trivial task of deallocating
data structures correctly and thus helps to avoid errors. However, it requires
information about dynamic data at run time (<a HREF="#SecD5">see D5</a>).

<h3>D4. Browser</h3>

The interface of a module (the declaration of the exported objects) is extracted
from the module by a so-called _browser_ which is a separate tool of the
Oberon environment. For example, the browser produces the following interface of
the module _Trees_ from <a HREF="#Cha11">Ch. 11</a>.

<pre>DEFINITION Trees; 

  TYPE
    Tree = POINTER TO Node;
    Node = RECORD
      name: POINTER TO ARRAY OF CHAR;
      PROCEDURE (t: Tree) Insert (name: ARRAY OF CHAR);
      PROCEDURE (t: Tree) Search (name: ARRAY OF CHAR): Tree;
      PROCEDURE (t: Tree) Write;
    END;

  PROCEDURE Init (VAR t: Tree);

END Trees.</pre>

For a record type, the browser also collects all procedures bound to this type
and shows their declaration in the record type declaration.

<a NAME="SecD5"><h3>D5. Run Time Data Structures</h3></a>

Certain information about records has to be available at run time: The dynamic
type of records is needed for type tests and type guards. A table with the
addresses of the procedures bound to a record is needed for calling them.
Finally, the garbage collector needs information about the location of pointers
in dynamically allocated records. All that information is stored in so-called
_type descriptors_ of which there is one for every record type at run time.
The following paragraphs show a possible implementation of type descriptors.

The dynamic type of a record corresponds to the address of its type descriptor.
For dynamically allocated records this address is stored in a so-called _type
tag_ which precedes the actual record data and which is invisible for the
programmer. If t is a variable of type _CenterTree_ (<a HREF="#Cha6">see
example in Ch. 6</a>) Figure D5.1 shows one possible implementation of the run
time data structures.

<img SRC="figured5.gif" WIDTH="439" HEIGHT="212">

<b>Fig. D5.1</b> A variable _t_ of type _CenterTree_, the record
_t^_ it points to, and its type descriptor

Since both the table of procedure addresses and the table of pointer offsets must
have a fixed offset from the type descriptor address, and since both may grow
when the type is extended and further procedures and pointers are added, the
tables are located at the opposite ends of the type descriptor and grow in
different directions.

A type-bound procedure _t.P_ is called as _t^.tag^.ProcTab[IndexP]_.
The procedure table index of every type-bound procedure is known at compile time.
A type test _v IS T_ is translated into
_v^.tag^.BaseTypes[ExtensionLevelT] = TypeDescrAdrT_. Both the extension
level of a record type and the address of its type descriptor are known at
compile time. For example, the extension level of _Node_ is 0 (it has no
base type), and the extension level of _CenterNode_ is 1.

<ol>
<li><a NAME="Book1">N.Wirth, J.Gutknecht: The Oberon System. Software Practice
and Experience 19, 9, Sept. 1989</a>
<li><a NAME="Book2">M.Reiser: The Oberon System. User Guide and Programming
Manual. Addison-Wesley, 1991</a>
<li><a NAME="Book3">C.Pfister, B.Heeb, J.Templ: Oberon Technical Notes. Report
156, ETH Zürich, March 1991</a>
</ol>
<hr>
Adapted to HTML by <a HREF="mailto:gesswein@informatik.uni-ulm.de">Jürgen
Geßwein</a>; 9. Juni 1995
</body>
</html>
