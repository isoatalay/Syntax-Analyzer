
package syntaxanalyzer;
class WhileStatement extends Token {

    static boolean isWhileStatement(String word) {
        return word.equals("while");
    }

    public WhileStatement(String value) {
        super(value);
    }
}

