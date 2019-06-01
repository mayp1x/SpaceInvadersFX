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

import java.awt.*;
import java.util.ArrayList;

public class GameViewManager {
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    private Stage menuStage;
    public Player playerShip;
    private AnimationTimer gameTimer;
    private AnimationTimer animationTimer;
    private int enemiesToSpawn;
    private int time;
    private String choosenShip;
    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private boolean available;

    ArrayList<Enemy> enemyList;
    ArrayList<Point> positionList;


    //99 75
    public static final int GAME_WIDTH = 1000;
    public static final int GAME_HEIGHT = 1000;


    public GameViewManager(){
        positionList = new ArrayList<>();
        enemyList = new ArrayList<>();
        enemiesToSpawn=5;
        time=0;
        available=true;

        initializeStage();
        createBackground();
        keyListeners();
        generateRoute();
        generateEnemies();
    }

    public int getEnemiesToSpawn() {
        return enemiesToSpawn;
    }

    public void setEnemiesToSpawn(int enemiesToSpawn) {
        this.enemiesToSpawn = enemiesToSpawn;
    }

    public ArrayList<Enemy> getEnemyList() {
        return enemyList;
    }

    public void setEnemyList(ArrayList<Enemy> enemyList) {
        this.enemyList = enemyList;
    }

    public void setPositionList(ArrayList<Point> positionList) {
        this.positionList = positionList;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
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
                else if(event.getCode()==KeyCode.SPACE){
                    shoot();
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

    public void createNewGame(Stage menuStage, String choosenShip){
        this.menuStage = menuStage;
        this.menuStage.hide();
        this.choosenShip = choosenShip;
        gameStage.show();
        createPlayer(choosenShip);
        createGameLoop();


    }

    public void generateRoute(){
        //positionList.add(new Point(675, 100));
        positionList.add(new Point(100, 100));
        positionList.add(new Point(100, 250));
        positionList.add(new Point(674, 250));
    }

    public void generateEnemies() {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                generateEnemy();
                deleteEnemy();
            }
        };
        animationTimer.start();
    }



    private void generateEnemy(){
        time++;
        if(time>50) {
            Enemy enemy = new Enemy(2, 3, 674, 100, this, true);
            gamePane.getChildren().add(enemy.getImageView());
            enemyList.add(enemy);
            Bullet bullet = new Bullet(playerShip,new Point((int) enemy.getImageView().getTranslateX(), (int) enemy.getImageView().getTranslateY()), this);
            gamePane.getChildren().add(bullet.bullet);
            bullet.start();
            time=0;
            enemiesToSpawn--;
            if(enemiesToSpawn<=0){
                animationTimer.stop();
            }
        }
    }

    private void deleteEnemy(){
        time++;
        if(time>50) {
            for (Enemy enemy : enemyList) {
                if (enemy.isAlive == false) {
                    gamePane.getChildren().remove(enemy);
                }
            }
            time=0;
        }
    }

    public void shoot(){
        PlayerBullet bullet = new PlayerBullet(this, new Point((int) playerShip.getTranslateX(), (int) playerShip.getTranslateY()));
        gamePane.getChildren().add(bullet.bullet);
        bullet.start();
    }

    private void createPlayer(String shipColor){
        playerShip = new Player(8,3,shipColor);
        gamePane.getChildren().add(playerShip);
    }

    public void moveRight() {
        if (playerShip.getTranslateX() + 7 <= 1000 - 100) playerShip.setTranslateX(playerShip.getTranslateX() + 7);
    }

    public void moveLeft() {
        if (playerShip.getTranslateX() - 7 >= 0) playerShip.setTranslateX(playerShip.getTranslateX() - 7);
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

    public ArrayList<Point> getPositionList() {
        return positionList;
    }

    public AnchorPane getPane(){
        return (AnchorPane) this.gamePane;
    }
}
