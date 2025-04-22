
package syntaxanalyzer;
class ReturnStatement extends Token {

    static boolean isReturnStatement(String word) {
        return word.equals("return");
    }

    public ReturnStatement(String value) {
        super(value);
    }
}
