package com.wcy;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GameViewManager {
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;

    private Stage menuStage;
    private ImageView ship;


    public static final int GAME_WIDTH = 1000;
    public static final int GAME_HEIGHT = 1000;


    public GameViewManager(){
        initializeStage();
        keyListeners();
    }

    private void keyListeners(){

    }

    private void initializeStage(){
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);

    }

    private void createNewGame(Stage menuStage, int choosenShip){
        this.menuStage = menuStage;
        this.menuStage.hide();
        gameStage.show();
    }
}
