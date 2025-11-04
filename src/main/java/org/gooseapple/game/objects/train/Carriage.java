package org.gooseapple.game.objects.train;

import org.gooseapple.core.event.EventHandler;
import org.gooseapple.core.event.events.RenderEvent;
import org.gooseapple.core.math.Vector2;
import org.gooseapple.core.render.Rectangle;
import org.gooseapple.core.render.Texture;

public class Carriage extends Rectangle {
    private Carriage previousCarriage;
    private Carriage nextCarriage;
    public Carriage(Vector2 position, String texture) {
        super(new Vector2(90,40), position);
        this.setTexture(new Texture(texture));
    }

    public Carriage getPreviousCarriage() {
        return previousCarriage;
    }

    public void addCarriageToEnd(Carriage carriage) {
        carriage.setPosition(this.getPosition().clone());
        if (this.previousCarriage != null) {
            this.previousCarriage.addCarriageToEnd(carriage);
        } else {
            setPreviousCarriage(carriage);
        }
    }

    public void setPreviousCarriage(Carriage previousCarriage) {
        this.previousCarriage = previousCarriage;
        this.previousCarriage.setNextCarriage(this);
    }

    public Carriage getNextCarriage() {
        return nextCarriage;
    }

    public void setNextCarriage(Carriage nextCarriage) {
        this.nextCarriage = nextCarriage;
        this.setPosition(new Vector2(this.getPosition().getX() - 90, this.getPosition().getY()));
        //Set the position of any previous carriages as well as this one
        if (this.previousCarriage != null) {
            this.previousCarriage.setNextCarriage(this);
        }
    }

    @EventHandler
    @Override
    public void render(RenderEvent event) {
        super.render(event);
    }
}
