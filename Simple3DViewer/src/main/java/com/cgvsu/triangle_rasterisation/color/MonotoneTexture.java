package com.cgvsu.triangle_rasterisation.color;

import com.cgvsu.triangle_rasterisation.Baricentrics_Triangle.Barycentric;
import javafx.scene.paint.Color;

import java.util.Objects;

// Класс для создания однотонной текстуры, которая имеет одинаковый цвет для всего треугольника.
public class MonotoneTexture implements Texture {

    // Хранит цвет, который будет использоваться для всей текстуры.
    private final ColorRGB color;

    // Конструктор для создания однотонной текстуры из объекта ColorRGB.
    public MonotoneTexture(final ColorRGB color) {
        // Проверка на null для переданного цвета.
        Objects.requireNonNull(color);

        // Инициализация поля color.
        this.color = color;
    }

    // Конструктор для создания однотонной текстуры из объекта Color (JavaFX).
    public MonotoneTexture(final Color color) {
        // Проверка на null для переданного цвета.
        Objects.requireNonNull(color);

        // Преобразуем объект Color в ColorRGB.
        this.color = new ColorRGB(color);
    }

    // Реализация метода из интерфейса Texture, возвращающий один и тот же цвет для каждой точки.
    @Override
    public ColorRGB get(final Barycentric b) {
        // Проверка на null для барицентрических координат.
        Objects.requireNonNull(b);

        // Возвращаем однотонный цвет, который не зависит от барицентрических координат.
        return color;
    }
}
