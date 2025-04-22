
package syntaxanalyzer;
class RelationalOperators extends Token {

    static boolean isRelationalOperator(String word) {

        return word.equals("<") || word.equals("<=")
                || word.equals(">") || word.equals(">=")
                || word.equals("==") || word.equals("!=");
    }

    public RelationalOperators(String value) {
        super(value);
    }

}
