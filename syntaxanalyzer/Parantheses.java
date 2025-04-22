
package syntaxanalyzer;
public class Parantheses extends Separator {

    public Parantheses(String value) {
        super(value);
    }

   char getComplement() {
        switch (this.value.charAt(0)) {
            case '(':
                return ')';
            case ')':
                return '(';
            default:
                throw new IllegalArgumentException("parantheses value (" + this.value + ") is illegal");
        }
    }
    
}
