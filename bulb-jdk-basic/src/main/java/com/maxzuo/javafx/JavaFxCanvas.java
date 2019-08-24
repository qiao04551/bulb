package com.maxzuo.javafx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * JavaFX Canvas API
 * Created by zfh on 2018/12/23
 */
public class JavaFxCanvas extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Canvas test");
        Group root = new Group();
        Canvas canvas = new Canvas(500, 300);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        graphicsContext.setFill(Color.GRAY);
        graphicsContext.fillRect(10, 10, 30, 100);

        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
