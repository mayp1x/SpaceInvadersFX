package com.wcy;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ViewManager manager = new ViewManager();
        primaryStage = manager.getMainStage();
        primaryStage.show();
    }

    public static void main(String[] args) {
	    launch(args);
    }
}
