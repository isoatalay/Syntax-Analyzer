
package syntaxanalyzer;

public class UnknownToken extends Token {
    
    public UnknownToken(String value) {
        super(value);
    }
    
    public void detectError(){
        if (value.startsWith("int") || value.startsWith("char")) {
            
        }
    }
    
    
}
