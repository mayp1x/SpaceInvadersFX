package com.wcy;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends ImageView {
    float speed;
    String shipColor;
    int lifes;
    int width;
    int height;
    boolean isAlive;

    public Player(float speed, int lifes, String shipColor){
        super(new Image("/com/wcy/resources/playership_" + shipColor.toLowerCase() + ".png", 55, 105, false, true));
        this.width = 55;
        this.height = 105;
        this.setTranslateY(600);
        this.setTranslateX(400 - width / 2);
        this.speed = speed;
        this.lifes = lifes;
        this.isAlive = true;
    }

    public void moveRight() {
        if (this.getTranslateX() + speed <= 800 - width) this.setTranslateX(this.getTranslateX() + speed);
    }

    public void moveLeft() {
        if (this.getTranslateX() - speed >= 0) this.setTranslateX(this.getTranslateX() - speed);
    }


}
