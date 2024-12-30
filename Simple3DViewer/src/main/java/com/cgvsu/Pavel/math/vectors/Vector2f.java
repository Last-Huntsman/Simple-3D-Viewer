package com.cgvsu.Pavel.math.vectors;

import javax.vecmath.Tuple2d;
import javax.vecmath.Tuple2f;
import java.util.Objects;

/**
 * Класс Vector2X для работы с двухмерными векторами.
 */
public final class Vector2f {

    public float x, y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f(float[] var){
        this.x = var[0];
        this.y = var[1];
    }

    public Vector2f(Vector2f var){
        this.x = var.x;
        this.y = var.y;
    }

    public Vector2f(){
        this.x =0.0F;
        this.y =0.0F;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    public final void set(float var1, float var2) {
        this.x = var1;
        this.y = var2;
    }

    public final void set(float[] var1) {
        this.x = var1[0];
        this.y = var1[1];
    }

    public final void set(Vector2f var) {
        this.x = var.x;
        this.y = var.y;
    }

    public final void get(float[] var) {
        var[0] = this.x;
        var[1] = this.y;
    }

    public final void add(Vector2f var1, Vector2f var2) {
        this.x = var1.x + var2.x;
        this.y = var1.y + var2.y;
    }

    public final void add(Vector2f var) {
        this.x += var.x;
        this.y += var.y;
    }

    public final void sub(Vector2f var1, Vector2f var2) {
        this.x = var1.x - var2.x;
        this.y = var1.y - var2.y;
    }

    public final void sub(Vector2f var) {
        this.x -= var.x;
        this.y -= var.y;
    }

    public final void negate(Vector2f var) {
        this.x = -var.x;
        this.y = -var.y;
    }

    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    public final void scale(float var1, Tuple2f var2) {
        this.x = var1 * var2.x;
        this.y = var1 * var2.y;
    }

    public final void scale(float var) {
        this.x *= var;
        this.y *= var;
    }

    public final float length() {
        return (float)Math.sqrt((double)(this.x * this.x + this.y * this.y));
    }

    public final float dot(javax.vecmath.Vector2f var1) {
        return this.x * var1.x + this.y * var1.y;
    }

    public final void normalize(Vector2f var1) {
        float var2 = (float)(1.0 / Math.sqrt((double)(var1.x * var1.x + var1.y * var1.y)));
        this.x = var1.x * var2;
        this.y = var1.y * var2;
    }

    public final void normalize() {
        float var1 = (float)(1.0 / Math.sqrt((double)(this.x * this.x + this.y * this.y)));
        this.x *= var1;
        this.y *= var1;
    }

    public final float angle(javax.vecmath.Vector2f var1) {
        double var2 = (double)(this.dot(var1) / (this.length() * var1.length()));
        if (var2 < -1.0) {
            var2 = -1.0;
        }

        if (var2 > 1.0) {
            var2 = 1.0;
        }

        return (float)Math.acos(var2);
    }

    public Vector2f clone() {
        return new Vector2f(x, y);
    }

    public boolean equals(Vector3f other) {
        final float eps = 1e-7f;
        return Math.abs(x - other.x) < eps && Math.abs(y - other.y) < eps;
    }

    public int hashCode() {
        return Objects.hash(x, y);
    }

}
