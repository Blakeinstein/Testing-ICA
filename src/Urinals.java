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

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}