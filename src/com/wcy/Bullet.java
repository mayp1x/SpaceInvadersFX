package com.wcy;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;


public class Bullet extends Thread {
    Player player;
    ImageView bullet;
    GameViewManager gm;
    AnimationTimer timer;

    public Bullet(Player player, Point point, GameViewManager gm) {
        this.player = player;
        bullet = new ImageView(new Image("/com/wcy/resources/laser.png"));
        bullet.setTranslateX(point.x);
        bullet.setTranslateY(point.y);
        this.gm = gm;
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                bullet.setTranslateY(bullet.getTranslateY() + 5);
            }
        };
        timer.start();
    }

    @Override
    public void run() {
        boolean isAlive=true;
        while(isAlive){
            synchronized (player){
                while(player.available!=true){
                    try {
                        player.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                player.setAvailable(false);
                if(bullet.getBoundsInParent().intersects(player.getBoundsInParent())){
                    Platform.runLater(()->{
                        gm.getPane().getChildren().remove(bullet);
                    });
                    isAlive=false;
                    player.lifes--;
                    System.out.println(player.lifes);
                    if(player.lifes<0){
                        Platform.runLater(()->{
                            gm.getPane().getChildren().remove(player);
                            player.isAlive=false;
                        });
                    }
                    timer.stop();
                }
                player.setAvailable(true);
                player.notifyAll();
            }
            if(this.bullet.getTranslateY()>1000){
                isAlive=false;
                timer.stop();

            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
