
package syntaxanalyzer;

class ArithmeticOperator extends Token {

    static boolean isOperator(String word) {
        char ch = word.charAt(0);
        boolean isChar = ch == '/' || ch == '*'
                || ch == '+' || ch == '-';
        return word.length() == 1 && isChar;
    }

    public ArithmeticOperator(String value) {
        super(value);
    }

}