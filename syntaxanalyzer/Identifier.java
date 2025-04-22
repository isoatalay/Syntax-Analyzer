
package syntaxanalyzer;


public class Identifier extends Token {

    static boolean isIdentifier(String word) {
        char ch = word.charAt(0);
        return word.length() == 1 && ch <= 'z' && ch >= 'a';
    }

    public Identifier(String value) {
        super(value);
    }
}