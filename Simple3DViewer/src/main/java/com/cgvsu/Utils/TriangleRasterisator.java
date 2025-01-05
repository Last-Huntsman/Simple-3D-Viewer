package com.cgvsu.Utils;

import com.cgvsu.math.Baricentrics_Triangle.Utils_for_trianglerasterisation;
import com.cgvsu.Utils.color_for_triangle_rasterisation.Texture;
import com.cgvsu.math.Baricentrics_Triangle.Triangle;
import javafx.geometry.Point2D;
import javafx.scene.image.PixelWriter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

// Класс для растеризации треугольников с учетом глубины (z-буфера).
public class TriangleRasterisator {

    // Интерфейс для записи пикселей на экран.
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

    // Сортировка вершин треугольника по оси Y (если равны — по оси X).
    private List<Point2D> sortedVertices(final Triangle t) {
        final List<Point2D> vertices = new ArrayList<>();
        vertices.add(t.getPoint1());
        vertices.add(t.getPoint2());
        vertices.add(t.getPoint3());

        // Сортируем вершины: сначала по Y, затем по X.
        vertices.sort(Comparator.comparing(Point2D::getY).thenComparing(Point2D::getX));
        return vertices;
    }

    // Отрисовка "плоского" треугольника (верхняя или нижняя стороны параллельны оси X).
    private void drawFlat(final Triangle t, final Point2D lone, final Point2D flat1, final Point2D flat2,
                          final double zLone, final double zFlat1, final double zFlat2, final double[][] zBuffer) {
        // Координаты "одинокой" вершины (которая отличается по Y).
        final double lx = lone.getX();
        final double ly = lone.getY();

        // Разницы по X и Y для двух других вершин.
        final double deltaFlatX1 = flat1.getX() - lx;
        final double deltaFlatY1 = flat1.getY() - ly;
        final double deltaZ1 = zFlat1 - zLone;

        final double deltaFlatX2 = flat2.getX() - lx;
        final double deltaFlatY2 = flat2.getY() - ly;
        final double deltaZ2 = zFlat2 - zLone;

        // Вычисляем угловые коэффициенты для X и Z.
        double deltaX1 = deltaFlatX1 / deltaFlatY1;
        double deltaX2 = deltaFlatX2 / deltaFlatY2;
        double deltaZ1Normalized = deltaZ1 / deltaFlatY1;
        double deltaZ2Normalized = deltaZ2 / deltaFlatY2;

        final double flatY = flat1.getY(); // Y-координата "плоских" вершин.
        if (Utils_for_trianglerasterisation.moreThan(ly, flatY)) {
            // Это нижний треугольник.
            drawBottom(t, lone, flatY, deltaX1, deltaX2, deltaZ1Normalized, deltaZ2Normalized, zLone, zBuffer);
        } else {
            // Это верхний треугольник.
            drawTop(t, lone, flatY, deltaX1, deltaX2, deltaZ1Normalized, deltaZ2Normalized, zLone, zBuffer);
        }
    }

    // Отрисовка верхнего плоского треугольника.
    private void drawTop(final Triangle t, final Point2D v, final double maxY, final double dx1, final double dx2,
                         final double dz1, final double dz2, final double zStart, final double[][] zBuffer) {
        double x1 = v.getX(); // Начальная X-координата первой стороны.
        double x2 = x1;       // Начальная X-координата второй стороны.
        double z1 = zStart;   // Начальная глубина первой стороны.
        double z2 = zStart;   // Начальная глубина второй стороны.

        // Проходим по строкам от начальной Y до верхней Y.
        for (int y = (int) v.getY(); y <= maxY; y++) {
            drawHLine(t, (int) x1, (int) x2, y, z1, z2, zBuffer);

            // Сдвигаем координаты и глубину для следующей строки.
            x1 += dx1;
            x2 += dx2;
            z1 += dz1;
            z2 += dz2;
        }
    }

    // Отрисовка нижнего плоского треугольника.
    private void drawBottom(final Triangle t, final Point2D v, final double minY, final double dx1, final double dx2,
                            final double dz1, final double dz2, final double zStart, final double[][] zBuffer) {
        double x1 = v.getX(); // Начальная X-координата первой стороны.
        double x2 = x1;       // Начальная X-координата второй стороны.
        double z1 = zStart;   // Начальная глубина первой стороны.
        double z2 = zStart;   // Начальная глубина второй стороны.

        // Проходим по строкам от начальной Y до нижней Y.
        for (int y = (int) v.getY(); y > minY; y--) {
            drawHLine(t, (int) x1, (int) x2, y, z1, z2, zBuffer);

            // Сдвигаем координаты и глубину для следующей строки.
            x1 -= dx1;
            x2 -= dx2;
            z1 -= dz1;
            z2 -= dz2;
        }
    }

    // Отрисовка одной горизонтальной линии между двумя X-координатами.
    private void drawHLine(final Triangle t, final int x1, final int x2, final int y,
                           final double z1, final double z2, final double[][] zBuffer) {
        double currentZ = z1; // Начальная глубина.
        double dz = (z2 - z1) / (x2 - x1); // Интерполяция глубины.

        // Проходим по пикселям от x1 до x2.
        for (int x = x1; x <= x2; x++) {
            if (x >= 0 && x < zBuffer.length && y >= 0 && y < zBuffer[0].length) {
                if (currentZ > zBuffer[x][y]) {
                    // Если пиксель ближе, чем текущий в z-буфере, обновляем.
                    zBuffer[x][y] = currentZ;
                    pixelWriter.setColor(x, y, t.getTexture().get(t.barycentrics( new Point2D(x, y))).convertToJFXColor());
                }
            }
            currentZ += dz; // Увеличиваем глубину.
        }
    }

    // Основной метод для отрисовки треугольника.
    public void draw(Point2D p1, double z1, Point2D p2, double z2, Point2D p3, double z3,
                     Texture texture, double[][] zBuffer) {
        Objects.requireNonNull(p1);
        Objects.requireNonNull(p2);
        Objects.requireNonNull(p3);


        // Создаем треугольник из вершин и текстуры.
        Triangle t = new Triangle(p1, p2, p3, texture);

        // Сортируем вершины треугольника по Y (и X для одинаковых Y).
        List<Point2D> vertices = sortedVertices(t);
        Point2D v1 = vertices.get(0);
        Point2D v2 = vertices.get(1);
        Point2D v3 = vertices.get(2);

        // Определяем глубину для каждой вершины.
        double depth1 = v1.equals(p1) ? z1 : v1.equals(p2) ? z2 : z3;
        double depth2 = v2.equals(p1) ? z1 : v2.equals(p2) ? z2 : z3;
        double depth3 = v3.equals(p1) ? z1 : v3.equals(p2) ? z2 : z3;

        // Если треугольник уже "плоский", обрабатываем сразу.
        if (Utils_for_trianglerasterisation.equals(v2.getY(), v3.getY())) {
            drawFlat(t, v1, v2, v3, depth1, depth2, depth3, zBuffer);
            return;
        }

        if (Utils_for_trianglerasterisation.equals(v1.getY(), v2.getY())) {
            drawFlat(t, v3, v1, v2, depth3, depth1, depth2, zBuffer);
            return;
        }

        // Разделяем треугольник на два "плоских".
        double x4 = v1.getX() + ((v2.getY() - v1.getY()) / (v3.getY() - v1.getY())) * (v3.getX() - v1.getX());
        double z4 = depth1 + ((v2.getY() - v1.getY()) / (v3.getY() - v1.getY())) * (depth3 - depth1);
        Point2D v4 = new Point2D(x4, v2.getY());

        // Отрисовываем две части треугольника.
        if (Utils_for_trianglerasterisation.moreThan(x4, v2.getX())) {
            drawFlat(t, v1, v2, v4, depth1, depth2, z4, zBuffer);
            drawFlat(t, v3, v2, v4, depth3, depth2, z4, zBuffer);
        } else {
            drawFlat(t, v1, v4, v2, depth1, z4, depth2, zBuffer);
            drawFlat(t, v3, v4, v2, depth3, z4, depth2, zBuffer);
        }
    }
}
