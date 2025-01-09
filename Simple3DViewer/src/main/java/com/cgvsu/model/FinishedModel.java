package com.cgvsu.model;

import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.render_engine.RenderMode;

public class FinishedModel {

    public Model model;

    private String name;
    private boolean isTargeted;

    private RenderMode renderMode;

    public String getName() {
        return name;
    }

    public FinishedModel(Model model, String name, RenderMode renderModeFactory, boolean isTargeted) {
        this.model = model;
        this.name = name;
        this.renderMode = renderModeFactory;
        this.isTargeted = false;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public boolean isTargeted() {
        return isTargeted;
    }

    public void setTargeted(boolean targeted) {
        this.isTargeted = targeted;
    }

    // Методы для управления трансформацией модели
    public void setPosition(Vector3f position) {
        model.position = position;
    }

    public void setScale(Vector3f scale) {
        model.scale = scale;
    }

    public void setRotation(Vector3f rotation) {
        model.rotation = rotation;
    }

    public Vector3f getPosition() {
        return model.position;
    }

    public Vector3f getScale() {
        return model.scale;
    }

    public Vector3f getRotation() {
        return model.rotation;
    }

    @Override
    public String toString() {
        return String.format("FinishedModel{name='%s', position=%s, scale=%s, rotation=%s, isTargeted=%s}",
                name, model.position, model.scale, model.rotation, isTargeted);
    }

    public RenderMode getRenderMode() {
        return renderMode;
    }
}
