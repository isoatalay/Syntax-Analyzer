package syntaxanalyzer;

import java.util.LinkedList;
import java.util.Queue;

public class Tokenizer {

    public Queue<Token> tokens;
   

    public Tokenizer(String input) {
        tokens = tokenize(input);
    }

    
    public Queue<Token> tokenize(String input) {
        StringBuilder sb = new StringBuilder();
        Queue<Token> tokens = new LinkedList<>();
        for (int i = 0; i < input.length(); i++) {
            String currentCh = String.valueOf(input.charAt(i)); 
            if (Semicolon.isSemicolon(currentCh)) { // if current ch is semicolon
                flushAndPush(tokens, sb, currentCh);
            } else if (Separator.isSeparator(currentCh)) { // if current ch is separator
                flushAndPush(tokens, sb, currentCh);
            } else if (AssignOperator.isAssignedOperator(currentCh)) { // if current ch is '=' it can be either assigned operator or '=='
                if (i + 1 == input.length()) { //before you check it is kind of relational op, ensure that  you are in bound
                    flushAndPush(tokens, sb, currentCh); // if you are not you dont need to anything
                } else {
                    if (input.charAt(i + 1) == '=') { // if next index is '=' it is relational
                        currentCh += "=";  //handle "=="
                        flushAndPush(tokens, sb, currentCh);
                        i++; // you check the next index, so you can pass it
                    } else { // you ensure that it is assign op
                        flushAndPush(tokens, sb, currentCh);
                    }
                }
            } else if (ArithmeticOperator.isOperator(currentCh)) { // if current ch is + - * / 
                flushAndPush(tokens, sb, currentCh);
            } else if (RelationalOperators.isRelationalOperator(currentCh) || input.charAt(i) == '!') {
                if (i + 1 == input.length()) { // check boundry
                    flushAndPush(tokens, sb, currentCh);
                } else {
                    if (input.charAt(i + 1) == '=') {// handle <= >= !=
                        currentCh += "=";
                        flushAndPush(tokens, sb, currentCh);
                        i++;
                    } else {
                        flushAndPush(tokens, sb, currentCh);
                    }
                }   
            }
            /*
            In the above we need a ' ' (space) to detect this kind of tokens. 
            Once we encounter we need to specify them as a token 
            And also if there is characters just before it we should accept them is a token, even it is a wrong argument
            */
            else {
                if (!Character.isWhitespace(input.charAt(i))) { // ignore \t \n ' ' etc.
                    sb.append(currentCh);
                } else { 
                    if (!sb.isEmpty()) { // if there is white space and sb is not empty
                        tokens.add(createToken(sb.toString())); // add it to your tokens
                        sb.delete(0, sb.length());
                    }
                }
            }
        }
        if (!sb.isEmpty()) {  // check sb is empty to not miss anything
            tokens.add(createToken(sb.toString()));
            sb.delete(0, sb.length());
        }
        return tokens;
    }
    
    public void flushAndPush(Queue<Token> tokens, StringBuilder sb, String currentCh) {
        if (sb.isEmpty()) { // if there is no characters in sb just push current ch
            tokens.add(createToken(currentCh)); 
        } else {
            String oldToken = sb.toString(); // else first push characters to tokens data structure 
            tokens.add(createToken(oldToken)); 
            sb.delete(0, sb.length()); // remove from sb after you push
            tokens.add(createToken(currentCh)); // add current ch
        }
    }

    public Token createToken(String word) {
        if (DataType.isType(word)) {
            return new DataType(word);
        } else if (Identifier.isIdentifier(word)) {
            return new Identifier(word);
        } else if (Separator.isSeparator(word)) {
            return Separator.createSeparator(word);
        } else if (ArithmeticOperator.isOperator(word)) {
            return new ArithmeticOperator(word);
        } else if (IfStatement.isIfStatement(word)) {
            return new IfStatement(word);
        } else if (WhileStatement.isWhileStatement(word)) {
            return new WhileStatement(word);
        } else if (ReturnStatement.isReturnStatement(word)) {
            return new ReturnStatement(word);
        } else if (Constant.isConstant(word)) {
            return new Constant(word);
        } else if (RelationalOperators.isRelationalOperator(word)) {
            return new RelationalOperators(word);
        } else if (Semicolon.isSemicolon(word)) {
            return new Semicolon(word);
        } else if (AssignOperator.isAssignedOperator(word)) {
            return new AssignOperator(word);
        } else {
            return new UnknownToken(word);
        }
    }

}
