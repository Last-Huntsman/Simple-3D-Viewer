package com.cgvsu.Egor.triangle_rasterisation.rasterisers;


import com.cgvsu.Egor.triangle_rasterisation.color.Texture;
import com.cgvsu.Egor.triangle_rasterisation.math.Barycentric;
import com.cgvsu.Egor.triangle_rasterisation.math.Triangle;
import com.cgvsu.Egor.triangle_rasterisation.math.Utils;
import javafx.geometry.Point2D;
import javafx.scene.image.PixelWriter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

// Класс для растеризации треугольников на основе метода сканирования строк (scanline).
public class TriangleRasterisator {

    // Интерфейс для записи пикселей на экран (в данном случае JavaFX PixelWriter).
    private PixelWriter pixelWriter;

    // Конструктор для инициализации PixelWriter.
    public TriangleRasterisator(PixelWriter pixelWriter) {
        this.pixelWriter = pixelWriter;
    }

    // Геттер для PixelWriter.
    public PixelWriter getPixelWriter() {
        return pixelWriter;
    }

    // Сеттер для изменения PixelWriter.
    public void setPixelWriter(PixelWriter pixelWriter) {
        this.pixelWriter = pixelWriter;
    }

    // Метод для сортировки вершин треугольника по оси Y, затем по оси X.
    private List<Point2D> sortedVertices(final Triangle t) {
        final List<Point2D> vertices = new ArrayList<>();
        vertices.add(t.getPoint1());
        vertices.add(t.getPoint2());
        vertices.add(t.getPoint3());

        // Сортировка: сначала по Y, затем по X (для одинаковых Y).
        vertices.sort(Comparator.comparing(Point2D::getY).thenComparing(Point2D::getX));
        return vertices;
    }

    // Метод для отрисовки "плоского" треугольника (верхняя или нижняя грани параллельны оси X).
    private void drawFlat(final Triangle t, final Point2D lone, final Point2D flat1, final Point2D flat2) {
        final double lx = lone.getX(); // X-координата "одиночной" вершины.
        final double ly = lone.getY(); // Y-координата "одиночной" вершины.

        // Разница в координатах между одиночной вершиной и двумя другими.
        final double deltaFlatX1 = flat1.getX() - lx;
        final double deltaFlatY1 = flat1.getY() - ly;

        final double deltaFlatX2 = flat2.getX() - lx;
        final double deltaFlatY2 = flat2.getY() - ly;

        // Угловые коэффициенты для линий.
        double deltaX1 = deltaFlatX1 / deltaFlatY1;
        double deltaX2 = deltaFlatX2 / deltaFlatY2;

        final double flatY = flat1.getY(); // Общая Y-координата для двух "плоских" вершин.
        if (Utils.moreThan(ly, flatY)) { // Если это "нижний" треугольник.
            drawBottom(t, lone, flatY, deltaX1, deltaX2);
        } else { // Если это "верхний" треугольник.
            drawTop(t, lone, flatY, deltaX1, deltaX2);
        }
    }

    // Метод для отрисовки "верхнего" плоского треугольника.
    private void drawTop(final Triangle t, final Point2D v, final double maxY, final double dx1, final double dx2) {
        double x1 = v.getX(); // Начальная X-координата первой линии.
        double x2 = x1;       // Начальная X-координата второй линии.

        // Проход по строкам от вершины до максимальной Y-координаты.
        for (int y = (int) v.getY(); y <= maxY; y++) {
            drawHLine(t, (int) x1, (int) x2, y); // Отрисовка горизонтальной линии.

            x1 += dx1; // Сдвиг первой линии вправо.
            x2 += dx2; // Сдвиг второй линии вправо.
        }
    }

    // Метод для отрисовки "нижнего" плоского треугольника.
    private void drawBottom(final Triangle t, final Point2D v, final double minY, final double dx1, final double dx2) {
        double x1 = v.getX(); // Начальная X-координата первой линии.
        double x2 = x1;       // Начальная X-координата второй линии.

        // Проход по строкам от вершины до минимальной Y-координаты.
        for (int y = (int) v.getY(); y > minY; y--) {
            drawHLine(t, (int) x1, (int) x2, y); // Отрисовка горизонтальной линии.

            x1 -= dx1; // Сдвиг первой линии влево.
            x2 -= dx2; // Сдвиг второй линии влево.
        }
    }

    // Метод для отрисовки одной горизонтальной линии между двумя X-координатами.
    private void drawHLine(final Triangle t, final int x1, final int x2, final int y) {

        // Проход по пикселям от x1 до x2.
        for (int x = (int) x1; x <= x2; x++) {
            final Barycentric b; // Барицентрические координаты текущего пикселя.
            try {
                b = t.barycentrics(new Point2D(x, y)); // Вычисление барицентрических координат.
            } catch (Exception e) {
                continue; // Если пиксель выходит за пределы треугольника, пропускаем.
            }

            if (!b.isInside()) {
                continue; // Проверка: пиксель внутри треугольника.
            }

            // Установка цвета пикселя с использованием текстуры.
            pixelWriter.setColor(x, y, t.getTexture().get(b).convertToJFXColor());
        }
    }

    // Основной метод для отрисовки треугольника.
    public void draw(Point2D p1, Point2D p2, Point2D p3, Texture texture) {
        Objects.requireNonNull(p1);
        Objects.requireNonNull(p2);
        Objects.requireNonNull(p3);

        // Создание треугольника с вершинами и текстурой.
        Triangle t = new Triangle(p1, p2, p3, texture);

        // Сортировка вершин треугольника по Y (и X для одинаковых Y).
        List<Point2D> vertices = sortedVertices(t);

        Point2D v1 = vertices.get(0); // Нижняя вершина.
        Point2D v2 = vertices.get(1); // Средняя вершина.
        Point2D v3 = vertices.get(2); // Верхняя вершина.

        double x1 = v1.getX();
        double y1 = v1.getY();

        double x2 = v2.getX();
        double y2 = v2.getY();

        double x3 = v3.getX();
        double y3 = v3.getY();

        // Проверка: треугольник уже "плоский" (верхний или нижний).
        if (Utils.equals(y2, y3)) {
            drawFlat(t, v1, v2, v3); // Нижний треугольник.
            return;
        }

        if (Utils.equals(y1, y2)) {
            drawFlat(t, v3, v1, v2); // Верхний треугольник.
            return;
        }

        // Разделение треугольника на два "плоских" по средней вершине.
        final double x4 = x1 + ((y2 - y1) / (y3 - y1)) * (x3 - x1); // X-координата разделяющей точки.
        final Point2D v4 = new Point2D(x4, v2.getY());

        // Выбор порядка отрисовки в зависимости от положения разделяющей точки.
        if (Utils.moreThan(x4, x2)) {
            drawFlat(t, v1, v2, v4); // Первая часть треугольника.
            drawFlat(t, v3, v2, v4); // Вторая часть треугольника.
        } else {
            drawFlat(t, v1, v4, v2); // Первая часть треугольника.
            drawFlat(t, v3, v4, v2); // Вторая часть треугольника.
        }
    }
}

