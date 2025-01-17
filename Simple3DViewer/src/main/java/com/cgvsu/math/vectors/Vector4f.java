package com.cgvsu.math.vectors;

import java.util.Objects;

/**
 * Класс Vector4X для работы с четырехмерными векторами.
 */
public  class Vector4f {

    public float x, y, z, w;

    public Vector4f(float var1, float var2, float var3, float var4) {
        this.x = var1;
        this.y = var2;
        this.z = var3;
        this.w = var4;
    }

    public Vector4f(float[] var) {
        this.x = var[0];
        this.y = var[1];
        this.z = var[2];
        this.w = var[3];
    }

    public Vector4f(Vector4f var) {
        this.x = var.x;
        this.y = var.y;
        this.z = var.z;
        this.w = var.w;
    }

    public Vector4f() {
        this.x = 0.0F;
        this.y = 0.0F;
        this.z = 0.0F;
        this.w = 0.0F;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
    }

    public  void set(float var1, float var2, float var3, float var4) {
        this.x = var1;
        this.y = var2;
        this.z = var3;
        this.w = var4;
    }

    public  void set(float[] var) {
        this.x = var[0];
        this.y = var[1];
        this.z = var[2];
        this.w = var[3];
    }

    public  void set(Vector4f var) {
        this.x = var.x;
        this.y = var.y;
        this.z = var.z;
        this.w = var.w;
    }

    public  void get(float[] var) {
        var[0] = this.x;
        var[1] = this.y;
        var[2] = this.z;
        var[3] = this.w;
    }

    public  void get(Vector4f var) {
        var.x = this.x;
        var.y = this.y;
        var.z = this.z;
        var.w = this.w;
    }

    public  void add(Vector4f var1, Vector4f var2) {
        this.x = var1.x + var2.x;
        this.y = var1.y + var2.y;
        this.z = var1.z + var2.z;
        this.w = var1.w + var2.w;
    }

    public  void add(Vector4f var) {
        this.x += var.x;
        this.y += var.y;
        this.z += var.z;
        this.w += var.w;
    }

    public  void sub(Vector4f var1, Vector4f var2) {
        this.x = var1.x - var2.x;
        this.y = var1.y - var2.y;
        this.z = var1.z - var2.z;
        this.w = var1.w - var2.w;
    }

    public  void sub(Vector4f var) {
        this.x -= var.x;
        this.y -= var.y;
        this.z -= var.z;
        this.w -= var.w;
    }

    public  void negate(Vector4f var1) {
        this.x = -var1.x;
        this.y = -var1.y;
        this.z = -var1.z;
        this.w = -var1.w;
    }

    public  void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        this.w = -this.w;
    }

    public  void scale(float var1, Vector4f var2) {
        this.x = var1 * var2.x;
        this.y = var1 * var2.y;
        this.z = var1 * var2.z;
        this.w = var1 * var2.w;
    }

    public  void scale(float var1) {
        this.x *= var1;
        this.y *= var1;
        this.z *= var1;
        this.w *= var1;
    }

    public  void set(Vector3f var1) {
        this.x = var1.x;
        this.y = var1.y;
        this.z = var1.z;
        this.w = 0.0F;
    }

    public  float length() {
        return (float)Math.sqrt((double)(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w));
    }

    public  float dot(Vector4f var1) {
        return this.x * var1.x + this.y * var1.y + this.z * var1.z + this.w * var1.w;
    }

    public  void normalize(javax.vecmath.Vector4f var1) {
        float var2 = (float)(1.0 / Math.sqrt((double)(var1.x * var1.x + var1.y * var1.y + var1.z * var1.z + var1.w * var1.w)));
        this.x = var1.x * var2;
        this.y = var1.y * var2;
        this.z = var1.z * var2;
        this.w = var1.w * var2;
    }

    public  void normalize() {
        float var1 = (float)(1.0 / Math.sqrt((double)(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w)));
        this.x *= var1;
        this.y *= var1;
        this.z *= var1;
        this.w *= var1;
    }

    public  float angle(Vector4f var) {
        double var2 = (double)(this.dot(var) / (this.length() * var.length()));
        if (var2 < -1.0) {
            var2 = -1.0;
        }

        if (var2 > 1.0) {
            var2 = 1.0;
        }

        return (float)Math.acos(var2);
    }

    public Vector4f clone() {
        return new Vector4f(x, y, z, w);
    }

    public boolean equals(Vector4f other) {
         float eps = 1e-7f;
        return Math.abs(x - other.x) < eps && Math.abs(y - other.y) < eps && Math.abs(z - other.z) < eps && Math.abs(w - other.w) < eps;
    }

    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }
}
