package com.cgvsu.Utils.color_for_triangle_rasterisation;

import com.cgvsu.math.Baricentrics_Triangle.Utils_for_trianglerasterisation;
import javafx.scene.paint.Color;

// Класс для представления цвета в формате RGB с альфа-каналом.
public class ColorRGB {

    // Компоненты цвета: красный, зеленый, синий и прозрачность (альфа).
    private final double red;
    private final double green;
    private final double blue;
    private final double alpha;

    // Конструктор для создания цвета на основе значений компонентов.
    // Все значения ограничиваются диапазоном от 0 до 1.
    public ColorRGB(final double red, final double green, final double blue, final double alpha) {
        // Используется метод Utils_for_trianglerasterisation.confined для гарантии, что значения находятся в диапазоне.
        this.red = Utils_for_trianglerasterisation.confined(0, red, 1);
        this.green = Utils_for_trianglerasterisation.confined(0, green, 1);
        this.blue = Utils_for_trianglerasterisation.confined(0, blue, 1);
        this.alpha = Utils_for_trianglerasterisation.confined(0, alpha, 1);
    }

    // Конструктор для создания цвета на основе объекта JavaFX Color.
    public ColorRGB(final Color color) {
        // Все значения из объекта Color также ограничиваются диапазоном [0, 1].
        this.red = Utils_for_trianglerasterisation.confined(0, color.getRed(), 1);
        this.green = Utils_for_trianglerasterisation.confined(0, color.getGreen(), 1);
        this.blue = Utils_for_trianglerasterisation.confined(0, color.getBlue(), 1);
        this.alpha = Utils_for_trianglerasterisation.confined(0, color.getOpacity(), 1);
    }

    // Получить значение красного канала.
    public double getRedChannel() {
        return red;
    }

    // Получить значение зеленого канала.
    public double getGreenChannel() {
        return green;
    }

    // Получить значение синего канала.
    public double getBlueChannel() {
        return blue;
    }

    // Получить значение альфа-канала (прозрачности).
    public double getAlphaChannel() {
        return alpha;
    }

    // Метод для преобразования цвета в объект JavaFX Color.
    public Color convertToJFXColor() {
        return new Color(red, green, blue, alpha);
    }
}
