package com.cgvsu.model;

import com.cgvsu.render_engine.RenderMode;

public class FinishedModel {

    public Model model;

    private String name;

    private RenderMode renderMode;

    public String getName() {
        return name;
    }

    public FinishedModel(Model model, String name, RenderMode renderModeFactory) {
        this.model = model;
        this.name = name;
        this.renderMode = renderModeFactory;
    }

}
