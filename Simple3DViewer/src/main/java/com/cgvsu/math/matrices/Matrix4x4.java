package com.cgvsu.math.matrices;

import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.math.vectors.Vector4f;

/**
 * Класс Matrix4x4 для работы с матрицами размером 4x4.
 */
public class Matrix4x4 {

    public float[] elements = new float[16];

    public Matrix4x4(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, float var12, float var13, float var14, float var15, float var16) {
        this.elements[0] = var1;
        this.elements[1] = var2;
        this.elements[2] = var3;
        this.elements[3] = var4;
        this.elements[4] = var5;
        this.elements[5] = var6;
        this.elements[6] = var7;
        this.elements[7] = var8;
        this.elements[8] = var9;
        this.elements[9] = var10;
        this.elements[10] = var11;
        this.elements[11] = var12;
        this.elements[12] = var13;
        this.elements[13] = var14;
        this.elements[14] = var15;
        this.elements[15] = var16;
    }

    public Matrix4x4(float[] var1) {
        this.elements[0] = var1[0];
        this.elements[1] = var1[1];
        this.elements[2] = var1[2];
        this.elements[3] = var1[3];
        this.elements[4] = var1[4];
        this.elements[5] = var1[5];
        this.elements[6] = var1[6];
        this.elements[7] = var1[7];
        this.elements[8] = var1[8];
        this.elements[9] = var1[9];
        this.elements[10] = var1[10];
        this.elements[11] = var1[11];
        this.elements[12] = var1[12];
        this.elements[13] = var1[13];
        this.elements[14] = var1[14];
        this.elements[15] = var1[15];
    }

    public Matrix4x4(Matrix3x3 var1, Vector3f var2, float var3) {
        this.elements[0] = var1.elements[0] * var3;
        this.elements[1] = var1.elements[1] * var3;
        this.elements[2] = var1.elements[2] * var3;
        this.elements[3] = var2.x;
        this.elements[4] = var1.elements[4] * var3;
        this.elements[5] = var1.elements[5] * var3;
        this.elements[6] = var1.elements[6] * var3;
        this.elements[7] = var2.y;
        this.elements[8] = var1.elements[8] * var3;
        this.elements[9] = var1.elements[9] * var3;
        this.elements[10] = var1.elements[10] * var3;
        this.elements[11] = var2.z;
        this.elements[12] = 0.0F;
        this.elements[13] = 0.0F;
        this.elements[14] = 0.0F;
        this.elements[15] = 1.0F;
    }

    public Matrix4x4(Matrix4x4 var1) {
        this.elements[0] = var1.elements[0];
        this.elements[1] = var1.elements[1];
        this.elements[2] = var1.elements[2];
        this.elements[3] = var1.elements[3];
        this.elements[4] = var1.elements[4];
        this.elements[5] = var1.elements[5];
        this.elements[6] = var1.elements[6];
        this.elements[7] = var1.elements[7];
        this.elements[8] = var1.elements[8];
        this.elements[9] = var1.elements[9];
        this.elements[10] = var1.elements[10];
        this.elements[11] = var1.elements[11];
        this.elements[12] = var1.elements[12];
        this.elements[13] = var1.elements[13];
        this.elements[14] = var1.elements[14];
        this.elements[15] = var1.elements[15];
    }

    public Matrix4x4() {
        this.elements = new float[] {
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0
        };
    }

    public final void set(Matrix4x4 var1) {
        this.elements[0] = var1.elements[0];
        this.elements[1] = var1.elements[1];
        this.elements[2] = var1.elements[2];
        this.elements[3] = var1.elements[3];
        this.elements[4] = var1.elements[4];
        this.elements[5] = var1.elements[5];
        this.elements[6] = var1.elements[6];
        this.elements[7] = var1.elements[7];
        this.elements[8] = var1.elements[8];
        this.elements[9] = var1.elements[9];
        this.elements[10] = var1.elements[10];
        this.elements[11] = var1.elements[11];
        this.elements[12] = var1.elements[12];
        this.elements[13] = var1.elements[13];
        this.elements[14] = var1.elements[14];
        this.elements[15] = var1.elements[15];
    }

    public final void set(Matrix3x3 var1) {
        elements[0] = var1.elements[0];
        elements[1] = var1.elements[1];
        elements[2] = var1.elements[2];
        elements[3] = 0.0F;
        elements[4] = var1.elements[3];
        elements[5] = var1.elements[4];
        elements[6] = var1.elements[5];
        elements[7] = 0.0F;
        elements[8] = var1.elements[6];
        elements[9] = var1.elements[7];
        elements[10] = var1.elements[8];
        elements[11] = 0.0F;
        elements[12] = 0.0F;
        elements[13] = 0.0F;
        elements[14] = 0.0F;
        elements[15] = 1.0F;
    }

    public final void set(float var1) {
        this.elements[0] = var1;
        this.elements[1] = 0.0F;
        this.elements[2] = 0.0F;
        this.elements[3] = 0.0F;
        this.elements[4] = 0.0F;
        this.elements[5] = var1;
        this.elements[6] = 0.0F;
        this.elements[7] = 0.0F;
        this.elements[8] = 0.0F;
        this.elements[9] = 0.0F;
        this.elements[10] = var1;
        this.elements[11] = 0.0F;
        this.elements[12] = 0.0F;
        this.elements[13] = 0.0F;
        this.elements[14] = 0.0F;
        this.elements[15] = 1.0F;
    }

    public final void set(float[] var1) {
        this.elements[0] = var1[0];
        this.elements[1] = var1[1];
        this.elements[2] = var1[2];
        this.elements[3] = var1[3];
        this.elements[4] = var1[4];
        this.elements[5] = var1[5];
        this.elements[6] = var1[6];
        this.elements[7] = var1[7];
        this.elements[8] = var1[8];
        this.elements[9] = var1[9];
        this.elements[10] = var1[10];
        this.elements[11] = var1[11];
        this.elements[12] = var1[12];
        this.elements[13] = var1[13];
        this.elements[14] = var1[14];
        this.elements[15] = var1[15];
    }

    public final void set(javax.vecmath.Vector3f var1) {
        this.elements[0] = 1.0F;
        this.elements[1] = 0.0F;
        this.elements[2] = 0.0F;
        this.elements[3] = var1.x;
        this.elements[4] = 0.0F;
        this.elements[5] = 1.0F;
        this.elements[6] = 0.0F;
        this.elements[7] = var1.y;
        this.elements[8] = 0.0F;
        this.elements[9] = 0.0F;
        this.elements[10] = 1.0F;
        this.elements[11] = var1.z;
        this.elements[12] = 0.0F;
        this.elements[13] = 0.0F;
        this.elements[14] = 0.0F;
        this.elements[15] = 1.0F;
    }

    public final void set(float var1, javax.vecmath.Vector3f var2) {
        this.elements[0] = var1;
        this.elements[1] = 0.0F;
        this.elements[2] = 0.0F;
        this.elements[3] = var2.x;
        this.elements[4] = 0.0F;
        this.elements[5] = var1;
        this.elements[6] = 0.0F;
        this.elements[7] = var2.y;
        this.elements[8] = 0.0F;
        this.elements[9] = 0.0F;
        this.elements[10] = var1;
        this.elements[11] = var2.z;
        this.elements[12] = 0.0F;
        this.elements[13] = 0.0F;
        this.elements[14] = 0.0F;
        this.elements[15] = 1.0F;
    }

    public final void set(javax.vecmath.Vector3f var1, float var2) {
        this.elements[0] = var2;
        this.elements[1] = 0.0F;
        this.elements[2] = 0.0F;
        this.elements[3] = var2 * var1.x;
        this.elements[4] = 0.0F;
        this.elements[5] = var2;
        this.elements[6] = 0.0F;
        this.elements[7] = var2 * var1.y;
        this.elements[8] = 0.0F;
        this.elements[9] = 0.0F;
        this.elements[10] = var2;
        this.elements[11] = var2 * var1.z;
        this.elements[12] = 0.0F;
        this.elements[13] = 0.0F;
        this.elements[14] = 0.0F;
        this.elements[15] = 1.0F;
    }

    public final void set(Matrix3x3 var1, javax.vecmath.Vector3f var2, float var3) {
        this.elements[0] = var1.elements[0] * var3;
        this.elements[1] = var1.elements[1] * var3;
        this.elements[2] = var1.elements[2] * var3;
        this.elements[3] = var2.x;
        this.elements[4] = var1.elements[4] * var3;
        this.elements[5] = var1.elements[5] * var3;
        this.elements[6] = var1.elements[6] * var3;
        this.elements[7] = var2.y;
        this.elements[8] = var1.elements[8] * var3;
        this.elements[9] = var1.elements[9] * var3;
        this.elements[10] = var1.elements[10] * var3;
        this.elements[11] = var2.z;
        this.elements[12] = 0.0F;
        this.elements[13] = 0.0F;
        this.elements[14] = 0.0F;
        this.elements[15] = 1.0F;
    }

    public String toString() {
        return this.elements[0] + ", " + this.elements[1] + ", " + this.elements[2] + ", " + this.elements[3] + "\n" + this.elements[4] + ", " + this.elements[5] + ", " + this.elements[6] + ", " + this.elements[7] + "\n" + this.elements[8] + ", " + this.elements[9] + ", " + this.elements[10] + ", " + this.elements[11] + "\n" + this.elements[12] + ", " + this.elements[13] + ", " + this.elements[14] + ", " + this.elements[15] + "\n";
    }

    public final void setIdentity() {
        this.elements = new float[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        };
    }

    public void setElement(int row, int col, float value) {
        elements[row * 4 + col] = value;
    }

    public final void add(float var1) {
        this.elements[0] += var1;
        this.elements[1] += var1;
        this.elements[2] += var1;
        this.elements[3] += var1;
        this.elements[4] += var1;
        this.elements[5] += var1;
        this.elements[6] += var1;
        this.elements[7] += var1;
        this.elements[8] += var1;
        this.elements[9] += var1;
        this.elements[10] += var1;
        this.elements[11] += var1;
        this.elements[12] += var1;
        this.elements[13] += var1;
        this.elements[14] += var1;
        this.elements[15] += var1;
    }

    public final void add(float var1, Matrix4x4 var2) {
        this.elements[0] = var2.elements[0] + var1;
        this.elements[1] = var2.elements[1] + var1;
        this.elements[2] = var2.elements[2] + var1;
        this.elements[3] = var2.elements[3] + var1;
        this.elements[4] = var2.elements[4] + var1;
        this.elements[5] = var2.elements[5] + var1;
        this.elements[6] = var2.elements[6] + var1;
        this.elements[7] = var2.elements[7] + var1;
        this.elements[8] = var2.elements[8] + var1;
        this.elements[9] = var2.elements[9] + var1;
        this.elements[10] = var2.elements[10] + var1;
        this.elements[11] = var2.elements[11] + var1;
        this.elements[12] = var2.elements[12] + var1;
        this.elements[13] = var2.elements[13] + var1;
        this.elements[14] = var2.elements[14] + var1;
        this.elements[15] = var2.elements[15] + var1;
    }

    public final void add(Matrix4x4 var1, Matrix4x4 var2) {
        this.elements[0] = var1.elements[0] + var2.elements[0];
        this.elements[1] = var1.elements[1] + var2.elements[1];
        this.elements[2] = var1.elements[2] + var2.elements[2];
        this.elements[3] = var1.elements[3] + var2.elements[3];
        this.elements[4] = var1.elements[4] + var2.elements[4];
        this.elements[5] = var1.elements[5] + var2.elements[5];
        this.elements[6] = var1.elements[6] + var2.elements[6];
        this.elements[7] = var1.elements[7] + var2.elements[7];
        this.elements[8] = var1.elements[8] + var2.elements[8];
        this.elements[9] = var1.elements[9] + var2.elements[9];
        this.elements[10] = var1.elements[10] + var2.elements[10];
        this.elements[11] = var1.elements[11] + var2.elements[11];
        this.elements[12] = var1.elements[12] + var2.elements[12];
        this.elements[13] = var1.elements[13] + var2.elements[13];
        this.elements[14] = var1.elements[14] + var2.elements[14];
        this.elements[15] = var1.elements[15] + var2.elements[15];
    }

    public final void add(Matrix4x4 var) {
        for (int i = 0; i < 16; i++) {
            this.elements[i] += var.elements[i];
        }
    }

    public final void sub(Matrix4x4 var1, Matrix4x4 var2) {
        this.elements[0] = var1.elements[0] - var2.elements[0];
        this.elements[1] = var1.elements[1] - var2.elements[1];
        this.elements[2] = var1.elements[2] - var2.elements[2];
        this.elements[3] = var1.elements[3] - var2.elements[3];
        this.elements[4] = var1.elements[4] - var2.elements[4];
        this.elements[5] = var1.elements[5] - var2.elements[5];
        this.elements[6] = var1.elements[6] - var2.elements[6];
        this.elements[7] = var1.elements[7] - var2.elements[7];
        this.elements[8] = var1.elements[8] - var2.elements[8];
        this.elements[9] = var1.elements[9] - var2.elements[9];
        this.elements[10] = var1.elements[10] - var2.elements[10];
        this.elements[11] = var1.elements[11] - var2.elements[11];
        this.elements[12] = var1.elements[12] - var2.elements[12];
        this.elements[13] = var1.elements[13] - var2.elements[13];
        this.elements[14] = var1.elements[14] - var2.elements[14];
        this.elements[15] = var1.elements[15] - var2.elements[15];
    }

    public final void sub(Matrix4x4 var1) {
        this.elements[0] -= var1.elements[0];
        this.elements[1] -= var1.elements[1];
        this.elements[2] -= var1.elements[2];
        this.elements[3] -= var1.elements[3];
        this.elements[4] -= var1.elements[4];
        this.elements[5] -= var1.elements[5];
        this.elements[6] -= var1.elements[6];
        this.elements[7] -= var1.elements[7];
        this.elements[8] -= var1.elements[8];
        this.elements[9] -= var1.elements[9];
        this.elements[10] -= var1.elements[10];
        this.elements[11] -= var1.elements[11];
        this.elements[12] -= var1.elements[12];
        this.elements[13] -= var1.elements[13];
        this.elements[14] -= var1.elements[14];
        this.elements[15] -= var1.elements[15];
    }

    public final void transpose() {
        float var1 = this.elements[4];
        this.elements[4] = this.elements[1];
        this.elements[1] = var1;
        var1 = this.elements[8];
        this.elements[8] = this.elements[2];
        this.elements[2] = var1;
        var1 = this.elements[12];
        this.elements[12] = this.elements[3];
        this.elements[3] = var1;
        var1 = this.elements[9];
        this.elements[9] = this.elements[6];
        this.elements[6] = var1;
        var1 = this.elements[13];
        this.elements[13] = this.elements[7];
        this.elements[7] = var1;
        var1 = this.elements[14];
        this.elements[14] = this.elements[11];
        this.elements[11] = var1;
    }

    public final void transpose(Matrix4x4 var) {
        if (this != var) {
            this.elements[0] = var.elements[0];
            this.elements[1] = var.elements[4];
            this.elements[2] = var.elements[8];
            this.elements[3] = var.elements[12];
            this.elements[4] = var.elements[1];
            this.elements[5] = var.elements[5];
            this.elements[6] = var.elements[9];
            this.elements[7] = var.elements[13];
            this.elements[8] = var.elements[2];
            this.elements[9] = var.elements[6];
            this.elements[10] = var.elements[10];
            this.elements[11] = var.elements[14];
            this.elements[12] = var.elements[3];
            this.elements[13] = var.elements[7];
            this.elements[14] = var.elements[11];
            this.elements[15] = var.elements[15];
        } else {
            this.transpose();
        }

    }

    public final float determinant() {
        float var1 = this.elements[0] * (this.elements[5] * this.elements[10] * this.elements[15] + this.elements[6] * this.elements[11] * this.elements[13] + this.elements[7] * this.elements[9] * this.elements[14] - this.elements[7] * this.elements[10] * this.elements[13] - this.elements[5] * this.elements[11] * this.elements[14] - this.elements[6] * this.elements[9] * this.elements[15]);
        var1 -= this.elements[1] * (this.elements[4] * this.elements[10] * this.elements[15] + this.elements[6] * this.elements[11] * this.elements[12] + this.elements[7] * this.elements[8] * this.elements[14] - this.elements[7] * this.elements[10] * this.elements[12] - this.elements[4] * this.elements[11] * this.elements[14] - this.elements[6] * this.elements[8] * this.elements[15]);
        var1 += this.elements[2] * (this.elements[4] * this.elements[9] * this.elements[15] + this.elements[5] * this.elements[11] * this.elements[12] + this.elements[7] * this.elements[8] * this.elements[13] - this.elements[7] * this.elements[9] * this.elements[12] - this.elements[4] * this.elements[11] * this.elements[13] - this.elements[5] * this.elements[8] * this.elements[15]);
        var1 -= this.elements[3] * (this.elements[4] * this.elements[9] * this.elements[14] + this.elements[5] * this.elements[10] * this.elements[12] + this.elements[6] * this.elements[8] * this.elements[13] - this.elements[6] * this.elements[9] * this.elements[12] - this.elements[4] * this.elements[10] * this.elements[13] - this.elements[5] * this.elements[8] * this.elements[14]);
        return var1;
    }

    public final void mul(float var1) {
        this.elements[0] *= var1;
        this.elements[1] *= var1;
        this.elements[2] *= var1;
        this.elements[3] *= var1;
        this.elements[4] *= var1;
        this.elements[5] *= var1;
        this.elements[6] *= var1;
        this.elements[7] *= var1;
        this.elements[8] *= var1;
        this.elements[9] *= var1;
        this.elements[10] *= var1;
        this.elements[11] *= var1;
        this.elements[12] *= var1;
        this.elements[13] *= var1;
        this.elements[14] *= var1;
        this.elements[15] *= var1;
    }

    public final void mul(float var1, Matrix4x4 var2) {
        this.elements[0] = var2.elements[0] * var1;
        this.elements[1] = var2.elements[1] * var1;
        this.elements[2] = var2.elements[2] * var1;
        this.elements[3] = var2.elements[3] * var1;
        this.elements[4] = var2.elements[4] * var1;
        this.elements[5] = var2.elements[5] * var1;
        this.elements[6] = var2.elements[6] * var1;
        this.elements[7] = var2.elements[7] * var1;
        this.elements[8] = var2.elements[8] * var1;
        this.elements[9] = var2.elements[9] * var1;
        this.elements[10] = var2.elements[10] * var1;
        this.elements[11] = var2.elements[11] * var1;
        this.elements[12] = var2.elements[12] * var1;
        this.elements[13] = var2.elements[13] * var1;
        this.elements[14] = var2.elements[14] * var1;
        this.elements[15] = var2.elements[15] * var1;
    }

    // Метод умножения матрицы на другую матрицу (перемножение двух 4x4 матриц).
    public final void mul(Matrix4x4 var) {
        // Перемножение каждой строки первой матрицы на каждый столбец второй матрицы и сохранение результата в текущей матрице.

        // Умножаем первую строку текущей матрицы на первый столбец второй матрицы
        float var1 = this.elements[0] * var.elements[0] + this.elements[1] * var.elements[4] + this.elements[2] * var.elements[8] + this.elements[3] * var.elements[12];

        // Умножаем первую строку текущей матрицы на второй столбец второй матрицы
        float var2 = this.elements[0] * var.elements[1] + this.elements[1] * var.elements[5] + this.elements[2] * var.elements[9] + this.elements[3] * var.elements[13];

        // Умножаем первую строку текущей матрицы на третий столбец второй матрицы
        float var3 = this.elements[0] * var.elements[2] + this.elements[1] * var.elements[6] + this.elements[2] * var.elements[10] + this.elements[3] * var.elements[14];

        // Умножаем первую строку текущей матрицы на четвертый столбец второй матрицы
        float var4 = this.elements[0] * var.elements[3] + this.elements[1] * var.elements[7] + this.elements[2] * var.elements[11] + this.elements[3] * var.elements[15];

        // Умножаем вторую строку текущей матрицы на первый столбец второй матрицы
        float var5 = this.elements[4] * var.elements[0] + this.elements[5] * var.elements[4] + this.elements[6] * var.elements[8] + this.elements[7] * var.elements[12];

        // Умножаем вторую строку текущей матрицы на второй столбец второй матрицы
        float var6 = this.elements[4] * var.elements[1] + this.elements[5] * var.elements[5] + this.elements[6] * var.elements[9] + this.elements[7] * var.elements[13];

        // Умножаем вторую строку текущей матрицы на третий столбец второй матрицы
        float var7 = this.elements[4] * var.elements[2] + this.elements[5] * var.elements[6] + this.elements[6] * var.elements[10] + this.elements[7] * var.elements[14];

        // Умножаем вторую строку текущей матрицы на четвертый столбец второй матрицы
        float var8 = this.elements[4] * var.elements[3] + this.elements[5] * var.elements[7] + this.elements[6] * var.elements[11] + this.elements[7] * var.elements[15];

        // Умножаем третью строку текущей матрицы на первый столбец второй матрицы
        float var9 = this.elements[8] * var.elements[0] + this.elements[9] * var.elements[4] + this.elements[10] * var.elements[8] + this.elements[11] * var.elements[12];

        // Умножаем третью строку текущей матрицы на второй столбец второй матрицы
        float var10 = this.elements[8] * var.elements[1] + this.elements[9] * var.elements[5] + this.elements[10] * var.elements[9] + this.elements[11] * var.elements[13];

        // Умножаем третью строку текущей матрицы на третий столбец второй матрицы
        float var11 = this.elements[8] * var.elements[2] + this.elements[9] * var.elements[6] + this.elements[10] * var.elements[10] + this.elements[11] * var.elements[14];

        // Умножаем третью строку текущей матрицы на четвертый столбец второй матрицы
        float var12 = this.elements[8] * var.elements[3] + this.elements[9] * var.elements[7] + this.elements[10] * var.elements[11] + this.elements[11] * var.elements[15];

        // Умножаем четвертую строку текущей матрицы на первый столбец второй матрицы
        float var13 = this.elements[12] * var.elements[0] + this.elements[13] * var.elements[4] + this.elements[14] * var.elements[8] + this.elements[15] * var.elements[12];

        // Умножаем четвертую строку текущей матрицы на второй столбец второй матрицы
        float var14 = this.elements[12] * var.elements[1] + this.elements[13] * var.elements[5] + this.elements[14] * var.elements[9] + this.elements[15] * var.elements[13];

        // Умножаем четвертую строку текущей матрицы на третий столбец второй матрицы
        float var15 = this.elements[12] * var.elements[2] + this.elements[13] * var.elements[6] + this.elements[14] * var.elements[10] + this.elements[15] * var.elements[14];

        // Умножаем четвертую строку текущей матрицы на четвертый столбец второй матрицы
        float var16 = this.elements[12] * var.elements[3] + this.elements[13] * var.elements[7] + this.elements[14] * var.elements[11] + this.elements[15] * var.elements[15];

        // Сохраняем полученные результаты в элементы текущей матрицы.
        this.elements[0] = var1;
        this.elements[1] = var2;
        this.elements[2] = var3;
        this.elements[3] = var4;
        this.elements[4] = var5;
        this.elements[5] = var6;
        this.elements[6] = var7;
        this.elements[7] = var8;
        this.elements[8] = var9;
        this.elements[9] = var10;
        this.elements[10] = var11;
        this.elements[11] = var12;
        this.elements[12] = var13;
        this.elements[13] = var14;
        this.elements[14] = var15;
        this.elements[15] = var16;
    }

    public final void mul(Matrix4x4 var1, Matrix4x4 var2) {
        if (this != var1 && this != var2) {
            this.elements[0] = var1.elements[0] * var2.elements[0] + var1.elements[1] * var2.elements[4] + var1.elements[2] * var2.elements[8] + var1.elements[3] * var2.elements[12];
            this.elements[1] = var1.elements[0] * var2.elements[1] + var1.elements[1] * var2.elements[5] + var1.elements[2] * var2.elements[9] + var1.elements[3] * var2.elements[13];
            this.elements[2] = var1.elements[0] * var2.elements[2] + var1.elements[1] * var2.elements[6] + var1.elements[2] * var2.elements[10] + var1.elements[3] * var2.elements[14];
            this.elements[3] = var1.elements[0] * var2.elements[3] + var1.elements[1] * var2.elements[7] + var1.elements[2] * var2.elements[11] + var1.elements[3] * var2.elements[15];
            this.elements[4] = var1.elements[4] * var2.elements[0] + var1.elements[5] * var2.elements[4] + var1.elements[6] * var2.elements[8] + var1.elements[7] * var2.elements[12];
            this.elements[5] = var1.elements[4] * var2.elements[1] + var1.elements[5] * var2.elements[5] + var1.elements[6] * var2.elements[9] + var1.elements[7] * var2.elements[13];
            this.elements[6] = var1.elements[4] * var2.elements[2] + var1.elements[5] * var2.elements[6] + var1.elements[6] * var2.elements[10] + var1.elements[7] * var2.elements[14];
            this.elements[7] = var1.elements[4] * var2.elements[3] + var1.elements[5] * var2.elements[7] + var1.elements[6] * var2.elements[11] + var1.elements[7] * var2.elements[15];
            this.elements[8] = var1.elements[8] * var2.elements[0] + var1.elements[9] * var2.elements[4] + var1.elements[10] * var2.elements[8] + var1.elements[11] * var2.elements[12];
            this.elements[9] = var1.elements[8] * var2.elements[1] + var1.elements[9] * var2.elements[5] + var1.elements[10] * var2.elements[9] + var1.elements[11] * var2.elements[13];
            this.elements[10] = var1.elements[8] * var2.elements[2] + var1.elements[9] * var2.elements[6] + var1.elements[10] * var2.elements[10] + var1.elements[11] * var2.elements[14];
            this.elements[11] = var1.elements[8] * var2.elements[3] + var1.elements[9] * var2.elements[7] + var1.elements[10] * var2.elements[11] + var1.elements[11] * var2.elements[15];
            this.elements[12] = var1.elements[12] * var2.elements[0] + var1.elements[13] * var2.elements[4] + var1.elements[14] * var2.elements[8] + var1.elements[15] * var2.elements[12];
            this.elements[13] = var1.elements[12] * var2.elements[1] + var1.elements[13] * var2.elements[5] + var1.elements[14] * var2.elements[9] + var1.elements[15] * var2.elements[13];
            this.elements[14] = var1.elements[12] * var2.elements[2] + var1.elements[13] * var2.elements[6] + var1.elements[14] * var2.elements[10] + var1.elements[15] * var2.elements[14];
            this.elements[15] = var1.elements[12] * var2.elements[3] + var1.elements[13] * var2.elements[7] + var1.elements[14] * var2.elements[11] + var1.elements[15] * var2.elements[15];
        } else {
            float var3 = var1.elements[0] * var2.elements[0] + var1.elements[1] * var2.elements[4] + var1.elements[2] * var2.elements[8] + var1.elements[3] * var2.elements[12];
            float var4 = var1.elements[0] * var2.elements[1] + var1.elements[1] * var2.elements[5] + var1.elements[2] * var2.elements[9] + var1.elements[3] * var2.elements[13];
            float var5 = var1.elements[0] * var2.elements[2] + var1.elements[1] * var2.elements[6] + var1.elements[2] * var2.elements[10] + var1.elements[3] * var2.elements[14];
            float var6 = var1.elements[0] * var2.elements[3] + var1.elements[1] * var2.elements[7] + var1.elements[2] * var2.elements[11] + var1.elements[3] * var2.elements[15];
            float var7 = var1.elements[4] * var2.elements[0] + var1.elements[5] * var2.elements[4] + var1.elements[6] * var2.elements[8] + var1.elements[7] * var2.elements[12];
            float var8 = var1.elements[4] * var2.elements[1] + var1.elements[5] * var2.elements[5] + var1.elements[6] * var2.elements[9] + var1.elements[7] * var2.elements[13];
            float var9 = var1.elements[4] * var2.elements[2] + var1.elements[5] * var2.elements[6] + var1.elements[6] * var2.elements[10] + var1.elements[7] * var2.elements[14];
            float var10 = var1.elements[4] * var2.elements[3] + var1.elements[5] * var2.elements[7] + var1.elements[6] * var2.elements[11] + var1.elements[7] * var2.elements[15];
            float var11 = var1.elements[8] * var2.elements[0] + var1.elements[9] * var2.elements[4] + var1.elements[10] * var2.elements[8] + var1.elements[11] * var2.elements[12];
            float var12 = var1.elements[8] * var2.elements[1] + var1.elements[9] * var2.elements[5] + var1.elements[10] * var2.elements[9] + var1.elements[11] * var2.elements[13];
            float var13 = var1.elements[8] * var2.elements[2] + var1.elements[9] * var2.elements[6] + var1.elements[10] * var2.elements[10] + var1.elements[11] * var2.elements[14];
            float var14 = var1.elements[8] * var2.elements[3] + var1.elements[9] * var2.elements[7] + var1.elements[10] * var2.elements[11] + var1.elements[11] * var2.elements[15];
            float var15 = var1.elements[12] * var2.elements[0] + var1.elements[13] * var2.elements[4] + var1.elements[14] * var2.elements[8] + var1.elements[15] * var2.elements[12];
            float var16 = var1.elements[12] * var2.elements[1] + var1.elements[13] * var2.elements[5] + var1.elements[14] * var2.elements[9] + var1.elements[15] * var2.elements[13];
            float var17 = var1.elements[12] * var2.elements[2] + var1.elements[13] * var2.elements[6] + var1.elements[14] * var2.elements[10] + var1.elements[15] * var2.elements[14];
            float var18 = var1.elements[12] * var2.elements[3] + var1.elements[13] * var2.elements[7] + var1.elements[14] * var2.elements[11] + var1.elements[15] * var2.elements[15];
            this.elements[0] = var3;
            this.elements[1] = var4;
            this.elements[2] = var5;
            this.elements[3] = var6;
            this.elements[4] = var7;
            this.elements[5] = var8;
            this.elements[6] = var9;
            this.elements[7] = var10;
            this.elements[8] = var11;
            this.elements[9] = var12;
            this.elements[10] = var13;
            this.elements[11] = var14;
            this.elements[12] = var15;
            this.elements[13] = var16;
            this.elements[14] = var17;
            this.elements[15] = var18;
        }

    }

    public final Vector3f mul(Vector3f var) {
        float var1 = this.elements[0] * var.x + this.elements[1] * var.y + this.elements[2] * var.z + this.elements[3] * 1;
        float var2 = this.elements[4] * var.x + this.elements[5] * var.y + this.elements[6] * var.z + this.elements[7] * 1;
        float var3 = this.elements[8] * var.x + this.elements[9] * var.y + this.elements[10] * var.z + this.elements[11] * 1;
        return new Vector3f(var1,var2,var3);
    }

    public final void mul(Vector4f var) {
        float var1 = this.elements[0] * var.x + this.elements[1] * var.y + this.elements[2] * var.z + this.elements[3] * var.w;
        float var2 = this.elements[4] * var.x + this.elements[5] * var.y + this.elements[6] * var.z + this.elements[7] * var.w;
        float var3 = this.elements[8] * var.x + this.elements[9] * var.y + this.elements[10] * var.z + this.elements[11] * var.w;
        float var4 = this.elements[12] * var.x + this.elements[13] * var.y + this.elements[14] * var.z + this.elements[15] * var.w;
        var.x = var1;
        var.y = var2;
        var.z = var3;
        var.w = var4;
    }
    public Vector4f mulV(Vector4f var) {
        float var1 = this.elements[0] * var.x + this.elements[1] * var.y + this.elements[2] * var.z + this.elements[3] * var.w;
        float var2 = this.elements[4] * var.x + this.elements[5] * var.y + this.elements[6] * var.z + this.elements[7] * var.w;
        float var3 = this.elements[8] * var.x + this.elements[9] * var.y + this.elements[10] * var.z + this.elements[11] * var.w;
        float var4 = this.elements[12] * var.x + this.elements[13] * var.y + this.elements[14] * var.z + this.elements[15] * var.w;
        return new Vector4f(var1, var2, var3, var4);
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
        var1 = 31L * var1 + (long)Float.floatToIntBits(this.elements[9]);
        var1 = 31L * var1 + (long)Float.floatToIntBits(this.elements[10]);
        var1 = 31L * var1 + (long)Float.floatToIntBits(this.elements[11]);
        var1 = 31L * var1 + (long)Float.floatToIntBits(this.elements[12]);
        var1 = 31L * var1 + (long)Float.floatToIntBits(this.elements[13]);
        var1 = 31L * var1 + (long)Float.floatToIntBits(this.elements[14]);
        var1 = 31L * var1 + (long)Float.floatToIntBits(this.elements[15]);
        return (int)(var1 ^ var1 >> 32);
    }

    public boolean equals(Object var1) {
        try {
            Matrix4x4 var2 = (Matrix4x4)var1;
            return this.elements[0] == var2.elements[0] && this.elements[1] == var2.elements[1] && this.elements[2] == var2.elements[2] && this.elements[3] == var2.elements[3] && this.elements[4] == var2.elements[4] && this.elements[5] == var2.elements[5] && this.elements[6] == var2.elements[6] && this.elements[7] == var2.elements[7] && this.elements[8] == var2.elements[8] && this.elements[9] == var2.elements[9] && this.elements[10] == var2.elements[10] && this.elements[11] == var2.elements[11] && this.elements[12] == var2.elements[12] && this.elements[13] == var2.elements[13] && this.elements[14] == var2.elements[14] && this.elements[15] == var2.elements[15];
        } catch (ClassCastException var4) {
            return false;
        } catch (NullPointerException var5) {
            return false;
        }
    }

    public Object clone() {
        Matrix4x4 var1 = null;

        try {
            var1 = (Matrix4x4)super.clone();
            return var1;
        } catch (CloneNotSupportedException var3) {
            throw new InternalError();
        }
    }

    public final void setColumn(int var1, float[] var2) {
        switch (var1) {
            case 0:
                this.elements[0] = var2[0];
                this.elements[4] = var2[1];
                this.elements[8] = var2[2];
                this.elements[12] = var2[3];
                break;
            case 1:
                this.elements[1] = var2[0];
                this.elements[5] = var2[1];
                this.elements[9] = var2[2];
                this.elements[13] = var2[3];
                break;
            case 2:
                this.elements[2] = var2[0];
                this.elements[6] = var2[1];
                this.elements[10] = var2[2];
                this.elements[14] = var2[3];
                break;
            case 3:
                this.elements[3] = var2[0];
                this.elements[7] = var2[1];
                this.elements[11] = var2[2];
                this.elements[15] = var2[3];
                break;
            default:
                throw new ArrayIndexOutOfBoundsException();
        }

    }

    public void setColumn(int col, Vector4f vector) {
        if (col < 0 || col >= 4) {
            throw new IndexOutOfBoundsException("Индекс столбца должен быть в диапазоне [0, 3].");
        }
        elements[col] = vector.x;
        elements[4 + col] = vector.y;
        elements[2 * 4 + col] = vector.z;
        elements[3 * 4 + col] = vector.w;
    }

}