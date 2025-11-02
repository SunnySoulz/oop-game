package org.gooseapple.game;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.gooseapple.core.event.EventHandler;
import org.gooseapple.core.event.events.MouseEvent;
import org.gooseapple.core.event.events.RenderEvent;
import org.gooseapple.core.event.events.TickEvent;
import org.gooseapple.core.math.Vector2;
import org.gooseapple.core.render.Rectangle;
import org.gooseapple.level.Level;

public class Game extends Level {
    private GridPane window;
    private Canvas gameCanvas;
    private Scene scene;
    private GraphicsContext graphicsContext;
    private Rectangle rect;

    private Vector2 screenSize = new Vector2(500,400);

    public Game() {
        this.window = new GridPane(screenSize.getX(),screenSize.getY());
        this.gameCanvas = new Canvas(screenSize.getX(),screenSize.getY());
        this.graphicsContext = this.gameCanvas.getGraphicsContext2D();

        this.window.getChildren().add(this.gameCanvas);

        this.scene = new Scene(this.window,screenSize.getX(),screenSize.getY());

        this.setEnabled(true);

        this.rect = new Rectangle(new Vector2(25,25), new Vector2(300,300));
        this.rect.setVelocity(new Vector2(3,2));
    }

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public Scene getScene() {
        return this.scene;
    }

    @EventHandler
    public void HandleMouseClick(MouseEvent event) {
        System.out.println(event.getMousePosition() + " " + event.getClickType().toString());
    }


    public Vector2 getScreenSize() {
        return this.screenSize;
    }
}
