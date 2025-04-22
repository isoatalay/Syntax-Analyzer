package syntaxanalyzer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Parser {

    Queue<Token> tokens;
    Stack<String> results;

    int statementCounter = 0;

    public Parser(Queue<Token> tokens) throws Exception {
        this.tokens = tokens;
        results = new Stack<>();
        while (!tokens.isEmpty()) {
            parseStatement();
        }
    }

    private void parseStatement() throws Exception {
        if (!tokens.isEmpty()) {
            Token currentToken = tokens.peek(); // first token (it doesnt remove)
            Exception e = null;

            if (currentToken instanceof IfStatement) {
                results.push("If statement  start");
                e = parseIfStatement();
                if (e == null) {
                    results.push("Statement " + ++statementCounter + ": Valid If Statement");
                } else {
                    results.push("Statement " + ++statementCounter + ": Invalid If Statement due to " + e);
                }
            } else if (currentToken instanceof WhileStatement) {
                results.push("While loop start");
                e = parseWhileStatement();
                if (e == null) {
                    results.push("Statement " + ++statementCounter + ": Valid While loop");
                } else {
                    results.push("Statement " + ++statementCounter + ": Invalid While loop due to " + e);
                }
            } else if (currentToken instanceof DataType) {
                e = parseVariableDeclaration();
                if (e != null) {
                    results.push("Statement " + ++statementCounter + ": Variable declaration is invalid due to " + e);
                } else {
                    e = parseSemicolon();
                    if (e != null) {
                        results.push("Statement " + ++statementCounter + ": Variable declaration is invalid due to " + e);
                    } else {
                        results.push("Statement " + ++statementCounter + ": Valid Variable declaration ");
                    }
                }

            } else if (currentToken instanceof Identifier) {
                e = parseAssignmentStatement();
                if (e != null) {
                    results.push("Statement " + ++statementCounter + ":  Assignment Statement is invalid due to " + e);
                } else {
                    e = parseSemicolon();
                    if (e != null) {
                        results.push("Statement " + ++statementCounter + ": Assignment Statement is invalid due to " + e);
                    } else {
                        results.push("Statement " + ++statementCounter + ": Valid Assignment Statement ");
                    }
                }

            } else if (currentToken instanceof ReturnStatement) {
                e = parseReturnStatement();
                if (e != null) {
                    results.push("Statement " + ++statementCounter + ": Return Statement is invalid due to " + e);
                } else {
                    e = parseSemicolon();
                    if (e != null) {
                        results.push("Statement " + ++statementCounter + ": Return Statement is invalid due to " + e);
                    } else {
                        results.push("Statement " + ++statementCounter + ": Valid Return Statement");
                    }
                }

            } else if (currentToken instanceof UnknownToken) {
                throw new IllegalArgumentException("Illegal token " + currentToken.value);
            } else {
                if (!(currentToken instanceof Semicolon)) {
                    e = new IllegalArgumentException("Illegal start of a statement");
                } else { // semi colon
                    tokens.poll();
                }

            }

            while (e != null) { // if exception occured, to be able to execute new Statement jump to first semicolon separator
                if (tokens.isEmpty()) {
                    break;
                } else {
                    if (tokens.peek().value.equals(";")) {
                        tokens.poll();
                        break;
                    }
                    Token c = tokens.poll();
                }

            }

        }
    }

    private Exception parseWhileStatement() throws Exception {
        try {
            tokens.poll(); //remove while statement token
            if (tokens.isEmpty()) {
                throw new NullPointerException("There is no element to parse.");
            } else {
                Token nextToken = tokens.poll(); // remove next token after while 

                if (nextToken.value.equals("(")) { // if next token is '('
                    parseCondition(); // parsing conditional statement

                    if (!tokens.isEmpty()) {
                        nextToken = tokens.poll();  // if the while statement is correct it should be ")"
                        if (nextToken.value.equals(")")) { // if correct
                            parseBlock(); // parse {.....} 
                        } else {
                            throw new IllegalArgumentException("Missing right paranthesis -> )");
                        }
                    } else {
                        throw new NullPointerException("No element to parse");
                    }
                } else {
                    throw new IllegalArgumentException("Expected \"(\" after while");
                }
            }
        } catch (Exception e) {
            return e;
        }
        return null;
    }

    private Exception parseIfStatement() throws Exception {
        tokens.poll(); //remove if 

        try {
            if (tokens.isEmpty()) {
                throw new NullPointerException("No element to parse");
            } else {
                Token nextToken = tokens.poll(); // remove next token after "if"

                if (nextToken.value.equals("(")) { // if next token is "("
                    parseCondition();  // parsing conditional statement

                    if (!tokens.isEmpty()) {
                        nextToken = tokens.poll();  // if the while statement is correct it should be ")"
                        if (nextToken.value.equals(")")) { // if correct
                            parseBlock(); // parse {.....} 
                        } else {
                            throw new IllegalArgumentException("Missing right paranthesis -> )");
                        }
                    } else {
                        throw new NullPointerException("No element to parse");
                    }
                } else {
                    throw new IllegalArgumentException("Expected \"(\" after if");
                }
            }
        } catch (Exception e) {
            return e;
        }
        return null;
    }

    private void parseCondition() { // Condition = 1st expression + relational Operator + 2nd expression 
        parseExpression(); //  we firstly need to parse first expression
        if (!tokens.isEmpty()) {
            Token nextToken = tokens.poll(); // after parsing first expression we are supposed to poll relational op.

            if (nextToken instanceof RelationalOperators) { // check 
                parseExpression(); // then we can parse the 2nd expression

            } else {
                throw new IllegalArgumentException("Expected Relational Operator instead of "
                        + nextToken + "which is an instance of " + nextToken.getClass().getName());
            }
        } else {
            throw new IllegalStateException("There is no token to parse.");
        }
    }

    private void parseExpression() { // expression = <identifier> or constant or arithmetic operation of both 
        if (!tokens.isEmpty()) {

            Token nextToken = tokens.peek();

            if ((nextToken instanceof Parantheses) && nextToken.value.equals("(")) {
                tokens.poll(); // remove (
                parseExpression();
                if (tokens.isEmpty()) {
                    throw new NullPointerException("There is no element to parse");
                }else{
                    nextToken = tokens.peek();
                    if ((nextToken instanceof Parantheses) && nextToken.value.equals(")")) {
                        tokens.poll();
                        if (tokens.peek() instanceof ArithmeticOperator) {
                            tokens.poll();
                            parseExpression();
                        }
                    }else{
                        throw new IllegalStateException("Missing Right paranthesis -> \")\"");
                    }
                }
            } else {
                nextToken = tokens.peek(); // it is supposed to be constant or identifier
                boolean isExpression = (nextToken instanceof Constant) || (nextToken instanceof Identifier);
                if (isExpression) {
                    tokens.poll(); //remove expression
                    Token nextNoken = tokens.peek(); // check next token 
                    if (nextNoken instanceof ArithmeticOperator) { // if next token is arithmetic op.
                        tokens.poll(); // then poll it arithmetic op
                        parseExpression(); // and there must be another expression to parse
                    }
                } else {
                    throw new IllegalArgumentException("Expected Constant or Identifer instead of " + nextToken + " which is an instance of " + nextToken.getClass().getName());
                }
            }
        } else {
            throw new IllegalStateException("There is no token to parse.");
        }
        

    }


    private void parseBlock() throws Exception {
        Token token = tokens.peek();  // it is suppoosed to be "{"
        if (token.value.equals("{")) {
            tokens.poll(); // poll { token
            while (!tokens.peek().value.equals("}")) {
                parseStatement(); // parse statement or statements between braces , If they exist
            }
            if (tokens.isEmpty()) {
                throw new NullPointerException("Missing \"}\" Because there is no token to parse");
            } else {
                Token nextToken = tokens.peek();
                if (nextToken.value.equals("}")) {
                    tokens.poll(); // poll }
                } else {
                    throw new IllegalArgumentException("Missing \"}\". Because the next token is " + token.value);
                }
            }

        } else { // if not !!
            throw new IllegalArgumentException("Expected \"{\" instead of " + token + " which is an instance of " + token.getClass().getName());
        }
    }

    private Exception parseVariableDeclaration() {
        tokens.poll(); //data type "if" or "char"
        try {
            if (!tokens.isEmpty()) {
                Token token = tokens.peek(); // it is supposed to be identifier 
                if (token instanceof Identifier) {
                    tokens.poll(); // poll identifer 
                } else { // if not an identifier 
                    throw new IllegalArgumentException("Expected identifier instead of " + token + " which is an instance of " + token.getClass().getName());
                }
            } else {
                throw new IllegalStateException("There is no token to parse.");
            }
        } catch (Exception e) {
            return e;
        }

        return null;

    }

    private Exception parseSemicolon() {
        try {
            if (!tokens.isEmpty()) {
                if ((tokens.peek() instanceof Semicolon)) {
                    tokens.poll(); // poll semi colon
                } else {
                    throw new IllegalArgumentException("Missing semicolon");
                }
            } else {
                throw new IllegalStateException("There is no token to parse.");
            }
        } catch (Exception e) {
            return e;
        }
        return null;
    }

    private Exception parseAssignmentStatement() {
        tokens.poll(); // remove identifier 
        try {
            if (!tokens.isEmpty()) {
                if (!(tokens.peek() instanceof AssignOperator)) { // after an identifier the following token must be assign op.
                    throw new IllegalStateException("Missing Assign Operator (=).");
                } else {
                    ; //remove " = "
                    tokens.poll();
                    parseExpression();  // parse expression after assign op.
                }
            } else {
                throw new IllegalStateException("There is no token to parse.");
            }
        } catch (Exception e) {
            return e;
        }
        return null;
    }

    private Exception parseReturnStatement() {
        tokens.poll(); // remove return
        try {
            if (!tokens.isEmpty()) {
                parseExpression(); // parse after return
            } else {
                throw new IllegalStateException("There is no token to parse.");
            }
        } catch (Exception e) {
            return e;
        }
        return null;
    }

}
