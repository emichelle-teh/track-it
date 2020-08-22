package model;

import exceptions.LessThanZeroE;
import exceptions.StringLengthZero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ItemTest {
    Item itemSpent1;

    @BeforeEach
    public void setup(){
        try {
            itemSpent1 = new Item("room",999,"rent");
        } catch (LessThanZeroE lessThanZeroE) {
            System.out.println("The price is less than 0...");
        } catch (StringLengthZero stringLengthZero) {
            System.out.println("Input for name/category is empty...TRY AGAIN.");
        }
    }

    @Test
    public void makeItemLessThanZeroE() {
        try {
            Item itemWrong = new Item("pillow", -1, "bedding");
            fail("Exception should have been thrown");
        } catch (LessThanZeroE exception) {
            System.out.println("Exception caught because price is -1");
        } catch (StringLengthZero exception) {
            fail("Wrong exception caught");
        }
    }


    @Test
    public void makeItemNameWrong() {
        try {
            Item itemWrong = new Item("", 10, "bedding");
            fail("Exception should have been thrown");
        } catch (LessThanZeroE exception) {
            fail("Wrong exception caught");
        } catch (StringLengthZero exception) {
            System.out.println("Exception caught because name field is empty");
        }
    }

    @Test
    public void makeItemCategoryWrong() {
        try {
            Item itemWrong = new Item("food", 10, "");
            fail("Exception should have been thrown");
        } catch (LessThanZeroE exception) {
            fail("Wrong exception caught");
        } catch (StringLengthZero exception) {
            System.out.println("Exception caught because category field is empty");
        }
    }

    @Test
    public void getNameTest(){
        assertEquals("room", itemSpent1.getName());
    }

    @Test
    public void getTagTest(){
        assertEquals("rent",itemSpent1.getTag());
    }

    @Test
    public void getDateOfExpenseTest(){
        assertEquals(LocalDate.now(), itemSpent1.getDateOfExpense());
    }

    @Test
    public void getPriceTest(){
        assertEquals(999,itemSpent1.getPrice());
    }

}
