package com.wcy;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Modifier;

public class SpaceRunnerButton extends Button {

    private final String FONT_PATH = "src/com/wcy/resources/trench.ttf";
    private final String BUTTON_PR_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/com/wcy/resources/blue_button_pr.png')";
    private final String BUTTON_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/com/wcy/resources/blue_button.png')";
    public static final int BUTTON_WIDTH = 190;
    public static final int BUTTON_HEIGHT = 49;


    public SpaceRunnerButton(String text){
        setText(text);
        setButtonFont();
        setPrefWidth(BUTTON_WIDTH);
        setPrefHeight(BUTTON_HEIGHT);
        setStyle(BUTTON_STYLE);
        initializeButtonListeners();
    }

    private void setButtonFont(){
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH),23));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Arial",23));
        }
    }

    private void setButtonPressedStyle(){
        setStyle(BUTTON_PR_STYLE);
        setPrefHeight(45);
        setLayoutY(getLayoutY()+4);
    }

    private void setButtonReleasedStyle(){
        setStyle(BUTTON_STYLE);
        setPrefHeight(49);
        setLayoutY(getLayoutY()-4);
    }

    private void initializeButtonListeners(){

        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setButtonPressedStyle();
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setButtonReleasedStyle();
            }
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(new DropShadow());
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(null);
            }
        });
    }

}
