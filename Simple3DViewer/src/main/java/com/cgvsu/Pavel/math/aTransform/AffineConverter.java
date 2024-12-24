package com.cgvsu.Pavel.math.aTransform;

import com.cgvsu.model.Model;

public class AffineConverter {

    private IAffine modelTransform;

    public void setModelTransform(IAffine modelTransform) {
        this.modelTransform = modelTransform;
    }

    public void apply(Model model) {
        model.vertices.replaceAll(v3 -> modelTransform.vertexTransform().multiplyMV(v3));
    }



}