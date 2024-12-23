package com.cgvsu.Pavel.math.aTransform;

import com.cgvsu.Pavel.math.matrices.Matrix4x4;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Matrix4f;

public class MegaTransform implements IAffine{//composite
    // point transformations
    private final List<IAffine> affine = new ArrayList<>();

    public MegaTransform() {}

    public static void main(String[] args) {
        float[] arr = new float[16];
        Matrix4f m = new Matrix4f(arr);

        for (int i = 0; i < 3; i++) {
            m.setElement(i, i, 1);
        }
    }
    @Override
    public Matrix4x4 vertexTransform() {

        Matrix4x4 modelMatrix = new Matrix4x4();
        modelMatrix.identity();
        for (IAffine a : affine) {
            modelMatrix.multiplyMM(a.vertexTransform());
        }
        return modelMatrix;
    }

    public void add(IAffine a) {
        affine.add(a);
    }

    public void clear() {
        this.affine.clear();
    }
}