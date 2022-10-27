import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UrinalsTest {
    Urinals urinals;

    UrinalsTest() {
        urinals = new Urinals();
    }

    @Test
    void validateInputSize() {
        Assertions.assertFalse(urinals.validateInput(""));
        Assertions.assertTrue(urinals.validateInput("0"));
        Assertions.assertTrue(urinals.validateInput("01"));

        Assertions.assertFalse(urinals.validateInput("101010101010101010101"));
        Assertions.assertTrue(urinals.validateInput("10101010101010101010"));
        Assertions.assertTrue(urinals.validateInput("1010101010101010101"));
        System.out.println("====== Rishikesh Anand == Test one complete =======");
    }
    @Test
    void validateInputChars() {
        Assertions.assertFalse(urinals.validateInput("ABC"));
        Assertions.assertFalse(urinals.validateInput("❤️❤️❤️❤️"));
        Assertions.assertFalse(urinals.validateInput("12345678912345678912"));
        Assertions.assertTrue(urinals.validateInput("10101010101010101010"));
        System.out.println("====== Rishikesh Anand == Test two complete =======");
    }

    @Test
    void validateInputContents() {
        Assertions.assertTrue(urinals.validateInput("10101010101010101010"));
        Assertions.assertFalse(urinals.validateInput("011"));
        Assertions.assertFalse(urinals.validateInput("111"));
        Assertions.assertTrue(urinals.validateInput("000"));
        System.out.println("====== Rishikesh Anand == Test three complete =======");
    }
}