package com.wcy;


import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.ArrayList;

public class PlayerBullet extends Thread {
    ImageView bullet;
    AnimationTimer timer;
    GameViewManager gm;


    public PlayerBullet(GameViewManager gm, Point point) {
        this.gm = gm;
        bullet = new ImageView(new Image("/com/wcy/resources/laser.png"));
        bullet.setTranslateX(point.x + 18);
        bullet.setTranslateY(point.y);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                bullet.setTranslateY(bullet.getTranslateY() - 5);
            }
        };
        timer.start();
    }

    @Override
    public void run() {
        boolean isAlive=true;
        while(isAlive){
            synchronized (gm){
                while(gm.isAvailable()!=true){
                    try {
                        gm.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                gm.setAvailable(false);

                for(Enemy enemy: gm.getEnemyList()){
                    if (bullet.getBoundsInParent().intersects(enemy.getImageView().getBoundsInParent())){
                        Platform.runLater(()->{
                            gm.getPane().getChildren().remove(bullet);
                            gm.enemyList.remove(enemy);
                            System.out.println(gm.enemyList);
                            gm.getPane().getChildren().remove(enemy);
                        });
                        isAlive=false;
                        enemy.isAlive=false;
                        timer.stop();
                    }
                }

                gm.setAvailable(true);
                gm.notifyAll();
            }
            if(this.bullet.getTranslateY()<(-50)){
                isAlive=false;
                timer.stop();

            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
