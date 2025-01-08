package com.cgvsu.math.Baricentrics_Triangle;


import com.cgvsu.math.vectors.Vector3f;

// Класс для представления барицентрических координат точки внутри треугольника.
public class Barycentric {

    // Барицентрические координаты точки (лямбда1, лямбда2, лямбда3).
    private final double lambda1;
    private final double lambda2;
    private final double lambda3;

    // Флаг, указывающий, находится ли точка внутри треугольника.
    private final boolean inside;

    // Конструктор для инициализации барицентрических координат.
    public Barycentric(final double lambda1, final double lambda2, final double lambda3) {

        // Проверка, что сумма барицентрических координат равна 1.
        final double sum = lambda1 + lambda2 + lambda3;
        if (!Utils_for_trianglerasterisation.equals(sum, 1)) {
            throw new IllegalArgumentException("Coordinates are not normalized");
            // Исключение выбрасывается, если координаты не нормализованы.
        }

        // Инициализация координат.
        this.lambda1 = lambda1;
        this.lambda2 = lambda2;
        this.lambda3 = lambda3;

        // Вычисление, находится ли точка внутри треугольника.
        this.inside = computeInside();
    }

    // Геттер для лямбда1 (первая барицентрическая координата).
    public double getLambda1() {
        return lambda1;
    }

    // Геттер для лямбда2 (вторая барицентрическая координата).
    public double getLambda2() {
        return lambda2;
    }

    // Геттер для лямбда3 (третья барицентрическая координата).
    public double getLambda3() {
        return lambda3;
    }

    // Метод для проверки, находится ли точка внутри треугольника.
    private boolean computeInside() {
        // Для нахождения точки внутри треугольника все барицентрические координаты
        // должны быть больше или равны нулю.
        final boolean f1 = Utils_for_trianglerasterisation.moreThan(lambda1, 0); // Проверка лямбда1 >= 0.
        final boolean f2 = Utils_for_trianglerasterisation.moreThan(lambda2, 0); // Проверка лямбда2 >= 0.
        final boolean f3 = Utils_for_trianglerasterisation.moreThan(lambda3, 0); // Проверка лямбда3 >= 0.

        return f1 && f2 && f3; // Возвращает true, если все координаты >= 0.
    }

    // Геттер для флага inside.
    // Определяет, принадлежит ли точка треугольнику.
    public boolean isInside() {
        return inside;
    }

    public Vector3f interpolate(Vector3f p1, Vector3f p2, Vector3f normal3) {
        return new Vector3f(
                (float) (lambda1 * p1.x + lambda2 * p2.x + lambda3 * normal3.x),
                (float) (lambda1 * p1.y + lambda2 * p2.y + lambda3 * normal3.y),
                (float) (lambda1 * p1.z + lambda2 * p2.z + lambda3 * normal3.z)
        );
    }

}
