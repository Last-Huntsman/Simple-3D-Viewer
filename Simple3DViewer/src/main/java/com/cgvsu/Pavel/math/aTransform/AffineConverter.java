package com.cgvsu.Pavel.math.aTransform;

import com.cgvsu.Pavel.math.vectors.Vector3f;
import com.cgvsu.model.Model;

public class AffineConverter {

    private IAffine modelTransform;

    public void setModelTransform(IAffine modelTransform) {
        this.modelTransform = modelTransform;
    }

    public void apply(Model model) {
        for (Vector3f vect : model.vertices) {
            modelTransform.vertexTransform().mul(vect);//ToDO: умножение матрицы на вектор
        }
    }
}