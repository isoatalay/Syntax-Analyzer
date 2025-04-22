
package syntaxanalyzer;

class Semicolon extends Token {

    static boolean isSemicolon(String word) {
        return word.length() == 1 && word.charAt(0) == ';';
    }

    public Semicolon(String value) {
        super(value);
    }
}
