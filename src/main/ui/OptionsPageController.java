package ui;

import exceptions.LessThanZeroE;
import exceptions.StringLengthZero;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Item;
import model.ItemManager;
import persistence.Reader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class OptionsPageController {

    @FXML
    public AnchorPane startingPane;
    public Button firstTimer;
    public Button loadButton;

    // MODIFIES: list
    // EFFECTS: Loads the stored list and moves the reader to the next page called "MainPage.fxml"
    @FXML
    public void loadList(MouseEvent event) {
        try {
            new Reader();
            List<Item> listBought = Reader.readItems(new File(GuiMain.LIST_FILE));
            double budgetAmount = Reader.readBudget(new File(GuiMain.LIST_FILE));
            try {
                GuiMain.list = new ItemManager(budgetAmount);
                GuiMain.list.makeList(listBought);
                goToPage("MainPage.fxml");
            } catch (LessThanZeroE e) {
                errorWhenBudgetLessThanZero();
            }
        } catch (IOException e) {
            try {
                GuiMain.list = new ItemManager(0.0);
            } catch (LessThanZeroE exception) {
                handleExceptionAlert("Your budget is less than 0...", "Put a budget greater than 0.");
            }
        } catch (LessThanZeroE e) {
            catchLessThanZeroErrorWhenReading();
        } catch (StringLengthZero stringLengthZero) {
            catchStringErrorWhenReading();
        }
        System.out.println("PRESSED LOAD LIST");
    }

    // EFFECT: display an error window and message (s1, s2) when an exception is thrown
    private void handleExceptionAlert(String s, String s2) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText(s);
        alert.setContentText(s2);
        alert.show();
    }

    // EFFECT: display error message when file is corrupted due to string length = 0
    private void catchStringErrorWhenReading() {
        handleExceptionAlert("Your file has some issues. One Item cannot be instantiated!", "One field has is empty!");
    }

    // EFFECT: display error message when file is corrupted due to price is less than 0.
    private void catchLessThanZeroErrorWhenReading() {
        handleExceptionAlert("Your file has some issues.", "One item has a price less than 0!");
    }

    // EFFECT: display error message when file is corrupted due to budget being less than 0.
    public void errorWhenBudgetLessThanZero() {
        handleExceptionAlert("Your budget is less than 0...", "File cannot be opened because budget is less than 0.");
    }

    // REQUIRES: Has an empty BoughtItemsList with budget 0.0
    // EFFECTS: Moves the stage to another page called SetBudget
    @FXML
    public void startNewEmpty(MouseEvent event) throws Exception {
        System.out.println("PRESSED NEW LIST");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SetBudget.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage currentStage = new Stage();
            currentStage.setTitle("SECOND PAGE");
            currentStage.setScene(new Scene(root1));
            currentStage.show();
        } catch (IOException e) {
            System.out.println("Can't load new window");
        }
        goToPage("AddExpense.fxml");
    }

    // MODIFIES: this startingPane
    // EFFECT: goes to the next page
    private void goToPage(String nameFxml) throws IOException {
        Pane nextPane = null;
        nextPane = (Pane) FXMLLoader.load(getClass().getResource(nameFxml));
        startingPane.getChildren().clear();
        startingPane.getChildren().add(nextPane);
    }
}
//