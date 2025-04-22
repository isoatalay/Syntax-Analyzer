package syntaxanalyzer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;


public class SyntaxAnalyzer {

    public static void main(String[] args) throws Exception {

       

        if (args.length < 2) {
            System.out.println("Usage: java MyClass <input file> <output file>");
            return;
        }

        String inputFileName = args[0];
        String outputFileName = args[1];

        try {
            String input = readFile(inputFileName);
            Tokenizer tk = new Tokenizer(input);
            Parser pr = new Parser(tk.tokens);
            writeFile(outputFileName, pr.results);

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private static String readFile(String inputFileName) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }

        reader.close();
        return sb.toString();

    }

    private static void writeFile(String outputFileName, Stack<String> results) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));
        for (String result : results) {
            writer.write(result);
            writer.write("\n");
        }
        writer.close();

    }

}
