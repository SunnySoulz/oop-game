package org.gooseapple.core.render;

import javafx.scene.image.Image;

public class Texture {

    private final Image image;
    public Texture(String source) {
        this.image = new Image(source);
    }

    public Image getImage() {
        return image;
    }
}
