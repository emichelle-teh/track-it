package ui;

import exceptions.LessThanZeroE;
import exceptions.StringLengthZero;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.ItemManager;
import model.Item;

import java.io.IOException;
import java.util.List;

public class AddExpenseController {

    @FXML
    public TextField itemDesc;
    public TextField price;
    public TextField categoryField;
    public AnchorPane startingPane;
    public Label screenTitle;
    public Button addButton;
    public Button backButton;


    // EFFECTS: prints list of items in console
    public void printItemList(ItemManager x) {
        List<Item> dummy = x.getList();
        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("%10s %10s %10s %10s %15s", "NUMBER", "ITEM NAME", "PRICE", "CATEGORY", "DATE");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------");
        for (Item i : dummy) {
            String f = "%10s %10s %10s %10s %15s";
            int position = dummy.indexOf(i) + 1;
            System.out.format(f, position, i.getName(), i.getPrice(), i.getTag(), i.getDateOfExpense());
            System.out.println();
        }
    }

    // MODIFIES: list
    // EFFECTS: gets user input and adds it to the list and brings user to the next page
    public void handleButton(javafx.event.ActionEvent actionEvent) throws IOException {
        //gets all the values from the text fields

        try {
            String itemName = itemDesc.getText();
            String priceItemS = price.getText();
            Double priceItem = Double.parseDouble(priceItemS);
            String category = categoryField.getText();
            if (itemDesc.getLength() < 0 || priceItem < 0) {
                System.out.println("Something is wrong.");
            }
            Item i1 = new Item(itemName, priceItem, category);
            GuiMain.list.addPurchase(i1);
            printItemList(GuiMain.list);
            goToNextPage();
        } catch (StringLengthZero exception) {
            catchStringLengthZeroE();

        } catch (LessThanZeroE exception) {
            catchLessThanZeroE();
        } catch (NumberFormatException exception) {
            catchNumberFormatException();
        }
    }


    // EFFECTS: goes to the next page, which is MainPage.fxml
    private void goToNextPage() throws IOException {
        // going to next screen
        Pane nextPane = null;
        nextPane = (Pane) FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        startingPane.getChildren().clear();
        startingPane.getChildren().add(nextPane);
    }

    private void catchNumberFormatException() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("UH OH");
        alert.setHeaderText("Please correct invalid price field!");
        alert.setContentText("Input a valid number! Don't leave it empty and don't input a symbol/character!");
        alert.show();
    }

    //public void getAlert(String title, String header, String contextText)

    private void catchLessThanZeroE() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("UH OH");
        alert.setHeaderText("Please correct invalid fields");
        alert.setContentText("Don't put a number less than 0!");
        alert.show();
    }

    private void catchStringLengthZeroE() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Oh NO!");
        alert.setHeaderText("Please correct invalid fields...");
        alert.setContentText("Input a valid text with more than 0 characters!");
        alert.show();
    }


    // MODIFIES: list
    // EFFECTS: brings user to the mainPage.fxml
    public void backButtonAction(ActionEvent actionEvent) throws IOException {
        goToNextPage();
    }


}
