package com.cgvsu.render_engine;


import javafx.scene.image.Image;

import java.awt.*;

public class RenderMode {
    public final boolean grid;
    public final Color color;

    public final Image texture;
    public final boolean light;

    RenderMode(boolean grid, Color color, Image texture, boolean light) {
        this.grid = grid;
        this.color = color;
        this.texture = texture;
        this.light = light;
    }

    public Color getColor() {
        return color;
    }

    public Image getTexture() {
        return texture;
    }
}
