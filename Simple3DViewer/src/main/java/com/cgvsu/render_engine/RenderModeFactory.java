package com.cgvsu.render_engine;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RenderModeFactory {
    public static RenderMode grid() {
        return new RenderMode(true, null, null, false);
    }

    public static RenderMode texture(BufferedImage image) {
        return new RenderMode(false, null, image, false);
    }

}