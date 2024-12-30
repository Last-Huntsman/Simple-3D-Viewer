package com.cgvsu.Pavel.math.matrices;


import com.cgvsu.Pavel.math.vectors.Vector3f;

/**
 * Класс Matrix3x3 для работы с матрицами размером 3x3.
 */
public class Matrix3x3 {
    public float[] elements;

    public Matrix3x3(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
        this.elements[0] = var1;
        this.elements[1] = var2;
        this.elements[2] = var3;
        this.elements[3] = var4;
        this.elements[4] = var5;
        this.elements[5] = var6;
        this.elements[6] = var7;
        this.elements[7] = var8;
        this.elements[8] = var9;
    }

    public Matrix3x3(float[] var) {
        this.elements[0] = var[0];
        this.elements[1] = var[1];
        this.elements[2] = var[2];
        this.elements[3] = var[3];
        this.elements[4] = var[4];
        this.elements[5] = var[5];
        this.elements[6] = var[6];
        this.elements[7] = var[7];
        this.elements[8] = var[8];
    }

    public Matrix3x3(Matrix3x3 var) {
        this.elements[0] = var.elements[0];
        this.elements[1] = var.elements[1];
        this.elements[2] = var.elements[2];
        this.elements[3] = var.elements[3];
        this.elements[4] = var.elements[4];
        this.elements[5] = var.elements[5];
        this.elements[6] = var.elements[6];
        this.elements[7] = var.elements[7];
        this.elements[8] = var.elements[8];
    }

    public Matrix3x3() {
        this.elements = new float[] {
                0, 0, 0,
                0, 0, 0,
                0, 0, 0
        };
    }

    public String toString() {
        return this.elements[0] + ", " + this.elements[1] + ", " + this.elements[2] + "\n" + this.elements[3] + ", " + this.elements[4] + ", " + this.elements[5] + "\n" + this.elements[6] + ", " + this.elements[7] + ", " + this.elements[8] + "\n";
    }

    public final void setIdentity() {
        this.elements[0] = 1.0F;
        this.elements[1] = 0.0F;
        this.elements[2] = 0.0F;
        this.elements[3] = 0.0F;
        this.elements[4] = 1.0F;
        this.elements[5] = 0.0F;
        this.elements[6] = 0.0F;
        this.elements[7] = 0.0F;
        this.elements[8] = 1.0F;
    }

    public void setElement(int row, int col, float value) {
        elements[row * 3 + col] = value;
    }

    public Matrix3x3 add(Matrix3x3 m2) {
        float[] result = new float[9];
        for (int i = 0; i < 9; i++) {
            result[i] = this.elements[i] + m2.elements[i];
        }
        return new Matrix3x3(result);
    }

    public Matrix3x3 subtract(Matrix3x3 m2) {
        float[] result = new float[9];
        for (int i = 0; i < 9; i++) {
            result[i] = this.elements[i] - m2.elements[i];
        }
        return new Matrix3x3(result);
    }

    public Matrix3x3 multiplyMM(Matrix3x3 m2) {
        float[] result = new float[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i * 3 + j] = 0;
                for (int k = 0; k < 3; k++) {
                    result[i * 3 + j] += this.elements[i * 3 + k] * m2.elements[k * 3 + j];
                }
            }
        }
        return new Matrix3x3(result);
    }

    public Vector3f multiplyMV(Vector3f v2) {
        float[] result = new float[3];
        for (int i = 0; i < 3; i++) {
            result[i] = this.elements[i * 3] * v2.x +
                    this.elements[i * 3 + 1] * v2.y +
                    this.elements[i * 3 + 2] * v2.z;
        }
        return new Vector3f(result[0], result[1], result[2]);

    }

    public Matrix3x3 transpose() {
        float[] result = new float[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i * 3 + j] = this.elements[j * 3 + i];
            }
        }
        return new Matrix3x3(result);
    }

    public static Matrix3x3 identity() {
        return new Matrix3x3(new float[]{1, 0, 0, 0, 1, 0, 0, 0, 1});
    }

    public static Matrix3x3 zero() {
        return new Matrix3x3(new float[9]);
    }

    public float determinant() {
        return elements[0] * (elements[4] * elements[8] - elements[5] * elements[7])
                - elements[1] * (elements[3] * elements[8] - elements[5] * elements[6])
                + elements[2] * (elements[3] * elements[7] - elements[4] * elements[6]);
    }

    public Matrix3x3 inverse() {
        float det = determinant();
        if (Math.abs(det) < 1e-6) {
            throw new IllegalArgumentException("Matrix3x3X.inverse: матрица вырождена.");
        }
        float[] result = new float[9];
        result[0] = (elements[4] * elements[8] - elements[5] * elements[7]) / det;
        result[1] = (elements[2] * elements[7] - elements[1] * elements[8]) / det;
        result[2] = (elements[1] * elements[5] - elements[2] * elements[4]) / det;
        result[3] = (elements[5] * elements[6] - elements[3] * elements[8]) / det;
        result[4] = (elements[0] * elements[8] - elements[2] * elements[6]) / det;
        result[5] = (elements[2] * elements[3] - elements[0] * elements[5]) / det;
        result[6] = (elements[3] * elements[7] - elements[4] * elements[6]) / det;
        result[7] = (elements[1] * elements[6] - elements[0] * elements[7]) / det;
        result[8] = (elements[0] * elements[4] - elements[1] * elements[3]) / det;
        return new Matrix3x3(result);
    }
    
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Matrix3x3 other)) return false;
        for (int i = 0; i < 9; i++) {
            if (Math.abs(this.elements[i] - other.elements[i]) > 1e-6) {
                return false;
            }
        }
        return true;
    }
}