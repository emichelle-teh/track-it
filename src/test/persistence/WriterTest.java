package persistence;

import exceptions.LessThanZeroE;
import exceptions.StringLengthZero;
import model.ItemManager;
import model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WriterTest {
    private static final String TEST_FILE = "./data/testBoughtItemsList.txt";
    private Writer testWriter;
    private ItemManager listItemsForTest;
    ItemManager list;
    Item milk;
    Item dinner;


    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        testWriter = new Writer(new File(TEST_FILE));
       // listItemsForTest = new BoughtItemsList(100);
        try {
            list = new ItemManager(1000);
        } catch (LessThanZeroE e) {
            fail("Exception shouldn't have been caught");
        }
        // making items
        try {
            milk = new Item("Milk", 5.99, "FOOD");
            dinner = new Item("Chicken Dinner", 9.99, "FOOD");
        } catch (LessThanZeroE lessThanZeroE) {
            fail("Exception shouldn't have been caught, all prices okay.");
        } catch (StringLengthZero stringLengthZero) {
            fail("Exception shouldn't have been caught, name & category alright");
        }
        // adding items to the list
        list.addPurchase(milk);
        list.addPurchase(dinner);
    }

    @Test
    void testWriteBoughtItemsList() {
        // save the list to the file
        testWriter.write(list);
        testWriter.close();

        // now read them back in and verify that the accounts have the expected values
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
        } catch (StringLengthZero | LessThanZeroE stringLengthZero) {
            stringLengthZero.printStackTrace();
        }


    }

}
