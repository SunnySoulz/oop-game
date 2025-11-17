package org.gooseapple.game.ui.background;

import org.gooseapple.core.math.Vector2;
import org.gooseapple.core.render.Rectangle;
import org.gooseapple.core.render.Texture;

import java.util.ArrayList;
import java.util.Arrays;

public class RailroadTile extends Rectangle {
    private ArrayList<RailroadType> nextValidTypes;

    public RailroadTile(RailroadType type, Vector2 position) {

        super(new Vector2(120, 40), position, false, null);

        String texturePath = loadRailroadType(type);
        setTexture(new Texture(texturePath));


    }

    private String loadRailroadType(RailroadType type) {
        switch (type) {
            case RAILROAD:
                nextValidTypes = new ArrayList<>(Arrays.asList(RailroadType.RAILROAD, RailroadType.RAILROAD_WIRE_START));
                return "textures/parallax/railroad/railroad.png";
            case RAILROAD_WIRE_START:
                nextValidTypes = new ArrayList<>(Arrays.asList(RailroadType.RAILROAD_WIRE_END, RailroadType.RAILROAD_WIRE_MIDDLE));
                return "textures/parallax/railroad/railroad_wire_start.png";
            case RAILROAD_WIRE_MIDDLE:
                nextValidTypes = new ArrayList<>(Arrays.asList(RailroadType.RAILROAD_WIRE_MIDDLE, RailroadType.RAILROAD_WIRE_END));
                return "textures/parallax/railroad/railroad_wire_middle.png";
            case RAILROAD_WIRE_END:
                nextValidTypes = new ArrayList<>(Arrays.asList(RailroadType.RAILROAD));
                return "textures/parallax/railroad/railroad_wire_end.png";
        }
        return "textures/parallax/railroad/railroad.png";
    }

    public void setNextValidTypes(ArrayList<RailroadType> nextValidTypes) {
        this.nextValidTypes = nextValidTypes;
    }

    public ArrayList<RailroadType> getNextValidTypes() {
        return nextValidTypes;
    }
}
