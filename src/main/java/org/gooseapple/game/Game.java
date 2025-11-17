package org.gooseapple.game;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import org.gooseapple.core.event.EventHandler;
import org.gooseapple.core.event.events.KeyboardEvent;
import org.gooseapple.core.event.events.MouseEvent;
import org.gooseapple.core.math.Vector2;
import org.gooseapple.core.render.Rectangle;
import org.gooseapple.core.render.Texture;
import org.gooseapple.core.sound.Sound;
import org.gooseapple.game.event.DestroyBulletEvent;
import org.gooseapple.game.objects.Bullet;
import org.gooseapple.game.objects.FlakBurst;
import org.gooseapple.game.objects.train.Carriage;
import org.gooseapple.game.objects.train.Locomotive;
import org.gooseapple.game.ui.background.BackgroundType;
import org.gooseapple.game.ui.background.Parallax;
import org.gooseapple.level.Level;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.Random;

public class Game extends Level {
    private GridPane window;
    private Canvas gameCanvas;
    private Scene scene;
    private GraphicsContext graphicsContext;
    private Sound drivingSound;
    private Sound flakSound;
    private Sound flakBurst;

    private ArrayList<Bullet> bullets = new ArrayList<>();

    private Vector2 screenSize = new Vector2(1300,400);

    private Locomotive locomotive;
    private Parallax parallax;

    private double speed = 1;

    public Game() {

        /**
         * TODO: Add background, maybe parallax for that 2d/3d aesthetic?
         * It may be cool to have multiple types of backgrounds, ie, desert, forest etc, but itll depend on how much time we have
         */

        this.window = new GridPane(screenSize.getX(),screenSize.getY());
        this.gameCanvas = new Canvas(screenSize.getX(),screenSize.getY());
        this.graphicsContext = this.gameCanvas.getGraphicsContext2D();

        this.window.getChildren().add(this.gameCanvas);

        this.scene = new Scene(this.window,screenSize.getX(),screenSize.getY());

        this.setEnabled(true);

        this.locomotive = new Locomotive( new Vector2(screenSize.getX() - 300, screenSize.getY() - 40), "textures/train1.png");
        this.locomotive.addCarriageToEnd(new Carriage(new Vector2(0,0), "textures/train_car.png"));
        this.locomotive.addCarriageToEnd(new Carriage(new Vector2(0,0), "textures/train_car.png"));
        this.locomotive.addCarriageToEnd(new Carriage(new Vector2(0,0), "textures/train_car.png"));
        this.locomotive.addCarriageToEnd(new Carriage(new Vector2(0,0), "textures/train_car_tank.png"));
        this.locomotive.addCarriageToEnd(new Carriage(new Vector2(0,0), "textures/train_car.png"));


        this.drivingSound = new Sound("/sound/train_drive.mp3");
        this.drivingSound.setVolume(0.025);
        this.drivingSound.setLoop(true);
        this.drivingSound.play();

        this.flakSound = new Sound("/sound/flak_fire.mp3");
        this.flakSound.setVolume(0.25);

        this.flakBurst = new Sound("/sound/flak_burst.mp3");
        this.flakBurst.setVolume(0.05);

        this.parallax = new Parallax(BackgroundType.PLAINS, screenSize);
    }

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public Scene getScene() {
        return this.scene;
    }

    @EventHandler
    public void HandleMouseClick(MouseEvent event) {
        if (event.getClickType() == MouseEvent.MouseClickType.LEFT) {
            this.flakSound.play();

            Bullet bullet = new Bullet(new Vector2(this.locomotive.getPosition().getX() + 20, this.locomotive.getPosition().getY()+9));
            Bullet bullet2 = new Bullet(new Vector2(this.locomotive.getPosition().getX() + 70, this.locomotive.getPosition().getY()+9));


            Vector2 direction = event.getMousePosition().subtract(this.locomotive.getPosition()).normalize();

            Random random = new Random();

            bullet.setVelocity(direction.multiply(random.nextDouble(5,5.5)));
            bullet2.setVelocity(direction.multiply(random.nextDouble(5,5.5)));
            bullets.add(bullet);
            bullets.add(bullet2);
        }
    }

    @EventHandler
    public void HandleBulletDestroyEvent(DestroyBulletEvent event) {
        flakBurst.play();
        new FlakBurst(event.getBullet().getPosition());
        bullets.remove(event.getBullet());
    }

    @EventHandler
    public void HandleKeyboardPress(KeyboardEvent event) {
        if (event.keyCode(KeyCode.W)) {
            this.speed += 0.125;
            this.parallax.setSpeed(this.speed);
        } else if (event.keyCode(KeyCode.S)) {
            this.speed -= 0.125;
            this.parallax.setSpeed(this.speed);
        }
    }


    public Vector2 getScreenSize() {
        return this.screenSize;
    }
}
