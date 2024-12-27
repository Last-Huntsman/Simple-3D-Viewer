package com.cgvsu.render_engine;

import com.cgvsu.Egor.triangle_rasterisation.color.GradientTexture;
import com.cgvsu.Egor.triangle_rasterisation.color.MonotoneTexture;
import com.cgvsu.Egor.triangle_rasterisation.rasterisers.TriangleRasterisator;
import com.cgvsu.Pavel.math.vectors.Vector3f;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import javax.vecmath.Point2f;
import java.util.ArrayList;

import static com.cgvsu.render_engine.GraphicConveyor.vertexToPoint;

public class PictureProcess {

    /**
     * Рисует треугольник с использованием заданного графического контекста и координат вершин.
     *
     * @param gc графический контекст для рисования
     * @param p1 первая вершина треугольника
     * @param p2 вторая вершина треугольника
     * @param p3 третья вершина треугольника
     */
    public static void showTriangle(GraphicsContext gc, Point2D p1, Point2D p2, Point2D p3) {
        // Создание растеризатора для отрисовки треугольника
        TriangleRasterisator tr = new TriangleRasterisator(gc.getPixelWriter());

        // Создание однотонной текстуры (используется для закраски треугольника)
        MonotoneTexture mt = new MonotoneTexture(Color.BLACK);

        // Отрисовка треугольника с помощью растеризатора
        tr.draw(p1, p2, p3, mt);
    }

    /**
     * Растеризует многоугольник с использованием z-буфера для устранения наложения.
     *
     * @param graphicsContext графический контекст для рисования
     * @param vertices        список вершин многоугольника
     * @param zBuffer         z-буфер для управления глубиной
     * @param width           ширина области отрисовки
     * @param height          высота области отрисовки
     */
    public static void rasterizePolygon(
            final GraphicsContext graphicsContext,
            final ArrayList<Vector3f> vertices,
            final double[][] zBuffer,
            final int width,
            final int height) {

        // Преобразование координат вершин многоугольника
        int nVertices = vertices.size();

        // Обход рёбер многоугольника
        for (int i = 0; i < nVertices; i++) {
            // Берём текущую вершину и следующую по индексу (циклически)
            Vector3f v1 = vertices.get(i);
            Vector3f v2 = vertices.get((i + 1) % nVertices);

            // Преобразование в экранные координаты
            Point2f p1 = vertexToPoint(v1, width, height);
            Point2f p2 = vertexToPoint(v2, width, height);

            // Растровизация линии между двумя вершинами с учётом глубины
            drawLineWithZBuffer(graphicsContext, p1, p2, v1.z, v2.z, zBuffer);
        }
    }

    /**
     * Растровизация линии между двумя точками с использованием алгоритма Брезенхема и z-буфера.
     *
     * @param graphicsContext графический контекст для рисования
     * @param p1              первая точка линии
     * @param p2              вторая точка линии
     * @param z1              глубина первой точки
     * @param z2              глубина второй точки
     * @param zBuffer         z-буфер для управления глубиной
     */
    private static void drawLineWithZBuffer(
            final GraphicsContext graphicsContext,
            final Point2f p1,
            final Point2f p2,
            final double z1,
            final double z2,
            final double[][] zBuffer) {

        // Преобразование координат в целочисленные значения
        int x1 = Math.round(p1.x);
        int y1 = Math.round(p1.y);
        int x2 = Math.round(p2.x);
        int y2 = Math.round(p2.y);

        // Инициализация параметров алгоритма Брезенхема
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;

        int err = dx - dy;
        double dz = (z2 - z1) / Math.max(dx, dy); // Изменение глубины на шаг
        double currentZ = z1; // Начальная глубина

        // Основной цикл алгоритма Брезенхема
        while (true) {
            // Проверка координат на выход за границы
            if (x1 >= 0 && x1 < zBuffer.length && y1 >= 0 && y1 < zBuffer[0].length) {
                // Обновление z-буфера и рисование точки, если она ближе
                if (currentZ > zBuffer[x1][y1]) {
                    zBuffer[x1][y1] = currentZ;
                    graphicsContext.strokeRect(x1, y1, 1, 1);
                }
            }

            // Условие завершения отрисовки линии
            if (x1 == x2 && y1 == y2) break;

            // Корректировка ошибки и координат
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }

            // Обновление текущей глубины
            currentZ += dz;
        }
    }
}
