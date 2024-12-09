package com.cgvsu.math.vectors;

import java.util.Objects;

/**
 * Класс Vector3X для работы с трехмерными векторами.
 */
public final class Vector3f implements Vector<Vector3f> {
    public final float x;
    public final float y;
    public final float z;

    /**
     *
     */
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public float w() {
        return 0;
    }


    // Реализация методов интерфейса Vector
    @Override
    public Vector3f add(Vector3f v2) {
        return new Vector3f(this.x + v2.x, this.y + v2.y, this.z + v2.z);
    }

    @Override
    public Vector3f subtract(Vector3f v2) {
        return new Vector3f(this.x - v2.x, this.y - v2.y, this.z - v2.z);
    }


//    public final void sub(Tuple3f var1, Tuple3f var2) {
//        this.x = var1.x - var2.x;
//        this.y = var1.y - var2.y;
//        this.z = var1.z - var2.z;
//    }

    @Override
    public Vector3f scale(float scalar) {
        return new Vector3f(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    @Override
    public Vector3f divide(float scalar) {
        if (scalar == 0) {
            throw new ArithmeticException("Vector3X3.divide: деление на ноль невозможно.");
        }
        return new Vector3f(this.x / scalar, this.y / scalar, this.z / scalar);
    }

    @Override
    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public Vector3f normalize() {
        float length = length();
        if (length == 0) {
            throw new ArithmeticException("Vector3f.normalize: длина вектора равна нулю, нормализация невозможна.");
        }
        return divide(length);
    }

    @Override
    public float dotProduct(Vector3f v2) {
        return this.x * v2.x + this.y * v2.y + this.z * v2.z;
    }
    //    public final float dot(javax.vecmath.Vector3f var1) {
//        return this.x * var1.x + this.y * var1.y + this.z * var1.z;
//    }

    // Метод для векторного произведения
    public Vector3f crossProduct(Vector3f v2) {
        return new Vector3f(
                this.y * v2.z - this.z * v2.y,
                this.z * v2.x - this.x * v2.z,
                this.x * v2.y - this.y * v2.x
        );
    }

    //    public final void cross(javax.vecmath.Vector3f var1, javax.vecmath.Vector3f var2) {
//        float var3 = var1.y * var2.z - var1.z * var2.y;
//        float var4 = var2.x * var1.z - var2.z * var1.x;
//        this.z = var1.x * var2.y - var1.y * var2.x;
//        this.x = var3;
//        this.y = var4;
//    }

    @Override
    public String toString() {
        return "Vector3X3{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Vector3f vector3FX = (Vector3f) obj;
        return Math.abs(this.x - vector3FX.x) < 1e-6 &&
                Math.abs(this.y - vector3FX.y) < 1e-6 &&
                Math.abs(this.z - vector3FX.z) < 1e-6;
    }

    @Override
    public Vector3f clone() {
        return new Vector3f(x, y, z);
    }




    @Override
    public float x() {
        return x;
    }

    @Override
    public float y() {
        return y;
    }

    @Override
    public float z() {
        return z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

}
