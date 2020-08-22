package model;
import exceptions.LessThanZeroE;
import exceptions.StringLengthZero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

//Unit tests for ExpenseList Class
public class ItemManagerTest {

    ItemManager list;
    Item milk;
    Item room;
    Item dinner;
    Item chair;
    Item tea;

    @BeforeEach
    public void setup() {
        try {
            list = new ItemManager(1000);
        } catch (LessThanZeroE e) {
            fail("Exception shouldn't have been caught");
        }
        try {
            milk = new Item("Milk", 5.99, "FOOD");
        } catch (LessThanZeroE lessThanZeroE) {
            fail("Exception shouldn't have been caught");
        } catch (StringLengthZero stringLengthZero) {
            fail("Exception shouldn't have been caught");
        }
        try {
            dinner = new Item("Chicken Dinner", 9.99, "FOOD");
        } catch (LessThanZeroE lessThanZeroE) {
            fail("Exception shouldn't have been caught");
        } catch (StringLengthZero stringLengthZero) {
            fail("Exception shouldn't have been caught");
        }
        try {
            room = new Item("Rent 50m^2", 1099, "RENT");
        } catch (LessThanZeroE lessThanZeroE) {
            fail("Exception shouldn't have been caught");
        } catch (StringLengthZero stringLengthZero) {
            fail("Exception shouldn't have been caught");
        }
        try {
            chair = new Item("Chair", 1000, "OTHERS");
        } catch (LessThanZeroE lessThanZeroE) {
            fail("Exception shouldn't have been caught");
        } catch (StringLengthZero stringLengthZero) {
            fail("Exception shouldn't have been caught");
        }
        try {
            tea = new Item("Expensive tea", 2000, "food");
        } catch (LessThanZeroE lessThanZeroE) {
            fail("Exception shouldn't have been caught");
        } catch (StringLengthZero stringLengthZero) {
            fail("Exception shouldn't have been caught");
        }

    }

    @Test
    public void addPurchaseTest() {
        // adding an item
        list.addPurchase(milk);
        assertEquals(1, list.getTotalNumberOfExpenses());
        // adding a second item
        assertEquals(5.99, list.getTotalAmountSpent());
        list.addPurchase(dinner);
        assertEquals(5.99 + 9.99, list.getTotalAmountSpent());
        assertEquals(2, list.getTotalNumberOfExpenses());
        // removing an item
        list.removePurchase(1);
        assertEquals(1, list.getTotalNumberOfExpenses());
        assertEquals(9.99, list.getTotalAmountSpent());

    }

    @Test
    public void addPurchaseTestExceedBudget() {
        //add an item bigger than 1000
        list.addPurchase(room);
        assertEquals(1, list.getTotalNumberOfExpenses());
        assertEquals(1099, list.getTotalAmountSpent());

    }

    @Test
    public void removePurchaseTest() {
        // adding an item
        list.addPurchase(milk);
        assertEquals(1, list.getTotalNumberOfExpenses());
        // adding a second item
        assertEquals(5.99, list.getTotalAmountSpent());
        list.addPurchase(dinner);
        //removing an item
        list.removePurchase(1);
        assertEquals(1, list.getTotalNumberOfExpenses());

    }

    @Test
    public void getListTest() {
        list.addPurchase(milk);
        assertEquals(1, list.getList().size());
    }

    @Test
    public void addPurchaseEqualsBudget() {
        list.addPurchase(dinner);
        assertEquals(1, list.getTotalNumberOfExpenses());
        assertTrue(list.getTotalAmountSpent() <= 1000);
    }

    @Test
    public void addPurchaseGreaterThanBudget() {
        list.addPurchase(tea);
        assertEquals(2000, list.getTotalAmountSpent());
        assertEquals(1, list.getTotalNumberOfExpenses());
        list.addPurchase(chair);
        assertEquals(3000, list.getTotalAmountSpent());
        assertEquals(2, list.getTotalNumberOfExpenses());
    }

    @Test
    public void makeListTest() {
        ItemManager aNewList = null;
        try {
            aNewList = new ItemManager(1000);
        } catch (LessThanZeroE e) {
            fail("Exception shouldn't have been thrown");
        }
        aNewList.addPurchase(room);
        aNewList.addPurchase(chair);
        list.makeList(aNewList.getList());
        assertEquals(aNewList.getList(), list.getList());
    }

    @Test
    public void addPurchaseTestWithNewRemove() {
        // adding an item
        list.addPurchase(milk);
        assertEquals(1, list.getTotalNumberOfExpenses());
        // adding a second item
        assertEquals(5.99, list.getTotalAmountSpent());
        list.addPurchase(dinner);
        assertEquals(5.99 + 9.99, list.getTotalAmountSpent());
        assertEquals(2, list.getTotalNumberOfExpenses());
        // removing an item
        list.removePurchase(milk);
        assertEquals(1, list.getTotalNumberOfExpenses());
        assertEquals(9.99, list.getTotalAmountSpent());

    }

    @Test
    public void invalidLimitTest() {
        try {
            ItemManager i1 = new ItemManager(-100);
            fail("Exception should have been thrown");
        } catch (LessThanZeroE exception) {
            System.out.println("Limit is negative...");
        }
    }

    @Test
    public void setBudgetTestCorrectly() {
        try {
            list.setMaxBudget(200);
        } catch (LessThanZeroE e) {
            fail("Exception should not be caught");
        }
    }

    @Test
    public void setBudgetTestWrongly() {
        try {
            list.setMaxBudget(-200);
        } catch (LessThanZeroE e) {
           System.out.println("Yay, acception caught!");
        }
    }

    @Test
    public void getMaxBudgetTest() {
        assertEquals(1000, list.getMaxBudget());
    }

    @Test
    public void exceedBudgetTrue() {
        try {
            list.setMaxBudget(200);
        } catch (LessThanZeroE e) {
            fail("Exception should not be caught");
        }
        list.addPurchase(tea);
        assertTrue(list.exceedBudget());
    }

    @Test
    public void exceedBudgetFalse() {
        try {
            list.setMaxBudget(200);
        } catch (LessThanZeroE e) {
            fail("Exception should not be caught");
        }
        list.addPurchase(dinner);
        assertFalse(list.exceedBudget());
    }

    @Test
    public void exceededBudgetAmountTest() {
        try {
            list.setMaxBudget(200);
        } catch (LessThanZeroE e) {
            fail("Exception should not be caught");
        }
        list.addPurchase(chair);
        assertEquals(-800, list.exceededBudgetAmount());
    }
}
