package com.cgvsu.Pavel.math.aTransform;

import com.cgvsu.Pavel.math.matrices.Matrix3x3;
import com.cgvsu.Pavel.math.matrices.Matrix4x4;

public class Scale implements IAffine{
    private final float sx;
    private final float sy;
    private final float sz;

    public Scale(float sx, float sy, float sz) {
        this.sx = sx;
        this.sy = sy;
        this.sz = sz;
        scaleMatrixInit();
    }

    private Matrix4x4 scaleMatrixInit() {
        Matrix3x3 scaleMatrix = new Matrix3x3();
        scaleMatrix.setElement(0, 0, sx);
        scaleMatrix.setElement(1, 1, sy);
        scaleMatrix.setElement(2, 2, sz);
        return AffineTransformations.makeMatrix4f(scaleMatrix);
    }

    @Override
    public Matrix4x4 vertexTransform() {
        return scaleMatrixInit();
    }
}
