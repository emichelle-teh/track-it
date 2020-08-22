package persistence;

import exceptions.LessThanZeroE;
import exceptions.StringLengthZero;
import model.Item;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ReaderTest {
    private static final String TEST_FILE = "./data/testBoughtItemsList.txt";
    @Test
    void ReaderConstructorTest() {
        new Reader();
    }

    @Test
    void testParseBoughtItemList() {
        try {
            List<Item> items = Reader.readItems(new File(TEST_FILE));
            Item numberOne = items.get(0);
            assertEquals("Milk", numberOne.getName());
            assertEquals(5.99, numberOne.getPrice());

            Item numberTwo = items.get(1);
            assertEquals("Chicken Dinner", numberTwo.getName());
            assertEquals(9.99, numberTwo.getPrice());

            double budget = Reader.readBudget(new File(TEST_FILE));
            assertEquals(1000, budget);
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        } catch (StringLengthZero stringLengthZero) {
            fail("IOException should not have been thrown");
        } catch (LessThanZeroE e) {
            fail("IOException should not have been thrown");
        }

    }

    @Test
    void testIOException() {
        try {
            Reader.readItems(new File("./data/testAccounts.txt"));
        } catch (IOException | LessThanZeroE | StringLengthZero e) {
            // expected
        }
    }
}
