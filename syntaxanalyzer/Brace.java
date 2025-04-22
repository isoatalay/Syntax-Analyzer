
package syntaxanalyzer;

public class Brace extends Separator {
    
    public Brace(String value) {
        super(value);
    }

    @Override
    char getComplement() {
        switch (this.value.charAt(0)) {
            case '{':
                return '}';
            case '}':
                return '{';
            default:
                throw new IllegalArgumentException("brace value (" + this.value + ") is illegal");
        }
    }
    
}
