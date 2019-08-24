package com.maxzuo.javafx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * JavaFX 初探 之 登录demo
 * Created by zfh on 2018-12-23
 */
public class JavaFxLogin extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 1.创建布局
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        // 2.新建场景
        Scene scene = new Scene(gridPane, 300, 270);
        primaryStage.setScene(scene);

        // 3.添加标题
        Text sceneTitle = new Text("Welcome");
        sceneTitle.setFont(Font.font("Tahoma"));
        gridPane.add(sceneTitle, 0, 0, 2, 1);

        // 4.添加标签及文本框
        Label userName = new Label("用户名：");
        gridPane.add(userName, 0, 1);

        TextField userTextField = new TextField();
        gridPane.add(userTextField, 1, 1);

        // 5.添加标签及密码框
        Label passWd = new Label("密码：");
        gridPane.add(passWd, 0, 2);

        PasswordField passwordField = new PasswordField();
        gridPane.add(passwordField, 1, 2);

        // 6.点击提交按钮
        Button button = new Button("登录");
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.getChildren().add(button);
        gridPane.add(hBox, 1, 4);

        // 7.提示文本提示
        final Text actionTarget = new Text();
        gridPane.add(actionTarget, 1, 6);

        button.setOnAction(event -> {
            actionTarget.setFill(Color.FIREBRICK);
            actionTarget.setText("已经登录");
        });

        primaryStage.setTitle("JavaFX Welcome");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
