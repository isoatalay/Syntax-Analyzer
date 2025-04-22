
package syntaxanalyzer;
abstract class Separator extends Token {

    static boolean isSeparator(String word) {
        char separator = word.charAt(0);
        boolean isSeparator = separator == '[' || separator == ']'
                || separator == '(' || separator == ')'
                || separator == '{' || separator == '}';
        return word.length() == 1 && isSeparator;

    }
    
    public static Separator createSeparator(String value){
        if (value.equals("{") || value.equals("}") ){
            return new Brace(value);
        }else if (value.equals("(") || value.equals(")")) {
            return new Parantheses(value);
        }else{
            throw new IllegalStateException("skadmsd");
        }
}

    abstract char getComplement() ;
       
    public Separator(String value) {
        super(value);
    }
}
