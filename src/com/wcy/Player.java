package com.wcy;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends ImageView {
    float speed;
    int lifes;
    String shipColor;
    boolean available;
    boolean isAlive;

    public Player(float speed, int lifes, String shipColor) {
        super(new Image("/com/wcy/resources/playership_" + shipColor.toLowerCase() + ".png"));
        this.setTranslateX(1000 / 2 - 75 / 2);
        this.setTranslateY(1000 / 2 - 99 / 2 + 300);
        this.speed = speed;
        this.lifes = lifes;
        this.isAlive = true;
        this.available = true;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getLifes() {
        return lifes;
    }

    public void setLifes(int lifes) {
        this.lifes = lifes;
    }

    public String getShipColor() {
        return shipColor;
    }

    public void setShipColor(String shipColor) {
        this.shipColor = shipColor;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
