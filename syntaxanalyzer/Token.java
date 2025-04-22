
package syntaxanalyzer;

abstract class Token {

    String value;

    public Token(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
