package com.wcy;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GameViewManager {
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    private Stage menuStage;
    private ImageView ship;
    private AnimationTimer gameTimer;

    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;


    //99 75
    public static final int GAME_WIDTH = 1000;
    public static final int GAME_HEIGHT = 1000;


    public GameViewManager(){
        initializeStage();
        createBackground();
        keyListeners();
    }

    private void keyListeners(){
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode()== KeyCode.LEFT){
                    isLeftKeyPressed=true;
                }
                else if(event.getCode()==KeyCode.RIGHT){
                    isRightKeyPressed=true;
                }

            }
        });

        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode()==KeyCode.LEFT){
                    isLeftKeyPressed=false;
                }
                else if(event.getCode()==KeyCode.RIGHT){
                    isRightKeyPressed=false;
                }
            }
        });
    }

    private void initializeStage(){
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);

    }

    public void createNewGame(Stage menuStage, int choosenShip){
        this.menuStage = menuStage;
        this.menuStage.hide();
        gameStage.show();
        createPlayer("blue");
        createGameLoop();

    }

    private void createPlayer(String shipColor){
        ship = new ImageView(new Image("/com/wcy/resources/playership_" + shipColor.toLowerCase() + ".png"));
        ship.setLayoutX(GAME_WIDTH/2 - 75/2);
        ship.setLayoutY(GAME_HEIGHT/2 - 99/2 + 200);
        gamePane.getChildren().add(ship);
    }

    public void moveRight() {
        if (ship.getTranslateX() + 3 <= 1000 - 75) ship.setTranslateX(ship.getTranslateX() + 3);
    }

    public void moveLeft() {
        if (ship.getTranslateX() - 3 >= 0) ship.setTranslateX(ship.getTranslateX() - 3);
    }

    public void  moveShip(){
        if(isLeftKeyPressed && !isRightKeyPressed){
            moveLeft();
        }
        else if(isRightKeyPressed && !isLeftKeyPressed){
            moveRight();
        }
    }

    private void createBackground() {
        Image background = new Image("/com/wcy/resources/space.gif", 787, 457, false, true);
        gamePane.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null)));
    }

    private void createGameLoop(){
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveShip();
            }
        };
        gameTimer.start();

    }
}
