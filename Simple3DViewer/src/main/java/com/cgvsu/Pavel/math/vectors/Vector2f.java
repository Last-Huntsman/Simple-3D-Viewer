package com.cgvsu.Pavel.math.vectors;

import java.util.Objects;

/**
 * Класс Vector2X для работы с двухмерными векторами.
 */
public final class Vector2f implements Vector<Vector2f> {
    private final float x;
    private final float y;

    /**
     *
     */
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public float z() {
        return 0;
    }

    @Override
    public float w() {
        return 0;
    }

    // Реализация методов интерфейса Vector2X
    @Override
    public Vector2f add(Vector2f v2) {
        return new Vector2f(this.x + v2.x, this.y + v2.y);
    }

    @Override
    public Vector2f subtract(Vector2f v2) {
        return new Vector2f(this.x - v2.x, this.y - v2.y);
    }

    @Override
    public Vector2f scale(float scalar) {
        return new Vector2f(this.x * scalar, this.y * scalar);
    }

    @Override
    public Vector2f divide(float scalar) {
        if (scalar == 0) {
            throw new ArithmeticException("Vector2X.divide: деление на ноль невозможно.");
        }
        return new Vector2f(this.x / scalar, this.y / scalar);
    }

    @Override
    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    @Override
    public Vector2f normalize() {
        float length = length();
        if (length == 0) {
            throw new ArithmeticException("Vector2X.normalize: длина вектора равна нулю, нормализация невозможна.");
        }
        return divide(length);
    }

    @Override
    public float dotProduct(Vector2f v2) {
        return this.x * v2.x + this.y * v2.y;
    }

    @Override
    public String toString() {
        return "Vector2X{" + "x=" + x + ", y=" + y + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Vector2f vector2FX = (Vector2f) obj;
        return Math.abs(this.x - vector2FX.x) < 1e-6 && Math.abs(this.y - vector2FX.y) < 1e-6;
    }

    @Override
    public Vector2f clone() {
        return new Vector2f(x, y);
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
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
