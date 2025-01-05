package com.cgvsu.triangle_rasterisation.color;

import com.cgvsu.triangle_rasterisation.Baricentrics_Triangle.Barycentric;
import com.cgvsu.triangle_rasterisation.Baricentrics_Triangle.Utils;
import javafx.scene.paint.Color;

import java.util.Objects;

// Класс для создания текстуры градиента, которая меняется по трем точкам.
public class GradientTexture implements Texture {

    // Вспомогательный класс для хранения трех цветов, которые будут использоваться для градиента.
    private class ThreePointGradient {
        private final ColorRGB color1;  // Первый цвет градиента
        private final ColorRGB color2;  // Второй цвет градиента
        private final ColorRGB color3;  // Третий цвет градиента

        // Конструктор для инициализации трех цветов.
        public ThreePointGradient(final ColorRGB color1, final ColorRGB color2, final ColorRGB color3) {
            // Проверка, что все цвета переданы.
            Objects.requireNonNull(color1);
            Objects.requireNonNull(color2);
            Objects.requireNonNull(color3);

            this.color1 = color1;
            this.color2 = color2;
            this.color3 = color3;
        }

        // Методы для получения каждого из цветов градиента.
        public ColorRGB getColor1() {
            return color1;
        }

        public ColorRGB getColor2() {
            return color2;
        }

        public ColorRGB getColor3() {
            return color3;
        }
    }

    // Объект для хранения информации о трехцветном градиенте.
    private final ThreePointGradient gradient;

    // Конструктор для создания градиента из объектов ColorRGB.
    public GradientTexture(final ColorRGB c1, final ColorRGB c2, final ColorRGB c3) {
        // Проверка на null для каждого из цветов.
        Objects.requireNonNull(c1);
        Objects.requireNonNull(c2);
        Objects.requireNonNull(c3);

        this.gradient = new ThreePointGradient(c1, c2, c3);
    }

    // Конструктор для создания градиента из объектов JavaFX Color.
    public GradientTexture(final Color c1, final Color c2, final Color c3) {
        // Проверка на null для каждого из цветов.
        Objects.requireNonNull(c1);
        Objects.requireNonNull(c2);
        Objects.requireNonNull(c3);

        // Преобразуем объекты Color в объекты ColorRGB для хранения.
        this.gradient = new ThreePointGradient(new ColorRGB(c1), new ColorRGB(c2), new ColorRGB(c3));
    }

    // Метод для вычисления значения красного канала на основе барицентрических координат.
    private double red(final Barycentric b) {
        // Весовые коэффициенты для каждого из цветов.
        final double r1 = b.getLambda1() * gradient.getColor1().getRedChannel();
        final double r2 = b.getLambda2() * gradient.getColor2().getRedChannel();
        final double r3 = b.getLambda3() * gradient.getColor3().getRedChannel();

        // Суммируем и ограничиваем результат в диапазоне от 0 до 1.
        return Utils.confined(0, r1 + r2 + r3, 1);
    }

    // Метод для вычисления значения зеленого канала на основе барицентрических координат.
    private double green(final Barycentric b) {
        final double g1 = b.getLambda1() * gradient.getColor1().getGreenChannel();
        final double g2 = b.getLambda2() * gradient.getColor2().getGreenChannel();
        final double g3 = b.getLambda3() * gradient.getColor3().getGreenChannel();

        return Utils.confined(0, g1 + g2 + g3, 1);
    }

    // Метод для вычисления значения синего канала на основе барицентрических координат.
    private double blue(final Barycentric b) {
        final double b1 = b.getLambda1() * gradient.getColor1().getBlueChannel();
        final double b2 = b.getLambda2() * gradient.getColor2().getBlueChannel();
        final double b3 = b.getLambda3() * gradient.getColor3().getBlueChannel();

        return Utils.confined(0, b1 + b2 + b3, 1);
    }

    // Метод для вычисления значения альфа-канала (прозрачности) на основе барицентрических координат.
    private double opacity(final Barycentric b) {
        final double o1 = b.getLambda1() * gradient.getColor1().getAlphaChannel();
        final double o2 = b.getLambda2() * gradient.getColor2().getAlphaChannel();
        final double o3 = b.getLambda3() * gradient.getColor3().getAlphaChannel();

        return Utils.confined(0, o1 + o2 + o3, 1);
    }

    // Реализация метода из интерфейса Texture для получения цвета на основе барицентрических координат.
    @Override
    public ColorRGB get(final Barycentric b) {
        // Проверка на null для барицентрических координат.
        Objects.requireNonNull(b);

        // Возвращаем новый цвет, вычисленный на основе барицентрических координат.
        return new ColorRGB(red(b), green(b), blue(b), opacity(b));
    }
}
