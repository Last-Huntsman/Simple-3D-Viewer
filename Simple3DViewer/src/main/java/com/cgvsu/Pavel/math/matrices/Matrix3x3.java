package com.cgvsu.Pavel.math.matrices;

import com.cgvsu.Pavel.math.vectors.Vector3f;

/**
 * Класс Matrix3x3 для работы с матрицами размером 3x3.
 */
public class Matrix3x3 {

    public float[] elements = new float[9];

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

    public final void set(float[] var) {
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

    public final void set(Matrix3x3 var) {
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

    public String toString() {
        return this.elements[0] + ", " + this.elements[1] + ", " + this.elements[2] + "\n" + this.elements[3] + ", " + this.elements[4] + ", " + this.elements[5] + "\n" + this.elements[6] + ", " + this.elements[7] + ", " + this.elements[8] + "\n";
    }

    public final void setIdentity() {
        this.elements = new float[] {
                1, 0, 0,
                0, 1, 0,
                0, 0, 1
        };
    }

    public void setElement(int row, int col, float value) {
        elements[row * 3 + col] = value;
    }

    public final void add(float var) {
        for (int i = 0; i < 9; i++) {
            this.elements[i] += var;
        }
    }

    public final void add(float var1, Matrix3x3 var2) {
        for (int i = 0; i < 9; i++) {
            this.elements[i] = var2.elements[i] + var1;
        }
    }

    public final void add(Matrix3x3 var1, Matrix3x3 var2) {
        for (int i = 0; i < 9; i++) {
            this.elements[i] = var1.elements[i] + var2.elements[i];
        }
    }

    public final void add(Matrix3x3 var) {
        for (int i = 0; i < 9; i++) {
            this.elements[i] += var.elements[i];
        }
    }

    public final void sub(Matrix3x3 var1, Matrix3x3 var2) {
        for (int i = 0; i < 9; i++) {
            this.elements[i] = var1.elements[i] - var2.elements[i];
        }
    }

    public final void sub(Matrix3x3 var) {
        for (int i = 0; i < 9; i++) {
            this.elements[i] -= var.elements[i];
        }
    }

    public final void transpose() {
        float var = this.elements[3];
        this.elements[3] = this.elements[1];
        this.elements[1] = var;
        var = this.elements[6];
        this.elements[6] = this.elements[2];
        this.elements[2] = var;
        var = this.elements[7];
        this.elements[7] = this.elements[5];
        this.elements[5] = var;
    }

    public final void transpose(Matrix3x3 var) {
        if (this != var) {
            this.elements[0] = var.elements[0];
            this.elements[1] = var.elements[3];
            this.elements[2] = var.elements[6];
            this.elements[3] = var.elements[1];
            this.elements[4] = var.elements[4];
            this.elements[5] = var.elements[7];
            this.elements[6] = var.elements[2];
            this.elements[7] = var.elements[5];
            this.elements[8] = var.elements[8];
        } else {
            this.transpose();
        }
    }

    public final float determinant() {
        return this.elements[0] * (this.elements[4] * this.elements[8] - this.elements[5] * this.elements[7])
                - this.elements[1] * (this.elements[3] * this.elements[8] - this.elements[5] * this.elements[6])
                + this.elements[2] * (this.elements[3] * this.elements[7] - this.elements[4] * this.elements[6]);
    }

    public final void mul(float var) {
        for (int i = 0; i < 9; i++) {
            this.elements[i] *= var;
        }
    }

    public final void mul(float var1, Matrix3x3 var2) {
        for (int i = 0; i < 9; i++) {
            this.elements[i] = var1 * var2.elements[i];
        }
    }

    public final void mul(Matrix3x3 var) {
        float var1 = this.elements[0] * var.elements[0] + this.elements[1] * var.elements[3] + this.elements[2] * var.elements[6];
        float var2 = this.elements[0] * var.elements[1] + this.elements[1] * var.elements[4] + this.elements[2] * var.elements[7];
        float var3 = this.elements[0] * var.elements[2] + this.elements[1] * var.elements[5] + this.elements[2] * var.elements[8];
        float var4 = this.elements[3] * var.elements[0] + this.elements[4] * var.elements[3] + this.elements[5] * var.elements[6];
        float var5 = this.elements[3] * var.elements[1] + this.elements[4] * var.elements[4] + this.elements[5] * var.elements[7];
        float var6 = this.elements[3] * var.elements[2] + this.elements[4] * var.elements[5] + this.elements[5] * var.elements[8];
        float var7 = this.elements[6] * var.elements[0] + this.elements[7] * var.elements[3] + this.elements[8] * var.elements[6];
        float var8 = this.elements[6] * var.elements[1] + this.elements[7] * var.elements[4] + this.elements[8] * var.elements[7];
        float var9 = this.elements[6] * var.elements[2] + this.elements[7] * var.elements[5] + this.elements[8] * var.elements[8];
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

    public final void mul(Matrix3x3 var1, Matrix3x3 var2) {
        if (this != var1 && this != var2) {
            this.elements[0] = var1.elements[0] * var2.elements[0] + var1.elements[1] * var2.elements[3] + var1.elements[2] * var2.elements[6];
            this.elements[1] = var1.elements[0] * var2.elements[1] + var1.elements[1] * var2.elements[4] + var1.elements[2] * var2.elements[7];
            this.elements[2] = var1.elements[0] * var2.elements[2] + var1.elements[1] * var2.elements[5] + var1.elements[2] * var2.elements[8];
            this.elements[3] = var1.elements[3] * var2.elements[0] + var1.elements[4] * var2.elements[3] + var1.elements[5] * var2.elements[6];
            this.elements[4] = var1.elements[3] * var2.elements[1] + var1.elements[4] * var2.elements[4] + var1.elements[5] * var2.elements[7];
            this.elements[5] = var1.elements[3] * var2.elements[2] + var1.elements[4] * var2.elements[5] + var1.elements[5] * var2.elements[8];
            this.elements[6] = var1.elements[6] * var2.elements[0] + var1.elements[7] * var2.elements[3] + var1.elements[8] * var2.elements[6];
            this.elements[7] = var1.elements[6] * var2.elements[1] + var1.elements[7] * var2.elements[4] + var1.elements[8] * var2.elements[7];
            this.elements[8] = var1.elements[6] * var2.elements[2] + var1.elements[7] * var2.elements[5] + var1.elements[8] * var2.elements[8];
        } else {
            float var3 = var1.elements[0] * var2.elements[0] + var1.elements[1] * var2.elements[3] + var1.elements[2] * var2.elements[6];
            float var4 = var1.elements[0] * var2.elements[1] + var1.elements[1] * var2.elements[4] + var1.elements[2] * var2.elements[7];
            float var5 = var1.elements[0] * var2.elements[2] + var1.elements[1] * var2.elements[5] + var1.elements[2] * var2.elements[8];
            float var6 = var1.elements[3] * var2.elements[0] + var1.elements[4] * var2.elements[3] + var1.elements[5] * var2.elements[6];
            float var7 = var1.elements[3] * var2.elements[1] + var1.elements[4] * var2.elements[4] + var1.elements[5] * var2.elements[7];
            float var8 = var1.elements[3] * var2.elements[2] + var1.elements[4] * var2.elements[5] + var1.elements[5] * var2.elements[8];
            float var9 = var1.elements[6] * var2.elements[0] + var1.elements[7] * var2.elements[3] + var1.elements[8] * var2.elements[6];
            float var10 = var1.elements[6] * var2.elements[1] + var1.elements[7] * var2.elements[4] + var1.elements[8] * var2.elements[7];
            float var11 = var1.elements[6] * var2.elements[2] + var1.elements[7] * var2.elements[5] + var1.elements[8] * var2.elements[8];
            this.elements[0] = var3;
            this.elements[1] = var4;
            this.elements[2] = var5;
            this.elements[3] = var6;
            this.elements[4] = var7;
            this.elements[5] = var8;
            this.elements[6] = var9;
            this.elements[7] = var10;
            this.elements[8] = var11;
        }

    }

    public final void mul(Vector3f var) {
        float var1 = this.elements[0] * var.x + this.elements[1] * var.y + this.elements[2] * var.z;
        float var2 = this.elements[3] * var.x + this.elements[4] * var.y + this.elements[5] * var.z;
        float var3 = this.elements[6] * var.x + this.elements[7] * var.y + this.elements[8] * var.z;
        var.x = var1;
        var.y = var2;
        var.z = var3;
    }

    public int hashCode() {
        long var1 = 1L;
        var1 = 31L * var1 + (long)Float.floatToIntBits(this.elements[0]);
        var1 = 31L * var1 + (long)Float.floatToIntBits(this.elements[1]);
        var1 = 31L * var1 + (long)Float.floatToIntBits(this.elements[2]);
        var1 = 31L * var1 + (long)Float.floatToIntBits(this.elements[3]);
        var1 = 31L * var1 + (long)Float.floatToIntBits(this.elements[4]);
        var1 = 31L * var1 + (long)Float.floatToIntBits(this.elements[5]);
        var1 = 31L * var1 + (long)Float.floatToIntBits(this.elements[6]);
        var1 = 31L * var1 + (long)Float.floatToIntBits(this.elements[7]);
        var1 = 31L * var1 + (long)Float.floatToIntBits(this.elements[8]);
        return (int)(var1 ^ var1 >> 32);
    }

    public boolean equals(Object var) {
        try {
            Matrix3x3 var2 = (Matrix3x3)var;
            return this.elements[0] == var2.elements[0] && this.elements[1] == var2.elements[1] && this.elements[2] == var2.elements[2] && this.elements[3] == var2.elements[3] && this.elements[4] == var2.elements[4] && this.elements[5] == var2.elements[5] && this.elements[6] == var2.elements[6] && this.elements[7] == var2.elements[7] && this.elements[8] == var2.elements[8];
        } catch (ClassCastException var4) {
            return false;
        } catch (NullPointerException var5) {
            return false;
        }
    }

    public Object clone() {
        Matrix3x3 var1 = null;
        try {
            var1 = (Matrix3x3)super.clone();
            return var1;
        } catch (CloneNotSupportedException var3) {
            throw new InternalError();
        }
    }

}