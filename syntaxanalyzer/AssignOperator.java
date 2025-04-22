
package syntaxanalyzer;
public class AssignOperator extends Token {

    public AssignOperator(String value) {
        super(value);
    }

    static boolean isAssignedOperator(String word) {
        return word.length() == 1 && word.charAt(0) == '=';
    }

}
