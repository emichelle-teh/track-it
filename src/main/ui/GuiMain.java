package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ItemManager;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class GuiMain extends Application {


    String filePath2 = "./data/bygoneDays.wav";

    public static ItemManager list;
    public static final String LIST_FILE = "./data/list.txt";

    public static void main(String[] args) {
        Application.launch(args);
    }

    // EFFECTS: Display the Options Page when the application starts
    @Override
    public void start(Stage primaryStage) throws Exception {
        //FXMLLoader loader = new FXMLLoader();
        // loader.setLocation(getClass().getResource("../OptionsPage.fxml"));
        Parent mainPane = FXMLLoader.load(getClass().getResource("OptionsPage.fxml"));
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("TrackIt!");
        primaryStage.show();

        playMusic(filePath2);

    }

    // EFFECTS: Plays the music selected
    public static void playMusic(String filePath) {
        InputStream music;
        try {
            music = new FileInputStream(new File(filePath));
            AudioStream audio = new AudioStream(music);
            AudioPlayer.player.start(audio);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error");
        }

    }




}