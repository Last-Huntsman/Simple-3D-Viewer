package com.cgvsu.Pavel.math.vectors;

import javax.vecmath.Tuple3f;
import javax.vecmath.Tuple4d;
import javax.vecmath.Tuple4f;
import java.util.Objects;

/**
 * Класс Vector4X для работы с четырехмерными векторами.
 */
public final class Vector4f {

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

    public final void set(float var1, float var2, float var3, float var4) {
        this.x = var1;
        this.y = var2;
        this.z = var3;
        this.w = var4;
    }

    public final void set(float[] var) {
        this.x = var[0];
        this.y = var[1];
        this.z = var[2];
        this.w = var[3];
    }

    public final void set(Vector4f var) {
        this.x = var.x;
        this.y = var.y;
        this.z = var.z;
        this.w = var.w;
    }

    public final void get(float[] var) {
        var[0] = this.x;
        var[1] = this.y;
        var[2] = this.z;
        var[3] = this.w;
    }

    public final void get(Vector4f var) {
        var.x = this.x;
        var.y = this.y;
        var.z = this.z;
        var.w = this.w;
    }

    public final void add(Vector4f var1, Vector4f var2) {
        this.x = var1.x + var2.x;
        this.y = var1.y + var2.y;
        this.z = var1.z + var2.z;
        this.w = var1.w + var2.w;
    }

    public final void add(Vector4f var) {
        this.x += var.x;
        this.y += var.y;
        this.z += var.z;
        this.w += var.w;
    }

    public final void sub(Vector4f var1, Vector4f var2) {
        this.x = var1.x - var2.x;
        this.y = var1.y - var2.y;
        this.z = var1.z - var2.z;
        this.w = var1.w - var2.w;
    }

    public final void sub(Vector4f var) {
        this.x -= var.x;
        this.y -= var.y;
        this.z -= var.z;
        this.w -= var.w;
    }

    public final void negate(Vector4f var1) {
        this.x = -var1.x;
        this.y = -var1.y;
        this.z = -var1.z;
        this.w = -var1.w;
    }

    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        this.w = -this.w;
    }

    public final void scale(float var1, Vector4f var2) {
        this.x = var1 * var2.x;
        this.y = var1 * var2.y;
        this.z = var1 * var2.z;
        this.w = var1 * var2.w;
    }

    public final void scale(float var1) {
        this.x *= var1;
        this.y *= var1;
        this.z *= var1;
        this.w *= var1;
    }

    public final void set(Vector3f var1) {
        this.x = var1.x;
        this.y = var1.y;
        this.z = var1.z;
        this.w = 0.0F;
    }

    public final float length() {
        return (float)Math.sqrt((double)(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w));
    }

    public final float dot(Vector4f var1) {
        return this.x * var1.x + this.y * var1.y + this.z * var1.z + this.w * var1.w;
    }

    public final void normalize(javax.vecmath.Vector4f var1) {
        float var2 = (float)(1.0 / Math.sqrt((double)(var1.x * var1.x + var1.y * var1.y + var1.z * var1.z + var1.w * var1.w)));
        this.x = var1.x * var2;
        this.y = var1.y * var2;
        this.z = var1.z * var2;
        this.w = var1.w * var2;
    }

    public final void normalize() {
        float var1 = (float)(1.0 / Math.sqrt((double)(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w)));
        this.x *= var1;
        this.y *= var1;
        this.z *= var1;
        this.w *= var1;
    }

    public final float angle(Vector4f var) {
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
        final float eps = 1e-7f;
        return Math.abs(x - other.x) < eps && Math.abs(y - other.y) < eps && Math.abs(z - other.z) < eps && Math.abs(w - other.w) < eps;
    }

    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }
}
