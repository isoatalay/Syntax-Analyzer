Syntax Analyzer
===============

A lightweight tokeniser + recursive‑descent parser that validates a small
Java‑like language.  Written for **CENG 206 — Programming Languages
(Homework 1, Spring 2025)**.  The program reads an input file, checks each
statement against the grammar below, and writes a per‑statement verdict to
an output file. :contentReference[oaicite:0]{index=0}&#8203;:contentReference[oaicite:1]{index=1}


Contents
--------
1.  Key Features
2.  Supported Grammar
3.  Project Layout
4.  Building
5.  Running
6.  Example
7.  Error Handling
8.  Contributing / Academic Note
9.  License


1  Key Features
---------------
* Pure‑Java implementation; no external libraries required.
* Clean token hierarchy (`Token` + 15 concrete subclasses).
* Recursive‑descent parser with basic error‑recovery (skips to next ‘;’).
* Command‑line interface:  
  `java syntaxanalyzer.SyntaxAnalyzer <input> <output>`
* Ready to integrate with **Ant** (`build.xml` provided) or plain `javac`.
* Emits readable feedback such as  
  `Statement 3: Invalid While loop due to Missing right parenthesis ")"`.


2  Supported Grammar (subset of Java)
------------------------------------

<program> ::= { <statement> } <statement> ::= <var‑decl> ';' | <assignment> ';' | <if‑stmt> | <while‑loop> | <return‑stmt> ';'

<var‑decl> ::= ('int' | 'char') <identifier> <assignment> ::= <identifier> '=' <expression> <if‑stmt> ::= 'if' '(' <condition> ')' <block> <while‑loop> ::= 'while' '(' <condition> ')' <block> <return‑stmt> ::= 'return' <expression> <block> ::= '{' { <statement> } '}' <condition> ::= <expression> <rel‑op> <expression> <expression> ::= <term> { ('+' | '-') <term> } <term> ::= <factor> { ('*' | '/') <factor> } <factor> ::= <identifier> | <constant> | '(' <expression> ')'

<identifier> ::= single lowercase letter (e.g., x) <constant> ::= decimal integer <rel‑op> ::= '<' | '<=' | '>' | '>=' | '==' | '!='

The implementation recognises all operators and delimiters listed in the
assignment brief. Character literals (`'a'`) are **not** yet supported.


3  Project Layout
-----------------

syntax-analyzer/ │ ├─ build.xml # Ant build script (optional) ├─ README.txt # this file └─ src/ └─ syntaxanalyzer/ ├─ *.java # Token, Tokenizer, Parser, etc. └─ ...



4  Building
-----------
Using **Ant** (recommended):

    ant jar        # creates dist/syntax-analyzer.jar

Manual compile:

    javac -d build src/syntaxanalyzer/*.java


5  Running
----------

java -cp build syntaxanalyzer.SyntaxAnalyzer input.txt output.txt

`input.txt` contains source code to be analysed; `output.txt` receives one
verdict line per statement.


6  Example
----------
Input (`example.txt`): int x; x = 5 + 3; if (x > 2) { x = x - 1; } return x;

Command: java syntaxanalyzer.SyntaxAnalyzer example.txt result.txt


Output (`result.txt`):
Statement 1: Valid Variable declaration 
Statement 2: Valid Assignment Statement 
Statement 3: Valid If Statement 
Statement 4: Valid Return Statement


7  Error Handling
-----------------
* **Missing semicolon** → “Missing semicolon”
* **Unmatched braces / parentheses** → “Missing '}' ” or “Missing right
  parenthesis ')'”
* **Illegal identifier** (not a single lower‑case letter) → descriptive
  message
* **Unsupported operator** → “Illegal token <token>”

The parser attempts to continue after an error by discarding tokens up to
the next semicolon.


8  Contributing / Academic Note
-------------------------------
This repository was created for a graded university assignment. Feel free to
fork and experiment under the terms of the license, **but do NOT submit this
code for academic credit**.  Posting derivative solutions without permission
may violate your institution’s honour code.


9  License
----------
Released under the **MIT License** — see `LICENSE` for details.

