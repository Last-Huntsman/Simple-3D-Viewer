package com.cgvsu.Pavel.math.aTransform;

import com.cgvsu.Pavel.math.matrices.Matrix3x3;
import com.cgvsu.Pavel.math.matrices.Matrix4x4;

public class Rotate implements IAffine {
    private double alpha;
    private final AXIS axis;

    public Rotate(double alpha, AXIS axis) {
        this.alpha = alpha;
        this.axis = axis;
        rotationAroundAxisMatrixInit();
    }

    private Matrix4x4 rotationAroundAxisMatrixInit() {
        Matrix3x3 rotationMatrix = new Matrix3x3();
        float[] Ra = new float[]{(float) Math.cos(alpha), (float) Math.sin(alpha), (float) -Math.sin(alpha), (float) Math.cos(alpha)};
        int ind;
        if (axis == AXIS.X) ind = 0;
        else {
            if (axis == AXIS.Y) ind = 1;
            else ind = 2;
        }

        int raInd = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == ind || j == ind) {
                    if (j == i) rotationMatrix.setElement(i, j, 1);
                    else rotationMatrix.setElement(i, j, 0);
                } else {
                    rotationMatrix.setElement(i, j, Ra[raInd]);
                    raInd++;
                }
            }
        }
        return AffineTransformations.makeMatrix4f(rotationMatrix);
    }
    public void addAlpha(float delta){
        alpha+=delta;
    }

    @Override
    public Matrix4x4 vertexTransform() {
        return rotationAroundAxisMatrixInit();
    }
}