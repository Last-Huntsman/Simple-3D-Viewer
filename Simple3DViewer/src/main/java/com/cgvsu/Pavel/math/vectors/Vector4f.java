package com.cgvsu.Pavel.math.vectors;

import java.util.Objects;

/**
 * Класс Vector4X для работы с четырехмерными векторами.
 */
public final class Vector4f implements Vector<Vector4f> {
    private final float x;
    private final float y;
    private final float z;
    private final float w;

    /**
     *
     */
    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
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
    public float w() {
        return w;
    }

    // Реализация методов интерфейса Vector
    @Override
    public Vector4f add(Vector4f v2) {
        return new Vector4f(this.x + v2.x, this.y + v2.y, this.z + v2.z, this.w + v2.w);
    }

    @Override
    public Vector4f subtract(Vector4f v2) {
        return new Vector4f(this.x - v2.x, this.y - v2.y, this.z - v2.z, this.w - v2.w);
    }

    @Override
    public Vector4f scale(float scalar) {
        return new Vector4f(this.x * scalar, this.y * scalar, this.z * scalar, this.w * scalar);
    }

    @Override
    public Vector4f divide(float scalar) {
        if (scalar == 0) {
            throw new ArithmeticException("Vector4X.divide: деление на ноль невозможно.");
        }
        return new Vector4f(this.x / scalar, this.y / scalar, this.z / scalar, this.w / scalar);
    }

    @Override
    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    @Override
    public Vector4f normalize() {
        float length = length();
        if (length == 0) {
            throw new ArithmeticException("Vector4X.normalize: длина вектора равна нулю, нормализация невозможна.");
        }
        return divide(length);
    }

    @Override
    public float dotProduct(Vector4f v2) {
        return this.x * v2.x + this.y * v2.y + this.z * v2.z + this.w * v2.w;
    }

    @Override
    public String toString() {
        return "Vector4X{" + "x=" + x + ", y=" + y + ", z=" + z + ", w=" + w + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Vector4f vector = (Vector4f) obj;
        return Math.abs(this.x - vector.x) < 1e-6 &&
                Math.abs(this.y - vector.y) < 1e-6 &&
                Math.abs(this.z - vector.z) < 1e-6 &&
                Math.abs(this.w - vector.w) < 1e-6;
    }

    @Override
    public Vector4f clone() {
        return new Vector4f(x, y, z, w);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }

}
