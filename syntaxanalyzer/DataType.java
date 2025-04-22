
package syntaxanalyzer;

class DataType extends Token {

    static boolean isType(String word) {
        return word.equals("int") || word.equals("char");
    }

    public DataType(String value) {
        super(value);
    }
}
