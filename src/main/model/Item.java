package model;

import exceptions.LessThanZeroE;
import exceptions.StringLengthZero;

import java.time.LocalDate;

// Represents an item that user spends money on
// It has a name and a price in dollars
public class Item {
    public String name;
    public double price; // in dollars
    public String tag;
    private LocalDate dateOfExpense;
   // private static final LocalDate LOCAL_DATE = LocalDate.now();

    // REQUIRES: itemName has a non-zero length, itemPrice is > 0
    // EFFECTS: name is set to itemName; price is itemPrice in dollars
    public Item(String itemName, double itemPrice, String categoryType) throws LessThanZeroE, StringLengthZero {
        if (itemName.length() == 0) {
            throw new StringLengthZero();
        } else {
            this.name = itemName;
        }
        if (itemPrice <= 0) {
            throw new LessThanZeroE();
        } else {
            this.price = itemPrice;
        }
        if (categoryType.length() == 0) {
            throw new StringLengthZero();
        } else {
            this.tag = categoryType;
        }
        this.dateOfExpense = LocalDate.now();
    }


    // EFFECTS: returns a price for the item
    public double getPrice() {
        return price;
    }

    //EFFECTS: returns name of item
    public String getName() {
        return name;
    }


    //EFFECTS: returns tag of item
    public String getTag() {
        return tag;
    }

    // EFFECTS: returns the time and date the purchase was made
    public LocalDate getDateOfExpense() {
        return dateOfExpense;
    }
}
