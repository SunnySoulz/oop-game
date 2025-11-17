package org.gooseapple.core.event.events;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;

public class KeyboardEvent extends Event {
    private ArrayList<KeyCode> pressedKeys = new ArrayList<>();

    public KeyboardEvent(ArrayList<KeyCode> pressedKeys) {
        this.pressedKeys = pressedKeys;
    }

    public ArrayList<KeyCode> getPressedKeys() {
        return pressedKeys;
    }

    public boolean keyCode(KeyCode keyCode) {
        return pressedKeys.contains(keyCode);
    }
}
