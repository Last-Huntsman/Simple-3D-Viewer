package com.cgvsu.Pavel.math.aTransform;

import com.cgvsu.Pavel.math.matrices.Matrix4x4;
public class Translate implements IAffine {
    private Float tx, ty, tz;
    private Float t;
    private AXIS axis;

    public Translate(float tx, float ty, float tz) {
        this.tx = tx;
        this.ty = ty;
        this.tz = tz;
    }

    public Translate(float t, AXIS axis) {
        this.t = t;
        this.axis = axis;
    }

    private Matrix4x4 translationMatrixInit() {
        Matrix4x4 translationMatrix = new Matrix4x4();
        if (tx != null) {
            for (int i = 0; i < 3; i++) {
                translationMatrix.setElement(i, i, 1);
            }
            translationMatrix.setColumn(3, new float[]{tx, ty, tz, 1});
        }
        if (t != null) {
            float[] floats = new float[4];
            floats[axis.ordinal()] = t;
            floats[3] = 1;
            for (int i = 0; i < 3; i++) {  
                translationMatrix.setElement(i, i, 1);
            }
            translationMatrix.setColumn(3, floats);
        }
        return translationMatrix;
    }

    @Override
    public Matrix4x4 vertexTransform() {
        return translationMatrixInit();
    }
}