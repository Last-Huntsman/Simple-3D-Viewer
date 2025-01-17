package com.cgvsu.math.vectors;

import java.util.Objects;

/**
 * Класс Vector3X для работы с трехмерными векторами.
 */
public  class Vector3f {

    public float x, y, z;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f(float[] var){
        this.x = var[0];
        this.y = var[1];
        this.z = var[2];
    }

    public Vector3f(Vector3f var){
        this.x = var.x;
        this.y = var.y;
        this.z = var.z;
    }

    public Vector3f(){
        this.x =0.0F;
        this.y =0.0F;
        this.z =0.0F;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    public  void set(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public  void set(float[] var) {
        this.x = var[0];
        this.y = var[1];
        this.z = var[2];
    }

    public  void set(Vector3f var) {
        this.x = var.x;
        this.y = var.y;
        this.z = var.z;
    }

    public  void get(float[] var) {
        var[0] = this.x;
        var[1] = this.y;
        var[2] = this.z;
    }

    public  void get(Vector3f var) {
        var.x = this.x;
        var.y = this.y;
        var.z = this.z;
    }

    public  void add(Vector3f var1, Vector3f var2) {
        this.x = var1.x + var2.x;
        this.y = var1.y + var2.y;
        this.z = var1.z + var2.z;
    }

    public  void add(Vector3f var1) {
        this.x += var1.x;
        this.y += var1.y;
        this.z += var1.z;
    }

    public  void sub(Vector3f var1, Vector3f var2) {
        this.x = var1.x - var2.x;
        this.y = var1.y - var2.y;
        this.z = var1.z - var2.z;
    }

    public  void sub(Vector3f var) {
        this.x -= var.x;
        this.y -= var.y;
        this.z -= var.z;
    }

    public  void negate(Vector3f var) {
        this.x = -var.x;
        this.y = -var.y;
        this.z = -var.z;
    }

    public  void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }

    public  void scale(float var1, Vector3f var2) {
        this.x = var1 * var2.x;
        this.y = var1 * var2.y;
        this.z = var1 * var2.z;
    }

    public  void scale(float var) {
        this.x *= var;
        this.y *= var;
        this.z *= var;
    }

    public  float length() {
        return (float)Math.sqrt((double)(this.x * this.x + this.y * this.y + this.z * this.z));
    }

    public  void cross(Vector3f var1, Vector3f var2) {
        float var3 = var1.y * var2.z - var1.z * var2.y;
        float var4 = var2.x * var1.z - var2.z * var1.x;
        this.z = var1.x * var2.y - var1.y * var2.x;
        this.x = var3;
        this.y = var4;
    }

    public  void cross(Vector3f var) {
        float var1 = this.y * var.z - this.z * var.y;
        float var2 = this.z * var.x - this.x * var.z;
        float var3 = this.x * var.y - this.y * var.x;
        this.x = var1;
        this.y = var2;
        this.z = var3;
    }

    public  float dot(Vector3f var1) {
        return this.x * var1.x + this.y * var1.y + this.z * var1.z;
    }

    public  void normalize(Vector3f var1) {
        float var2 = (float)(1.0 / Math.sqrt((double)(var1.x * var1.x + var1.y * var1.y + var1.z * var1.z)));
        this.x = var1.x * var2;
        this.y = var1.y * var2;
        this.z = var1.z * var2;
    }

    public  void normalize() {
        float var1 = (float)(1.0 / Math.sqrt((double)(this.x * this.x + this.y * this.y + this.z * this.z)));
        this.x *= var1;
        this.y *= var1;
        this.z *= var1;
    }

    public  float angle(Vector3f var) {
        double var2 = (double)(this.dot(var) / (this.length() * var.length()));
        if (var2 < -1.0) {
            var2 = -1.0;
        }

        if (var2 > 1.0) {
            var2 = 1.0;
        }

        return (float)Math.acos(var2);
    }

    public float distance(Vector3f other) {
        float dx = this.x - other.x;
        float dy = this.y - other.y;
        float dz = this.z - other.z;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public Vector3f clone() {
        return new Vector3f(x, y, z);
    }

    public boolean equals(Vector3f other) {
         float eps = 1e-7f;
        return Math.abs(x - other.x) < eps && Math.abs(y - other.y) < eps && Math.abs(z - other.z) < eps;
    }

    public int hashCode() {
        return Objects.hash(x, y, z);
    }

}
