import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class UrinalsTest {
    private static final File sourceFile = new File("./urinal.dat");
    private static final File tempFile = new File("./urinal.dat.bak");
    private final Urinals urinals = new Urinals();

    @BeforeAll
    static void setup() {
        var flag = sourceFile.renameTo(tempFile);
        Assertions.assertTrue(flag, "Failed to backup file");
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
        Assertions.assertTrue(urinals.validateInput("10101010101010101010"), "String with no consecutive s should be accepted");

        // consecutive 1s should not be accepted
        Assertions.assertFalse(urinals.validateInput("011"), "String with consecutive 1s should not be accepted");

        // no 1s should be accepted
        Assertions.assertTrue(urinals.validateInput("000"), "String without 1s should be accepted");
        System.out.println("====== Rishikesh Anand == Test three complete =======");
    }

    @AfterAll
    static void cleanup() {
        var flag = tempFile.renameTo(sourceFile);
        Assertions.assertTrue(flag, "Failed to restore file");
        System.out.println("Restored file");
    }
}