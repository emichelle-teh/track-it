package ui;

import exceptions.LessThanZeroE;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.ItemManager;
import model.Item;
import persistence.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static ui.GuiMain.LIST_FILE;
import static ui.GuiMain.list;

public class MainPageController implements Initializable {
    public String currentBudget = Double.toString(list.maxBudget);

    @FXML
    public Button addButton;
    public Button removeButton;
    public Button saveButton;
    public Button saveQuitButton;
    public TableView<Item> displayTable;
    public TableColumn<Item, String> itemDescList;
    public TableColumn<Item, Double> priceList;
    public TableColumn<Item, String> categoryList;
    public Label warningText;
    public AnchorPane startingPane;

    public ObservableList<Item> listTable = FXCollections.observableArrayList(GuiMain.list.getList());
    public TextField budget;
    public Button setBudgetButton;

    @FXML
    private Label statusLabel;
    @FXML
    private Label currentBudgetLabel;

    @FXML
    public Label updatedValue;

    // EFFECTS: initializes the updatedValue label and the Warning text
    private void init() {
        updatedValue.setText(currentBudget);
        setWarning();
    }


    // EFFECTS: loads the itemName, price, category into the table
    // source of inspiration: https://www.youtube.com/watch?v=4RNhPZJ84P0
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
        itemDescList.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
        priceList.setCellValueFactory(new PropertyValueFactory<Item, Double>("Price"));
        categoryList.setCellValueFactory(new PropertyValueFactory<Item, String>("Tag"));
        displayTable.setItems(listTable);
    }

    // EFFECTS: deletes an item when the user selects it
    public void deleteItem(javafx.event.ActionEvent actionEvent) {
        Item selectedItem = displayTable.getSelectionModel().getSelectedItem();
        list.removePurchase(selectedItem);
        displayTable.getItems().remove(selectedItem);
        printItemList(list);
        setWarning();

    }

    // EFFECTS: changes the budget to what the user wants
    public void setBudget(ActionEvent actionEvent) {

        try {
            String budgetS = budget.getText();
            double budgetNew = Double.parseDouble(budgetS);
            list.setMaxBudget(budgetNew);
            System.out.println(GuiMain.list.maxBudget);
            updatedValue.setText(budgetS);
            setWarning();
        } catch (LessThanZeroE exception) {
            catchLessThanZeroE();
        } catch (NumberFormatException exception) {
            catchNumberFormatE();
        }

    }

    // EFFECT: display an alert message when you have an empty string in the price field, or a invalid character
    private void catchNumberFormatE() {
        handleExceptionAlert("UH OH", "Please correct invalid price field!",
                "Input a valid number! Don't leave it empty & don't input a symbol/character!");
    }

    // EFFECT: display an alert message based on strings inputted
    private void handleExceptionAlert(String s, String s2, String s3) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(s);
        alert.setHeaderText(s2);
        alert.setContentText(s3);
        alert.show();
    }

    // EFFECT: display an alert message when you input a budget less than 0
    private void catchLessThanZeroE() {
        handleExceptionAlert("Error!", "Your input is invalid!!",
                "Please put a budget greater than or equal to 0!");
    }

    // EFFECTS: modifies the warning text when the budget is changed
    public void setBudgetAndRespone(ActionEvent event1) {
        setBudget(event1);
        setWarningText(event1);
    }

    // MODIFIES : list
    // EFFECTS: saves the list
    public void saveList(ActionEvent actionEvent) {
        try {
            Writer writer = new Writer(new File(LIST_FILE));
            writer.write(GuiMain.list);
            writer.close();
            System.out.println("Accounts saved to file " + LIST_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to Save");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // this is due to a programming error
        }
    }


    // EFFECTS: prints list for debugging purposes
    public void printItemList(ItemManager x) {
        List<Item> dummy = x.getList();
        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("%10s %10s %10s %10s %15s", "NUMBER", "ITEM NAME", "PRICE", "CATEGORY", "DATE");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------");
        for (Item i: dummy) {
            String f = "%10s %10s %10s %10s %15s";
            int position = dummy.indexOf(i) + 1;
            System.out.format(f, position, i.getName(), i.getPrice(), i.getTag(), i.getDateOfExpense());
            System.out.println();
        }
    }

    // EFFECTS: brings user to the scene where you can add an item to the list
    public void goToAdd(MouseEvent mouseEvent) throws IOException {
        System.out.println("PRESSED NEW LIST");
        Pane nextPane = null;
        nextPane = (Pane) FXMLLoader.load(getClass().getResource("AddExpense.fxml"));
        startingPane.getChildren().clear();
        startingPane.getChildren().add(nextPane);
    }


    // EFFECTS: saves the  list and quits the program
    public void saveListAndQuit(ActionEvent actionEvent) {
        saveList(actionEvent);
        // get a handle to the stage
        Stage stage = (Stage) saveQuitButton.getScene().getWindow();
        // do what you have to do
        stage.close();
        System.exit(0);
    }

    // EFFECTS: does setWarning when button is clicked
    public void setWarningText(ActionEvent event) {
        setWarning();
    }

    // EFFECTS: If the person exceeded their budget, warn them and tell them how much they exceeded by.
    //          If the person is within their budget range, tell them and inform how much money is left to spend.
    public void setWarning() {
        double exceedBudget = list.exceededBudgetAmount();
        String exceedBudgetText = String.format("%.2f", exceedBudget);
        Double valueExceeded1 = (-(exceedBudget));
        String valueExceeded = String.format("%.2f", valueExceeded1);

        if (list.exceedBudget()) {
            warningText.setText("OH NO! You exceeded your budget by $" + valueExceeded);
        }

        if (!list.exceedBudget()) {
            warningText.setText("You're still within budget!" + "You have $" + exceedBudgetText + " left to spend!");
        }
    }
}
