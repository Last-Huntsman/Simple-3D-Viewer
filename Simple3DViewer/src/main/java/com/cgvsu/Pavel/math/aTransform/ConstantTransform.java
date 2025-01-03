package com.cgvsu.Pavel.math.aTransform;

import com.cgvsu.Pavel.math.matrices.Matrix4x4;

public class ConstantTransform implements IAffine {

    private final Matrix4x4 modelMatrix;

    public ConstantTransform(Matrix4x4 modelMatrix) {
        this.modelMatrix = modelMatrix;
    }

    @Override
    public Matrix4x4 vertexTransform() {
        return modelMatrix;
    }

}