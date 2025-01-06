package com.cgvsu.math.Baricentrics_Triangle;

import com.cgvsu.Utils.color_for_triangle_rasterisation.Texture;
import javafx.geometry.Point2D;

import java.util.Objects;

// Класс для представления треугольника в 2D пространстве.
// Также хранит текстуру для наложения на треугольник.
public class Triangle {

    // Вершины треугольника.
    private Point2D point1;
    private Point2D point2;
    private Point2D point3;

    // Текстура, связанная с треугольником.
    private Texture texture;

    // Конструктор для инициализации треугольника и его текстуры.
    public Triangle(Point2D point1, Point2D point2, Point2D point3, Texture texture) {

        // Проверка на null-значения для вершин и текстуры.
        Objects.requireNonNull(point1);
        Objects.requireNonNull(point2);
        Objects.requireNonNull(point3);
        Objects.requireNonNull(texture);

        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        this.texture = texture;
    }
    public Triangle(Point2D point1, Point2D point2, Point2D point3) {

        // Проверка на null-значения для вершин и текстуры.
        Objects.requireNonNull(point1);
        Objects.requireNonNull(point2);
        Objects.requireNonNull(point3);


        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
    }

    // Получить текстуру, связанную с треугольником.
    public Texture getTexture() {
        return texture;
    }

    // Установить новую текстуру для треугольника.
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    // Геттер для первой вершины треугольника.
    public Point2D getPoint1() {
        return point1;
    }

    // Геттер для второй вершины треугольника.
    public Point2D getPoint2() {
        return point2;
    }

    // Геттер для третьей вершины треугольника.
    public Point2D getPoint3() {
        return point3;
    }

    // Геттер для X-координаты третьей вершины.
    public double x3() {
        return point3.getX();
    }

    // Геттер для Y-координаты третьей вершины.
    public double y3() {
        return point3.getY();
    }

    // Метод для вычисления барицентрических координат точки относительно треугольника.
    public Barycentric barycentrics(final Point2D p) {

        // Получение координат точки.
        final double x = p.getX();
        final double y = p.getY();

        // Получение координат вершин треугольника.
        final double x1 = getPoint1().getX();
        final double y1 = getPoint1().getY();

        final double x2 = getPoint2().getX();
        final double y2 = getPoint2().getY();

        final double x3 = getPoint3().getX();
        final double y3 = getPoint3().getY();

        // Вычисление числителей для барицентрических координат.
        final double n1 = (y2 - y3) * (x - x3) + (x3 - x2) * (y - y3); // Числитель для лямбда1.
        final double n2 = (y3 - y1) * (x - x3) + (x1 - x3) * (y - y3); // Числитель для лямбда2.
        final double n3 = (y1 - y2) * (x - x1) + (x2 - x1) * (y - y1); // Числитель для лямбда3.

        // Вычисление знаменателя (общего для всех координат).
        final double d = 1 / ((y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3));

        // Вычисление барицентрических координат (лямбда1, лямбда2, лямбда3).
        final double l1 = n1 * d;
        final double l2 = n2 * d;
        final double l3 = n3 * d;

        // Возвращение объекта с вычисленными координатами.
        return new Barycentric(l1, l2, l3);
    }
}