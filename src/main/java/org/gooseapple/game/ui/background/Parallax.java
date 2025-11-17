package org.gooseapple.game.ui.background;

import javafx.scene.layout.Background;
import org.gooseapple.core.event.EventHandler;
import org.gooseapple.core.event.EventListener;
import org.gooseapple.core.event.EventManager;
import org.gooseapple.core.event.ListenerPriority;
import org.gooseapple.core.event.events.RenderEvent;
import org.gooseapple.core.math.Vector2;
import org.gooseapple.core.render.Rectangle;

import java.util.ArrayList;

public class Parallax implements EventListener {
    private ArrayList<Rectangle> backgrounds;
    private double horizontalShift = 1;
    private double speed = 0;
    private BackgroundType backgroundType;

    //I should find a better way to do this, but for now thisll work


    public Parallax(BackgroundType backgroundType, Vector2 screenSize) {
        this.backgrounds = new ArrayList<>();
        EventManager.getInstance().addListener(this);
        this.backgroundType = backgroundType;

        loadBackgrounds(backgroundType, screenSize);
    }

    private void loadBackgrounds(BackgroundType backgroundType, Vector2 screenSize) {
        switch (backgroundType) {
            case PLAINS:
                ArrayList<Rectangle> plainBackgrounds = new ArrayList<>();
                plainBackgrounds.add(new Rectangle(screenSize.clone(), new Vector2(0,0), false, "textures/parallax/plains/1.png"));
                plainBackgrounds.add(new Rectangle(screenSize.clone(), new Vector2(0,0), false, "textures/parallax/plains/2.png"));
                plainBackgrounds.add(new Rectangle(screenSize.clone(), new Vector2(0,0), false, "textures/parallax/plains/3.png"));
                plainBackgrounds.add(new Rectangle(screenSize.clone(), new Vector2(0,0), false, "textures/parallax/plains/4.png"));
                setBackgrounds(plainBackgrounds);
                break;
            case DUSTBOWL:
                break;
            case MOUNTAINS:
                ArrayList<Rectangle> backgrounds = new ArrayList<>();
                backgrounds.add(new Rectangle(screenSize.clone(), new Vector2(0,0), false, "textures/parallax/mountains/sky.png"));
                backgrounds.add(new Rectangle(screenSize.clone(), new Vector2(0,0), false, "textures/parallax/mountains/clouds_bg.png"));
                backgrounds.add(new Rectangle(screenSize.clone(), new Vector2(0,0), false, "textures/parallax/mountains/cloud_lonely.png"));
                backgrounds.add(new Rectangle(screenSize.clone(), new Vector2(0,0), false, "textures/parallax/mountains/glacial_mountains.png"));
                backgrounds.add(new Rectangle(screenSize.clone(), new Vector2(0,0), false, "textures/parallax/mountains/clouds_mg_3.png"));
                backgrounds.add(new Rectangle(screenSize.clone(), new Vector2(0,0), false, "textures/parallax/mountains/clouds_mg_2.png"));
                backgrounds.add(new Rectangle(screenSize.clone(), new Vector2(0,0), false, "textures/parallax/mountains/clouds_mg_1.png"));
                setBackgrounds(backgrounds);
                break;
        }
    }

    public ArrayList<Rectangle> getBackgrounds() {
        return backgrounds;
    }

    public void setBackgrounds(ArrayList<Rectangle> backgrounds) {
        this.backgrounds = backgrounds;
    }

    @EventHandler(priority = ListenerPriority.HIGHEST)
    public void onRender(RenderEvent event) {
        var gc = event.getGraphicsContext();

        horizontalShift += 1 * speed;

        for (int i = 0; i < backgrounds.size(); i++) {

            var rect = backgrounds.get(i);
            double baseX = rect.getPosition().getX();
            double baseY = rect.getPosition().getY();
            double width = rect.getSize().getX();
            double height = rect.getSize().getY();

            double offset = -(horizontalShift * i) * (1/4d);

            double x = baseX - offset;

            x = ((x % width) + width) % width * -1;

            gc.drawImage(rect.getTexture().getImage(), x, baseY, width, height);
            gc.drawImage(rect.getTexture().getImage(), x + width, baseY, width, height);
        }
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}

