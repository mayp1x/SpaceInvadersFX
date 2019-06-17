package com.wcy;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GameViewManager {
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    private Stage menuStage;
    public Player playerShip;
    private AnimationTimer gameTimer;
    private AnimationTimer animationTimer;
    private AnimationTimer levelTwoTimer;
    private int enemiesToSpawn;
    private int time;
    private String choosenShip;
    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private boolean available;
    private boolean letsGoSecondStage;
    private boolean isSecondStage;
    private Text nextLevel;
    private Text lifeCounter;
    private boolean gameEnd;

    ArrayList<Enemy> enemyList;
    ArrayList<Point> positionList;


    //99 75
    public static final int GAME_WIDTH = 1000;
    public static final int GAME_HEIGHT = 1000;


    public GameViewManager() {
        lifeCounter = new Text();
        nextLevel = new Text();
        try {
            lifeCounter.setFont(Font.loadFont(new FileInputStream("src/com/wcy/resources/trench.ttf"), 64));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        lifeCounter.setFill(Color.WHITE);
        lifeCounter.setLayoutX(20);
        lifeCounter.setLayoutY(930);
        positionList = new ArrayList<>();
        enemyList = new ArrayList<>();
        letsGoSecondStage = false;
        enemiesToSpawn = 5;
        time = 0;
        gameEnd = false;
        available = true;
        isSecondStage = false;
        initializeStage();
        createBackground();
        keyListeners();

    }

    public Stage getMenuStage() {
        return menuStage;
    }

    public void setMenuStage(Stage menuStage) {
        this.menuStage = menuStage;
    }

    public AnimationTimer getGameTimer() {
        return gameTimer;
    }

    public void setGameTimer(AnimationTimer gameTimer) {
        this.gameTimer = gameTimer;
    }

    public Stage getGameStage() {
        return gameStage;
    }

    public void setGameStage(Stage gameStage) {
        this.gameStage = gameStage;
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

    private void keyListeners() {
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = true;
                } else if (event.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed = true;
                } else if (event.getCode() == KeyCode.SPACE) {
                    shoot();
                } else if (event.getCode() == KeyCode.ENTER) {
                    letsGoSecondStage = true;
                    System.out.println(letsGoSecondStage);
                    isSecondStage = false;
                    clearNextLevelText();

                }
            }
        });

        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = false;
                } else if (event.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed = false;
                }

            }
        });
    }


    private void initializeStage() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
        gameStage.initStyle(StageStyle.UNDECORATED);
    }

    public void createNewGame(Stage menuStage, String choosenShip) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        this.choosenShip = choosenShip;
        gameStage.show();
        createPlayer(choosenShip);
        createGameLoop();
        generateEnemiesStageOne();
        generateEnemiesStageTwo();
        generateRoute();

    }

    public void generateRoute() {
        //positionList.add(new Point(675, 100));
        positionList.add(new Point(100, 100));
        positionList.add(new Point(100, 250));
        positionList.add(new Point(674, 250));
    }

    public void generateEnemiesStageOne() {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                generateEnemy();

            }
        };
        animationTimer.start();
    }

    public void generateEnemiesStageTwo() {
        levelTwoTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (letsGoSecondStage) generateEnemySecondStage();
            }
        };
        levelTwoTimer.start();
    }

    private void generateEnemy() {
        time++;
        if (time > 100) {
            Enemy enemy = new Enemy(2, 3, 1200, 100, this, true);
            gamePane.getChildren().add(enemy.getImageView());
            enemyList.add(enemy);
            Bullet bullet = new Bullet(playerShip, new Point((int) enemy.getImageView().getTranslateX(), (int) enemy.getImageView().getTranslateY()), this);
            gamePane.getChildren().add(bullet.bullet);
            bullet.start();
            time = 0;
            enemiesToSpawn--;

            if (enemiesToSpawn <= 0) {
                animationTimer.stop();
                isSecondStage = true;
                setEnemiesToSpawn(10);
            }
        }
    }

    private void generateEnemySecondStage() {
        if (letsGoSecondStage) {
            time++;
            if (time > 50) {
                Enemy enemy = new Enemy(4, 3, 1200, 100, this, true);
                gamePane.getChildren().add(enemy.getImageView());
                enemyList.add(enemy);
                Bullet bullet = new Bullet(playerShip, new Point((int) enemy.getImageView().getTranslateX(), (int) enemy.getImageView().getTranslateY()), this);
                gamePane.getChildren().add(bullet.bullet);
                bullet.start();
                time = 0;
                enemiesToSpawn--;
                if (enemiesToSpawn <= 0) {
                    letsGoSecondStage = false;
                    levelTwoTimer.stop();
                    gameEnd = true;
                    //nextStage
                }
            }
        }
    }

    public void shoot() {
        PlayerBullet bullet = new PlayerBullet(this, new Point((int) playerShip.getTranslateX(), (int) playerShip.getTranslateY()));
        gamePane.getChildren().add(bullet.bullet);
        bullet.start();
    }


    private void damagePlayer() {
        for (Enemy enemy : enemyList) {
            if (enemy.getImageView().getTranslateY() > 1000) {
                Platform.runLater(() -> {
                    enemy.getImageView().setImage(null);
                    gamePane.getChildren().remove(enemy);
                    enemyList.remove(enemy);
                    playerShip.lifes--;
                    System.out.println(playerShip.lifes);
                    if (playerShip.lifes == 0) {
                        playerShip.setTranslateX(-100);
                        playerShip.setTranslateY(-100);
                        gameStage.close();
                        gameTimer.stop();
                        menuStage.show();
                    }
                });
            }
        }
    }

    private void createPlayer(String shipColor) {
        playerShip = new Player(8, 3, shipColor);
        gamePane.getChildren().add(playerShip);
    }

    public void moveRight() {
        if (playerShip.getTranslateX() + 7 <= 1000 - 100) playerShip.setTranslateX(playerShip.getTranslateX() + 7);
    }

    public void moveLeft() {
        if (playerShip.getTranslateX() - 7 >= 0) playerShip.setTranslateX(playerShip.getTranslateX() - 7);
    }

    public void moveShip() {
        if (isLeftKeyPressed && !isRightKeyPressed) {
            moveLeft();
        } else if (isRightKeyPressed && !isLeftKeyPressed) {
            moveRight();
        }
    }

    private void createBackground() {
        Image background = new Image("/com/wcy/resources/space.gif", 787, 457, false, true);
        gamePane.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null)));
    }

    private void drawNextLevelText() {
        isSecondStage = false;
        String t = "Press ENTER to continue...";
        try {
            nextLevel.setFont(Font.loadFont(new FileInputStream("src/com/wcy/resources/trench.ttf"), 64));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        nextLevel.setText(t);
        nextLevel.setFill(Color.WHITE);
        nextLevel.setTextAlignment(TextAlignment.CENTER);
        nextLevel.setLayoutX(180);
        nextLevel.setLayoutY(-100);

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(3));
        translateTransition.setToY(250);
        gamePane.getChildren().add(nextLevel);
        translateTransition.setNode(nextLevel);
        translateTransition.setCycleCount(1);
        translateTransition.play();
    }

    private void drawEndGameText() {
        gamePane.getChildren().remove(nextLevel);
        nextLevel = new Text();
        gameEnd = false;
        String t = "Congratulations! You Won!\nClick ENTER and check in the guest book :)";
        try {
            nextLevel.setFont(Font.loadFont(new FileInputStream("src/com/wcy/resources/trench.ttf"), 58));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        nextLevel.setText(t);
        nextLevel.setFill(Color.WHITE);
        nextLevel.setTextAlignment(TextAlignment.CENTER);
        nextLevel.setLayoutX(10);
        nextLevel.setLayoutY(-100);
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(3));
        translateTransition.setToY(250);
        gamePane.getChildren().add(nextLevel);
        translateTransition.setNode(nextLevel);
        translateTransition.setCycleCount(1);
        translateTransition.play();
    }

    private void clearNextLevelText() {
        gamePane.getChildren().remove(nextLevel);
    }

    private void drawLifeCounter() {
        if (gamePane.getChildren().contains(lifeCounter)) {
            gamePane.getChildren().remove(lifeCounter);
        }
        String lifes = Integer.toString(playerShip.getLifes());
        String text = "LIFES: " + lifes;
        lifeCounter.setText(text);
        gamePane.getChildren().add(lifeCounter);

    }

    private void updateScoreboard(String name) {
        try {
            FileWriter fw = new FileWriter("guestbook.txt", true);
            fw.write(name + "\n");
            fw.close();
            System.out.println("Thanks for playing the game!");
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }


    private void createGameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                drawLifeCounter();
                moveShip();
                damagePlayer();
                if (isSecondStage && enemyList.isEmpty()) {
                    drawNextLevelText();
                }
                if (gameEnd && enemyList.isEmpty()) {
                    drawEndGameText();
                    gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                            if (event.getCode() == KeyCode.ENTER) {
                                for (int i = 1; i < 20; i++) {
                                    System.out.println("*");

                                }
                                System.out.print("PLEASE ENTER YOUR NAME: ");
                                Scanner sc = new Scanner(System.in);
                                String name = sc.nextLine();
                                updateScoreboard(name);
                                System.exit(0);
                            }
                        }
                    });

                }
            }
        };
        gameTimer.start();

    }

    public ArrayList<Point> getPositionList() {
        return positionList;
    }

    public AnchorPane getPane() {
        return (AnchorPane) this.gamePane;
    }
}