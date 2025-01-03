package com.cgvsu.Pavel.math.aTransform;

import com.cgvsu.Pavel.math.matrices.Matrix3x3;
import com.cgvsu.Pavel.math.matrices.Matrix4x4;
import com.cgvsu.model.Model;

public class AffineTransformations {
    public static void main(String[] args) {
        xxx();
    }
    public static void xxx() {
        Model model = new Model();
        MegaTransform mt = new MegaTransform();

        mt.add(new Translate(1, 3, 0));
        mt.add(new Scale(1, 0, 0));
        mt.add(new Translate(-1, 0, 0));
        mt.add(new Rotate(0, AXIS.X));

        AffineConverter ac = new AffineConverter();

        ac.setModelTransform(mt);
        ac.apply(model);
        mt.add(new Rotate(1, AXIS.X));
        ac.apply(model);

        BuilderAffine x = new BuilderAffine();
        IAffine zzz = x.translate(1, 2, 4).rotateX(30).translateY(10).rotate(10, AXIS.Y).apply(mt).build();

    }

    public static Matrix4x4 modelMatrix(int tx, int ty, int tz,
                                        double alpha, double beta, double gamma,
                                        int sx, int sy, int sz) {
        Matrix4x4 modelMatrix = new Matrix4x4();
        Matrix4x4 transitionMatrix = translationMatrix(tx, ty, tz);
        Matrix4x4 rotationMatrix = makeMatrix4f(rotationMatrix(alpha, beta, gamma));
        Matrix4x4 scaleMatrix = makeMatrix4f(scaleMatrix(sx, sy, sz));
        modelMatrix.mul(transitionMatrix, rotationMatrix);
        modelMatrix.mul(scaleMatrix);
        return modelMatrix;
    }

    public static Matrix3x3 scaleMatrix(float sx, float sy, float sz) {
        Matrix3x3 S3 = new Matrix3x3();
        S3.setElement(0, 0, sx);
        S3.setElement(1, 1, sy);
        S3.setElement(2, 2, sz);
        return S3;
    }

    public static Matrix3x3 rotationAroundAxisMatrix(double alpha, AXIS axis) {
        Matrix3x3 R3 = new Matrix3x3();
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
                    if (j == i) R3.setElement(i, j, 1);
                    else R3.setElement(i, j, 0);
                } else {
                    R3.setElement(i, j, Ra[raInd]);
                    raInd++;
                }
            }
        }
        return R3;
    }

    public static Matrix3x3 rotationMatrix(double alpha, double beta, double gamma) {
        Matrix3x3 Rx = rotationAroundAxisMatrix(alpha, AXIS.X);
        Matrix3x3 Ry = rotationAroundAxisMatrix(beta, AXIS.Y);
        Matrix3x3 Rz = rotationAroundAxisMatrix(gamma, AXIS.X);
        Matrix3x3 R = new Matrix3x3();
        R.mul(Rz, Ry);
        R.mul(Rx);
        float y1 = (float) Math.cos(beta), y2 = (float) Math.sin(beta), y3 = (float) - Math.sin(beta), y4 = (float) Math.cos(beta);
        float x1 = (float) Math.cos(alpha), x2 = (float) Math.sin(alpha), x3 = (float) -Math.sin(alpha), x4 = (float) Math.cos(alpha);
        float z1 = (float) Math.cos(gamma), z2 = (float) Math.sin(gamma), z3 = (float) -Math.sin(gamma), z4 = (float) Math.cos(gamma);

        float[] floats = new float[]
                {(y1 * z1), (x1 * z2 + y2 * x3 * z1), (z2 * x2 + z1 * y2 * x4),
                        (y1 * z3), (x1 * z4 + y2 * z3 * x3), (z4 * x2 + x4 * y2 * z3),
                        (y3), (y4 * x3), (x4 * y4)};
        return new Matrix3x3(floats);
    }

    public static Matrix4x4 translationMatrix(float tx, float ty, float tz) {
        Matrix4x4 T4 = new Matrix4x4();
        for (int i = 0; i < 3; i++) {
            T4.setElement(i, i, 1);
        }
        T4.setColumn(3, new float[]{tx, ty, tz, 1});
        return T4;
    }

    public static Matrix4x4 makeMatrix4f(Matrix3x3 matrix3x3) {
        Matrix4x4 matrix4x4 = new Matrix4x4();
        matrix4x4.set(matrix3x3);
        return matrix4x4;
    }
}