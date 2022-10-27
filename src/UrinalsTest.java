import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UrinalsTest {
    Urinals urinals;

    UrinalsTest() {
        urinals = new Urinals();
    }

    @Test
    void validateInput() {
        Assertions.assertFalse(urinals.validateInput(""));
        System.out.println("====== Rishikesh Anand == Test one complete =======");
    }
}