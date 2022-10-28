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
        } catch (IOException ignored) {}

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
        var f = (new File(filename)).createNewFile();
        if (!f) throw new IOException();
        return filename;
    }

    /**
     * Read from input file and write to output file
     * @return name of file output was written to
     */
    String writeOutputToFile() {
        // read from file
        var inputs = readFile();
        var builder = new StringBuilder();
        for (var inp : inputs) {
            builder.append(evaluateMaxFreeUrinals(inp)).append("\n");
        }
        try {
            var outfile = createOutputFilename();
            var file = new File(outfile);
            if (!file.exists()) throw new IOException();
            var writer = new FileWriter(file);
            writer.write(builder.toString());
            writer.close();
            System.out.printf("Output written to %s%n", outfile);
            return outfile;
        } catch (IOException e) {
            System.out.println(builder);
        }
        return null;
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
            if (ans.equalsIgnoreCase("y") || ans.isEmpty()) {
                read = true;
            } else if (!ans.equalsIgnoreCase("n")) {
                System.out.println("\n Invalid input, try again");
                retry = true;
            }
        }
        if (read) {
            urinal.writeOutputToFile();
        } else {
            // read from stdin
            System.out.print("Waiting for input: ");
            reader.nextLine();
            String inp = reader.nextLine();
            System.out.println(urinal.evaluateMaxFreeUrinals(inp));
        }
    }
}