/*
File authored by: Rishikesh Anand
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

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

    /**
     * Create output file name
     * @return name of new file
     * @throws IOException if unable to create new file, usually because of permissions.
     */
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
        Scanner reader = new Scanner(System.in);
        boolean read = false, retry = true;
        var urinal = new Urinals();
        // ask if user wants to read from file or read from stdin
        while (retry) {
            retry = false;
            System.out.print("Read from urinal.dat? (Y/n) ");
            String ans = reader.next();
            if (ans.toLowerCase().equals("y") || ans.isEmpty()) {
                read = true;
            } else if (ans.toLowerCase().equals("n")) {
                read = false;
            } else {
                System.out.println("\n Invalid input, try again");
                retry = true;
            }
        }
        if (read) {
            // read from file
            var inputs = urinal.readFile();
            var builder = new StringBuilder();
            for (var inp : inputs) {
                builder.append(urinal.evaluateMaxFreeUrinals(inp)).append("\n");
            }
            try {
                var outfile = urinal.createOutputFilename();
                var file = new File(outfile);
                if (!file.exists()) throw new IOException();
                var writer = new FileWriter(file);
                writer.write(builder.toString());
                writer.close();
            } catch (IOException e) {
                System.out.println(builder.toString());
            }
        } else {
            // read from stdin
            System.out.print("Waiting for input: ");
            reader.nextLine();
            String inp = reader.nextLine();
            System.out.println(urinal.evaluateMaxFreeUrinals(inp));
        }
    }
}