
package syntaxanalyzer;
class Constant extends Token {

    static boolean isConstant(String word) {
        int i;
        String sub = "-";
        String sum = "+";
        String mult = "*";
        String div = "/";
        
        boolean flag = word.contains(sub) || word.contains(sum)
                || word.contains(mult)|| word.contains(div); 
        try {
            if (flag) {
                throw new Exception();
            }
            
            i = Integer.valueOf(word);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Constant(String value) {
        super(value);
    }
}