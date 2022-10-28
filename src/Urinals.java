/*
File authored by: Rishikesh Anand
 */

import java.io.*;
import java.util.ArrayList;

public class Urinals {
    /**
     * Validates the input to check if it conforms to input guidelines
     * @param input the input string
     * @return true if input is valid, false otherwise.
     */
    Boolean validateInput(String input) {
        var n = input.length();
        if (n < 1 || n > 20) return false;
        for (var ch : input.toCharArray()) {
            if (!(ch == '1' || ch == '0')) {
                return false;
            }
        }
        for (int i = 0; i < n-1; i++) {
            if (input.charAt(i) == '1' && input.charAt(i) == input.charAt(i+1)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Read "urinal.dat" and return list of inputs
     * @return An array list of inputs from "urinal.dat"
     */
    ArrayList<String> readFile() {
        var list = new ArrayList<String>();
        try {
            var reader = new BufferedReader(new FileReader("./urinal.dat"));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("-1")) break;
                list.add(line);
            }
        } catch (IOException e) {}

        return list;
    }

    /**
     * Calculates max number of urinals that can be occupied for a given input.
     * @param input Initial state to consider
     * @return -1 for invalid input, max number of available urinals otherwise.
     */
    int evaluateMaxFreeUrinals(String input) {
        if (!validateInput(input)) return -1;
        int count = 0;
        var chars = input.toCharArray();
        var n = input.length();
        for (int i = 0; i < n; i++) {
            if (chars[i] == '0' && ((i==0 || chars[i-1] ==  '0') & (i==n-1 || chars[i+1] == '0'))) {
                count++;
                chars[i] = '1';
            }
        }
        return count;
    }

    String createOutputFilename() throws IOException {
        String filename = "rule.txt";
        if ((new File(filename)).exists()) {
            int fileNumber = 1;
            do {
                filename = String.format("rule%d.txt", fileNumber++);
            } while((new File(filename)).exists());
        }
        (new File(filename)).createNewFile();
        return filename;
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}