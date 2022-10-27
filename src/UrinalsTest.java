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
        Assertions.assertFalse(urinals.validateInput("101010101010101010101"));
        Assertions.assertTrue(urinals.validateInput("10101010101010101010"));
        System.out.println("====== Rishikesh Anand == Test one complete =======");
    }
}