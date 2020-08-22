package ui;

import exceptions.LessThanZeroE;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.ItemManager;

public class SetBudgetController {

    @FXML
    public AnchorPane startingPane;

    @FXML
    private Label prompt;

    @FXML
    private Button setBudgetOption;

    @FXML
    private TextField budgetInput;


    // EFFECTS: sets the User's budget and moves the user to the next page
    @FXML
    public void moveToExpensePage(MouseEvent event) {

        System.out.println("PRESSED NEW LIST");
        // getting the budget & making the list
        String budgetInputted = budgetInput.getText();
        Double budgetSet = Double.parseDouble(budgetInputted);
        try {
            GuiMain.list = new ItemManager(budgetSet);
            // update the value of budget on the MainPageController
            Stage stage = (Stage) setBudgetOption.getScene().getWindow();
            // do what you have to do
            stage.close();
        } catch (LessThanZeroE exception) {
            catchLessThanZeroE();
        } catch (NumberFormatException exception1) {
            catchNumberFormatE();
        }
    }

    // EFFECTS: displays alert when the NumberFormatException is caught
    private void catchNumberFormatE() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("UH OH");
        alert.setHeaderText("Please correct invalid price field!");
        alert.setContentText("Input a valid number! Don't leave it empty and don't input a symbol/character!");
        alert.show();
    }

    // EFFECTS: displays alert when the LessThanZeroException is caught
    private void catchLessThanZeroE() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("Your input is invalid!!");
        alert.setContentText("Please put a budget greater than or equal to 0!");
        alert.show();
    }
////
}
