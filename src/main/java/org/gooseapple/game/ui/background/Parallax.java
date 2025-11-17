package org.gooseapple.game.ui.background;

import org.gooseapple.core.event.EventHandler;
import org.gooseapple.core.event.EventListener;
import org.gooseapple.core.event.EventManager;
import org.gooseapple.core.event.ListenerPriority;
import org.gooseapple.core.event.events.RenderEvent;
import org.gooseapple.core.math.Vector2;
import org.gooseapple.core.render.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Parallax implements EventListener {
    private ArrayList<Rectangle> backgrounds;
    private ArrayList<RailroadTile> railroad;
    private double horizontalShift = 1;
    private double speed = 0;
    private BackgroundType backgroundType;
    private Vector2 screenSize;
    private Random random = new Random();

    public Parallax(BackgroundType backgroundType, Vector2 screenSize) {
        this.backgrounds = new ArrayList<>();
        this.railroad = new ArrayList<>();
        EventManager.getInstance().addListener(this);
        this.backgroundType = backgroundType;
        this.screenSize = screenSize.clone();
        loadBackgrounds(backgroundType, screenSize);
        initRailroad();
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

    //These may get moved to their own classes later on, but for now this is what I have
    private void initRailroad() {
        RailroadTile first = new RailroadTile(RailroadType.RAILROAD, new Vector2(0, screenSize.getY() - 40));
        railroad.add(first);
        while (getRailroadEndX() < screenSize.getX()) {
            spawnNextRailroad();
        }
    }

    private double getRailroadEndX() {
        RailroadTile last = railroad.getLast();
        return last.getPosition().getX() + last.getSize().getX();
    }

    private void spawnNextRailroad() {
        RailroadTile last = railroad.getLast();
        ArrayList<RailroadType> valid = last.getNextValidTypes();
        RailroadType next = valid.get(random.nextInt(valid.size()));
        RailroadTile tile = new RailroadTile(next, new Vector2(getRailroadEndX(), screenSize.getY() - 40));
        //TODO: the screenSize.getY will end up being replaced, I plan on having two separate screensizes, one for the controls portion, and the other
        //for the visible game portion
        railroad.add(tile);
    }

    private void updateRailroad(double shift) {
        for (int i = 0; i < railroad.size(); i++) {
            RailroadTile t = railroad.get(i);
            t.getPosition().setX(t.getPosition().getX() - shift);
        }
        RailroadTile first = railroad.getFirst();
        if (first.getPosition().getX() + first.getSize().getX() < 0) {
            railroad.removeFirst();
        }
        while (getRailroadEndX() < screenSize.getX()) {
            spawnNextRailroad();
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

        updateRailroad(speed);
        for (RailroadTile t : railroad) {
            gc.drawImage(t.getTexture().getImage(), t.getPosition().getX(), t.getPosition().getY(), t.getSize().getX(), t.getSize().getY());
        }
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}