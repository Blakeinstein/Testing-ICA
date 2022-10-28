import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


class UrinalsTest {
    private static final File sourceFile = new File("./urinal.dat");
    private static final File tempFile = new File("./urinal.dat.bak");
    private final Urinals urinals = new Urinals();

    void writeContents(String content) {
        try {
            var writer = new FileWriter(sourceFile);
            writer.write(content);
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void cleanOutputFiles() {
        var outFile = "rule.txt";
        var file = new File(outFile);
        if (file.exists()) {
            var fileNumber = 1;
            do {
                file.delete();
                file = new File(String.format("rule%d.txt", fileNumber++));
            } while (file.exists());
        }
    }

    @BeforeAll
    static void setup() {
        var flag = sourceFile.renameTo(tempFile);
        Assumptions.assumeTrue(flag, "Failed to backup file");
        System.out.println("Backed up input file");
    }

    @Test
    void validateInputSize() {
        // validate based on input size.

        // length 0 should not be accepted.
        Assertions.assertFalse(urinals.validateInput(""), "Length 0 should not be accepted.");
        // length 1 should be accepted.
        Assertions.assertTrue(urinals.validateInput("0"), "Length 1 should be accepted");
        // length 2 should be accepted.
        Assertions.assertTrue(urinals.validateInput("01"), "Length 2 should be accepted");

        // length 21 should not be accepted.
        Assertions.assertFalse(urinals.validateInput("101010101010101010101"), "Length 21 should not be accepted");
        // length 20 should be accepted
        Assertions.assertTrue(urinals.validateInput("10101010101010101010"), "Length 20 should be accepted");
        // length 19 should be accepted
        Assertions.assertTrue(urinals.validateInput("1010101010101010101"), "Length 19 should be accepted");
        System.out.println("====== Rishikesh Anand == Test one complete =======");
    }
    @Test
    void validateInputChars() {
        // validate based on valid char set

        // ascii chars should not be accepted
        Assertions.assertFalse(urinals.validateInput("ABC"), "Ascii chars should not be accepted");
        // unicode chars apart from 0/1 should not be accepted
        Assertions.assertFalse(urinals.validateInput("❤️❤️❤️❤️"), "Unicode chars apart from 0/1 should not be accepted");
        // numbers apart from 0/1 should not be accepted
        Assertions.assertFalse(urinals.validateInput("12345678912345678912"), "Numbers apart from 0/1 should not be accepted");
        // a valid combination of 0/1 should be accepted
        Assertions.assertTrue(urinals.validateInput("10101010101010101010"), "A valid combination of 0/1 should be accepted");
        System.out.println("====== Rishikesh Anand == Test two complete =======");
    }

    @Test
    void validateInputContents() {
        // validate input state

        // no consecutive 1 should be accepted
        Assertions.assertTrue(urinals.validateInput("10101010101010101010"), "String with no consecutive 1s should be accepted");

        // consecutive 1s should not be accepted
        Assertions.assertFalse(urinals.validateInput("011"), "String with consecutive 1s should not be accepted");

        // no 1s should be accepted
        Assertions.assertTrue(urinals.validateInput("000"), "String without 1s should be accepted");
        System.out.println("====== Rishikesh Anand == Test three complete =======");
    }

    @Test
    void readFile() {
        // Validate if file is read correctly

        var lines = new String[]{
                "1010101010",
                "001010000",
                "00000"
        };

        // Test for EOF terminator
        // prepare input file
        writeContents(String.join("\n", lines));
        Assumptions.assumeTrue(sourceFile.canRead(), "Unable to read input file for testing");

        Assertions.assertArrayEquals(
                urinals.readFile().toArray(),
                lines,
                "File contents should be read correctly with EOF terminator"
        );

        // Test for -1 terminator
        // prepare input file
        var newLines = new ArrayList<>(Arrays.asList(lines));
        newLines.add("-1");
        writeContents(String.join("\n", newLines));
        Assumptions.assumeTrue(sourceFile.canRead(), "Unable to read input file for testing");

        Assertions.assertArrayEquals(
                urinals.readFile().toArray(),
                lines,
                "File contents should be read correctly with -1 terminator"
        );

        // Test for missing file
        // delete input file
        var flag = sourceFile.delete();
        Assumptions.assumeTrue(flag, "Unable to delete file");
        Assertions.assertTrue(urinals.readFile().isEmpty(), "Missing file should yield empty list");

        System.out.println("====== Rishikesh Anand == Test four complete =======");
    }

    @Test
    void evaluateMaxFreeUrinals() {
        // validate output for all possible inputs

        // invalid input
        Assertions.assertEquals(
                -1,
                urinals.evaluateMaxFreeUrinals("011"),
                "Should return -1 for invalid input state"
        );

        // valid inputs
        Assertions.assertEquals(
                1,
                urinals.evaluateMaxFreeUrinals("10001"),
                "Should return correct output for valid input"
        );
        Assertions.assertEquals(
                0,
                urinals.evaluateMaxFreeUrinals("1001"),
                "Should return correct output for valid input"
        );
        Assertions.assertEquals(
                3,
                urinals.evaluateMaxFreeUrinals("00000"),
                "Should return correct output for valid input"
        );
        Assertions.assertEquals(
                2,
                urinals.evaluateMaxFreeUrinals("0000"),
                "Should return correct output for valid input"
        );
        Assertions.assertEquals(
                1,
                urinals.evaluateMaxFreeUrinals("01000"),
                "Should return correct output for valid input"
        );

        System.out.println("====== Rishikesh Anand == Test five complete =======");
    }

    @Test
    void createOutputFilename() throws IOException {
        // validate valid file name generation

        cleanOutputFiles();

        // no previous output
        Assumptions.assumeFalse(
                (new File("rule.txt")).exists(),
                "rule.txt already exists"
        );
        Assertions.assertEquals(
                "rule.txt",
                urinals.createOutputFilename(),
                "First execution should return rule.txt"
        );

        // rule.txt exists
        Assumptions.assumeFalse(
                (new File("rule1.txt")).exists(),
                "rule1.txt already exists"
        );
        Assertions.assertEquals(
                "rule1.txt",
                urinals.createOutputFilename(),
                "Second execution should return rule1.txt"
        );

        // rule.txt and rule1.txt exits
        Assumptions.assumeFalse(
                (new File("rule2.txt")).exists(),
                "rule2.txt already exists"
        );
        Assertions.assertEquals(
                "rule2.txt",
                urinals.createOutputFilename(),
                "Third execution should return rule2.txt"
        );

        // create dummy files to simulate external changes
        try {
            for (int i = 3; i < 6; i++) {
                var filename = String.format("rule%d.txt", i);
                var file = new File(filename);
                Assumptions.assumeFalse(
                        file.exists(),
                        String.format("%s already exists", filename)
                );

                file.createNewFile();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // next file name should be 6th
        Assumptions.assumeFalse(
                (new File("rule6.txt")).exists(),
                "rule6.txt already exists"
        );
        Assertions.assertEquals(
                "rule6.txt",
                urinals.createOutputFilename(),
                "filename should reflect changes from external sources"
        );

        cleanOutputFiles();

        System.out.println("====== Rishikesh Anand == Test six complete =======");
    }

    @AfterAll
    static void cleanup() {
        var flag = tempFile.renameTo(sourceFile);
        Assumptions.assumeTrue(flag, "Failed to restore file");
        System.out.println("Restored Input file");

    }
}