# 🆃🆁🅰🅲🅺 🅸🆃! #

## A Budgeting application for CPSC 210 ##

### **The Story** ### 

Finding myself losing track of all the money spent during Black Friday, I decided to create
an application that helps solve this problem! This application will allow users to input spendings,
and can help them analyze and visualize what they spend the most money on, as well as ease the process of tracking bills.
Hopefully I can implement a system that integrates taking a picture of a bill and inputting that 
into the program. 

**Who will use it?:** Anyone who wants to track their spendings!

This application aims to achieve the following user stories:
1. As a user, I want to be able to add an item with their price to the list of things I spent money 
2. As a user, I want to be able to remove an item 
3. As a user, I want to be able to view all expenditures
4. As a user, I want to be able to set/change a limit & get an message when I exceed that limit (or budget)

5. As a user, I want to be able to save a final todo list 
6. As a user, I want to be able to open the todo list and re-edit it


#### **Instructions For Grader** #### 

1. When you first start my application, GUIMain, you will automatically trigger my audiovisual component.
 
2. Then, please click the button that indicates that this is your first time using the app. 

3. You will be prompted to set a budget, which is the first part user story (4).

4. You can add an expense (first required event) directly after setting a budget (when you use it for the first time).
Once you have used the app, to add an item, you just have to click the "Add" button that will redirect you to a page with required fields.

5. You can remove an expense (second required event) by selecting the expense on the table, and then clicking the "Remove" button.

6. You do not have to generate the third required event (viewing all expenditures) because it's immediately on the table. 

7. You can change your budget's limit & get an error message (generate the second part of the fourth required event) by pressing the "Change Budget" button.

8. You can save the state of my application (fifth required event) by clicking the "Save" button or the "Save & Quit" button. 

Note that you can only reach this page if you have finished selecting whether this is the first time running the app, or loading previous data.

9. If you press "Save & Quit", you will exit the application and stop the music from playing.

10. To load a previous list of expenses (sixth required event), run the application and click "Load old list". 

11. You will then be able to add or remove an expense from the loaded list. 

#### **Phase 4: Task 2** #### 
I have chosen to test and design a robust class. 

These classes are: ItemManager and Item.

**For ItemManager:**
The method that is robust is the constructor.

"public ItemManager(double limit) throws LessThanZeroE"

LessThanZeroE will be thrown if the budget inputted is a negative number, because a budget can either be 0 or greater than 0. 

You can test this exception by: 
1) When you start the application the first time, pick this is my first time starting.
2) Try to input a wrong budget, such as a negative number. 
3) A popup message should appear, indicating that you should input positive numbers.Try to put a valid input!

This exception is handled in SetBudgetController.
Similarly, this will happen too when you try to set a negative value as a budget in the MainPage (the page with the table).
The exception will be handled by MainPageController.

To make it even more robust, if you enter a non-valid number in any of the fields where you're supposed to put/set a budget, such as an symbol or -, there will also be a popup warning window. 

This is because in the MainPageController, a NumberFormatException thrown and handled by the function setBudget().
NumberFormatException is also thrown and caught in the SetBudgetController, in the moveToExpensePage(actionEvent) method, that takes in the budget and makes a new BudgetList with that budget.

**For Item:** 

The method that is robust is the constructor.

"public Item(String itemName, double itemPrice, String categoryType) that throws two exceptions: LessThanZeroE, StringLengthZero."

LessThanZeroE will be thrown if the price inputted is a negative number. 

StringLengthZeroE will be thrown if the user doesn't input anything in the name & category fields.

These exceptions will be caught in the AddExpenseController class, specifically in the method "handleButton".

You can test these exceptions by: 
1) Run the GUIMain 
2) Whenever you add an expense,you can try to not put anything in the field. An alert box should pop up. Press Ok to close it.
3) Try to put a symbol in the price field, or leave it empty. In both cases, an alert box should pop up and your fields will be cleared.
The symbols won't work because a NumberFormatException is thrown when a symbol is inputted in the priceField of the GUI. 
This exception will be handled by the handleButton() method too. 

**You will not be allowed to continue unless you input the fields correctly :)**

I know it's a bit weird to be able to input a " " and have it pass, but my goal is just to make sure that SOMETHING or SOME STRING is inputted by the user.

Due to the Item throwing exceptions, when the Reader does parseItem(), an exception is also thrown. 
This exception will be thrown when a reader is reading a corrupted file. 

This when a Item has a empty file name/category, or the price is negative. 

Whenever this happens in the OptionsController loadList(), an exception is caught and a popup window is shown.

You can test this exception by: 
1) Editing the list.txt and making one of the fields wrong
2) Then, run the GUIMain --> load list, it should have an error!


#### **Phase 4: Task 3 - Coupling and Cohesion** #### 

**Problem 1:** 
Before, "totalPurchase" was a public field that the ItemManager had to keep track of. Every time I added or remove something, I would
calculate the total purchase/modify it. This made other classes that had methods that depended on totalPurchase access the field directly.
It made methods not cohesive since adding an item/removing an item should be the sole function of a method, but it was also
calculating the totalPurchase.

Hence, I made a method that said getTotalPurchase and created a local variable there.
The method would return total purchase by looping through a list of items bought and calculating the total spent. 
 
This made my controllers less dependent on the field directly, and was useful in the methods in the MainPageController related
to setting the warning text on the page. 

**Problem 2:**
In the MainPage controller, and the setBudgetController of the GUI, I was making the GUI directly change the ItemManager's 
maxBudget by directly accessing the field. This isn't supposed to be the responsibility of the GUI.
Hence, I decided to make this a function in the ItemManager class. 

public void setBudget(ActionEvent actionEvent) throws LessThanZeroE {
        String budgetS = budget.getText();
        Double budgetNew = Double.parseDouble(budgetS);
        GuiMain.list.maxBudget = budgetNew;

Now: 

 // MODIFIES: this
    // EFFECTS: change maxBudget based on what user wants to input
    public void setMaxBudget(double limit) throws LessThanZeroE {
        if (limit < 0) {
            throw new LessThanZeroE();
        } else {
            maxBudget = limit;
        }
    }

**Problem 3:**
In the old version of SetWarning() in the MainPageController(), I was making the GUI do the calculations for exceededBudget.

For example: 
double totalPurchase = list.getTotalAmountSpent();
        double exceedBudget = list.maxBudget - totalPurchase;
        String exceedBudgetText = String.format("%.2f", exceedBudget);
        
My GUI also was the class that determined whether the ItemManager exceeded budget. 

Hence, I made was to make exceedBudget() method and a calculateExceededAmount() method in the ItemManager class. 

exceedBudget() returns a true if the user exceeded the budget and false if the user didn't.

calculateExceededAmount() just does the calculation. 

This increased cohesion between the classes. 

**Problem 4:**
A lot of the methods in my GUI were super NOT cohesive. For example, loadList would be in charge of: 
- loading the list 
- catching the 2 exceptions 
- going to the next page 
- making an ItemManager 

This would also similarly happen in other controllers. My methods were all in one super big one. Hence, I refactored all of them 
and made them into helper methods, making them more distinct in what they do. I asked Giovanni and he said it's technically making my 
methods more cohesive...!

I just put all this because a TA suggested describing more than 1 problem just in case I didn't fulfill the cohesion/coupling requirement 
since my code is so simple.

A prof suggested I use a Singleton in my program, but I didn't want to because in the future, I would like to have more than one instance
of the ItemManager class. 

##ANYWAYS, THANKS FOR GRADING ME BERRYWANNNNN! *SURPRISE* i'll miss having you as my TA!












