package com.wcy;

import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.*;

public class SpaceInvadersMenuSubscene extends SubScene {

    private final String FONT_PATH = "src/com/wcy/resources/trench.ttf";
    private final String BACKGROUND_PATH = "/com/wcy/resources/blue_panel.png";
    public static final int PANEL_WIDTH = 500;
    public static final int PANEL_HEIGHT = 500;


    public SpaceInvadersMenuSubscene(String name) {
        super(new AnchorPane(), PANEL_WIDTH, PANEL_HEIGHT);
        prefWidth(PANEL_WIDTH);
        prefHeight(PANEL_HEIGHT);
        Image background = new Image(BACKGROUND_PATH, PANEL_HEIGHT, PANEL_WIDTH, false, true);
        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null)));
        setLayoutY(1000 / 2 - PANEL_HEIGHT / 2 + 100);
        setLayoutX(1000 / 2 - PANEL_WIDTH / 2);
        switch (name) {
            case "GUESTBOOK":
                try {
                    createScoreboardScene();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "HELP":
                createHelpScene();
                break;
            case "SETTINGS":
                createSettingsScene();
                break;
        }
    }

    private void createScoreboardScene() throws IOException {
        Text text = new Text();
        String t = "GUESTBOOK";
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("guestbook.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(fileReader);


        try {
            text.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 40));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        text.setText(t);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setLayoutX(50);
        text.setLayoutY(80);
        getPane().getChildren().add(text);
        int i = 1;
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            Text score = new Text();
            try {
                score.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            t = i + ". " + line;
            score.setText(t);
            score.setLayoutX(50);
            score.setLayoutY(100 + 50 * i);
            getPane().getChildren().add(score);
            i++;
            if(i==8){
                break;
            }
        }
        bufferedReader.close();
    }

    private void createHelpScene() {
        Text text = new Text();
        String t = "HELP? NOBODY CAN HELP WAT STUDENTS...\n\nCREDITS: MARCIN PIOTROWSKI\nI7Y5S1";
        try {
            text.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        text.setText(t);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setLayoutX(40);
        text.setLayoutY(200);
        getPane().getChildren().add(text);
    }

    private void createSettingsScene() {
    }

    public AnchorPane getPane() {
        return (AnchorPane) this.getRoot();
    }
}
