package com.wcy;

import com.wcy.GameViewManager;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Enemy {
    float speed;
    int lifes;
    private ImageView imageView;
    private GameViewManager gameViewManager;
    private int LastPositionIndex = 0;
    ArrayList<Point> positionList;
    private int t;
    int randomNumber;
    boolean isAlive;
    AnimationTimer animationTimer;

    public Enemy(float speed, int lifes, int x, int y, GameViewManager gameViewManager, boolean isAlive) {
        imageView = new ImageView(new Image("/com/wcy/resources/enemy.png"));
        imageView.setTranslateX(x);
        imageView.setTranslateY(y);
        this.speed = speed;
        this.lifes = lifes;
        this.gameViewManager = gameViewManager;
        this.positionList = gameViewManager.getPositionList();
        this.t = 0;
        this.isAlive = isAlive;
        Random random = new Random();
        this.randomNumber = random.nextInt(20) + 50;
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updatePosition();
            }
        };
        animationTimer.start();
    }



    public AnimationTimer getAnimationTimer() {
        return animationTimer;
    }

    public void setAnimationTimer(AnimationTimer animationTimer) {
        this.animationTimer = animationTimer;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void moveRight() {
        imageView.setTranslateX(imageView.getTranslateX() + speed);
    }

    public void moveLeft() {
        imageView.setTranslateX(imageView.getTranslateX() - speed);
    }

    public void moveDown() {
        imageView.setTranslateY(imageView.getTranslateY() + speed);
    }

    public void moveUp() {
        imageView.setTranslateY(imageView.getTranslateY() + speed);
    }

    public void chooseWhichWay(Point point) {
        if (imageView.getTranslateX() == point.x) {
            if (imageView.getTranslateY() > point.y) {
                moveUp();
            } else {
                moveDown();
            }
        } else if (imageView.getTranslateY() == point.y) {
            if (imageView.getTranslateX() > point.x) {
                moveLeft();
            } else {
                moveRight();
            }
        }
    }

    public void updatePosition() {
        t++;
        chooseWhichWay(positionList.get(LastPositionIndex));
        if (positionList.get(LastPositionIndex).x == imageView.getTranslateX() && positionList.get(LastPositionIndex).y == imageView.getTranslateY()) {
            if (LastPositionIndex + 1 <= positionList.size() - 1) {
                LastPositionIndex++;
            }
        }
        if (t > randomNumber) {
            Bullet bullet = new Bullet(gameViewManager.playerShip, new Point((int) this.getImageView().getTranslateX(), (int) this.getImageView().getTranslateY()), gameViewManager);
            gameViewManager.getPane().getChildren().add(bullet.bullet);
            bullet.start();
            Random random = new Random();
            randomNumber = random.nextInt(40) + 50;
            t = 0;
        }
    }
}
