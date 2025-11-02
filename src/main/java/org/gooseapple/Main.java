package org.gooseapple;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.gooseapple.core.dispatcher.LoopDispatcher;
import org.gooseapple.core.dispatcher.MouseDispatcher;
import org.gooseapple.game.Game;

public class Main extends Application {
    private LoopDispatcher loopDispatcher;
    private MouseDispatcher mouseDispatcher;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //All of the calculations is held on this game object
        Game game = new Game();

        primaryStage.setScene(game.getScene());

        //Physics and UI thread listening happen here
        loopDispatcher = new LoopDispatcher(game);
        mouseDispatcher = new MouseDispatcher(game);

        primaryStage.show();
    }
}