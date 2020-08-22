package model;

import exceptions.LessThanZeroE;
import persistence.Reader;
import persistence.Saveable;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

// Represents a list of items bought

public class ItemManager implements Saveable {
    private List<Item> boughtItemsList;
//    public double totalPurchase;    // total amount of money spent in the list
    public double maxBudget;

    // EFFECTS: construct an empty ItemSpent list
    public ItemManager(double limit) throws LessThanZeroE {
        boughtItemsList = new ArrayList<>();
        if (limit < 0) {
            throw new LessThanZeroE();
        } else {
            maxBudget = limit;
        }
    }

    // MODIFIES: this
    // EFFECTS: change maxBudget based on what user wants to input
    public void setMaxBudget(double limit) throws LessThanZeroE {
        if (limit < 0) {
            throw new LessThanZeroE();
        } else {
            maxBudget = limit;
        }
    }

    // EFFECTS: returns maxBudget of an ItemManager
    public double getMaxBudget() {
        return maxBudget;
    }

    // MODIFIES: this
    // EFFECTS: adds a purchase to the expense list where item
    // is the grocery item, and the price gets added to the
    // total purchase
    public void addPurchase(Item itemName) {
        boughtItemsList.add(itemName);
//        totalPurchase = itemName.price + totalPurchase;
    }


    // REQUIRES: itemPosition >= 0
    // MODIFIES: this
    // EFFECTS: removes the item in the row that the user inputs in
    public void removePurchase(int itemPosition) {
        Item itemRemoved = boughtItemsList.remove(itemPosition - 1);
//        totalPurchase = totalPurchase - itemRemoved.getPrice();
    }


    // REQUIRES: itemPosition to be in the list
    // MODIFIES: this
    // EFFECTS: removes the item that the user inputs
    public void removePurchase(Item item) {
        boughtItemsList.remove(item);
//        totalPurchase = totalPurchase - item.getPrice();
    }

    // EFFECTS: returns total amount of money spent
    public double getTotalAmountSpent() {
        double totalPurchase = 0;
//
        for (Item item : boughtItemsList) {
            totalPurchase += item.getPrice();
        }
        return totalPurchase;
    }

    // EFFECTS: returns total number of purchases in the list
    public int getTotalNumberOfExpenses() {
        return boughtItemsList.size();
    }

    // EFFECTS: return the list
    public List<Item> getList() {
        return boughtItemsList;
    }

    // EFFECTS: returns true if totalPurchase > maxBudget, false if totalPurchase <= list.maxBudget
    public boolean exceedBudget() {
        return getTotalAmountSpent() > maxBudget;
    }

    // EFFECTS: returns amount that the user overspends
    public double exceededBudgetAmount() {
        return maxBudget - getTotalAmountSpent();
    }



    @Override
    public void save(PrintWriter printWriter) {
        printWriter.println(maxBudget);

        for (Item i: boughtItemsList) {
            printWriter.print(i.name);
            printWriter.print(Reader.DELIMITER);
            printWriter.print(i.price);
            printWriter.print(Reader.DELIMITER);
            printWriter.print(i.tag);
            printWriter.print(Reader.DELIMITER);
            printWriter.println(i.getDateOfExpense());
        }
    }

    // EFFECTS: makes the list
    public void makeList(List<Item> listItem) {
       // boughtItemsList = listItem;boughtItemsList.clear();
        boughtItemsList.clear();
        for (Item item : listItem) {
            this.addPurchase(item);
        }
    }
}
