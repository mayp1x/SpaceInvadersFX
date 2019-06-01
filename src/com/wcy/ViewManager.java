package com.wcy;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ViewManager {

    private final String FONT_PATH = "src/com/wcy/resources/trench.ttf";
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;
    private static final int BUTTON_Y_CENTRE = HEIGHT/2 - SpaceRunnerButton.BUTTON_HEIGHT/2;
    private static final int BUTTON_X_CENTRE = WIDTH/2 - SpaceRunnerButton.BUTTON_WIDTH/2;
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;
    ArrayList<SpaceRunnerButton> menuButtons;

    public ViewManager() {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        menuButtons = new ArrayList<>();
        createButtons();
        createBackground();
        createTitle();
        createFlyingShip(150, -1, 6, "blue");
        createFlyingShip(200, 1, 8, "orange");
        createFlyingShip(280, -1, 5, "green");
        createFlyingShip(320, -1, 4, "orange");
        createFlyingShip(380, 1, 7, "blue");


    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void addMenuButton(SpaceRunnerButton button){
        button.setLayoutX(BUTTON_X_CENTRE);
        button.setLayoutY(BUTTON_Y_CENTRE + menuButtons.size() * 100);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }

    private void createButtons() {
        createStartButton();
        createScoreButton();
        createHelpButton();
        createSettingsButton();
        createExitButton();
    }

    private void createStartButton(){
        SpaceRunnerButton startButton = new SpaceRunnerButton("START");
        addMenuButton(startButton);
    }

    private void createScoreButton(){
        SpaceRunnerButton scoreButton = new SpaceRunnerButton("SCOREBOARD");
        addMenuButton(scoreButton);
    }

    private void createHelpButton(){
        SpaceRunnerButton helpButton = new SpaceRunnerButton("HELP");
        addMenuButton(helpButton);
    }

    private void createSettingsButton(){
        SpaceRunnerButton settingsButton = new SpaceRunnerButton("SETTINGS");
        addMenuButton(settingsButton);
    }

    private void createExitButton(){
        SpaceRunnerButton exitButton = new SpaceRunnerButton("EXIT");
        addMenuButton(exitButton);
    }

    private void createFlyingShip(int startY, int whichWay, int speed, String color){
        //default ship is blue
        ImageView ship = new ImageView(new Image("/com/wcy/resources/playership_blue.png", 99, 75, false, true));

        switch (color){
            case "blue":
                ship = new ImageView(new Image("/com/wcy/resources/playership_blue.png", 99, 75, false, true));
                break;
            case "orange":
                ship = new ImageView(new Image("/com/wcy/resources/playership_orange.png", 99, 75, false, true));
                break;
            case "green":
                ship = new ImageView(new Image("/com/wcy/resources/playership_green.png", 99, 75, false, true));
                break;


        }
        TranslateTransition t = new TranslateTransition();
        t.setDuration(Duration.seconds(speed));
        t.setToY(startY);
        ship.setRotate(ship.getRotate()+(90*whichWay));

        if(whichWay<0){
            ship.setTranslateX(WIDTH+200);
            t.setToX(-200);
        }
        else{
            ship.setTranslateX(-200);
            t.setToX(WIDTH+200);
        }
        ship.setTranslateY(startY);
        mainPane.getChildren().add(ship);
        t.setNode(ship);
        t.setCycleCount(t.INDEFINITE);
        t.play();

    }


    private void createTitle(){
        Text text = new Text();
        String title = "WAT Invaders";
        text.setText(title);
        text.setFill(Color.WHITE);
        text.setStrokeWidth(1);
        text.setStroke(Color.BLUE);
        try {
            text.setFont(Font.loadFont(new FileInputStream(FONT_PATH),100));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        text.setX(210);
        text.setY(100);
        mainPane.getChildren().add(text);
    }

    private void createBackground(){
        Image background = new Image("/com/wcy/resources/space.gif", 787, 457, false, true);
        mainPane.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null)));

    }
}
