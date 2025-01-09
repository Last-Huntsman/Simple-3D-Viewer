package com.cgvsu.render_engine;


import javafx.scene.image.Image;

public class RenderModeFactory {
    public static RenderMode grid() {
        return new RenderMode(true, null, null, false);
    }

    public static RenderMode texture(Image image) {
        return new RenderMode(false, null, image, false);
    }

}