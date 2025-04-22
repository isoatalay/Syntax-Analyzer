
package syntaxanalyzer;

class IfStatement extends Token {

    static boolean isIfStatement(String word) {
        return word.equals("if");
    }

    public IfStatement(String value) {
        super(value);
    }
}
