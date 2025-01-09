package com.cgvsu.Utils;

import com.cgvsu.math.vectors.Vector3f;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import static com.cgvsu.render_engine.GraphicConveyor.vertexToPoint;


public class Rendering_the_grid {

    /**
     * Растеризует многоугольник с использованием z-буфера для устранения наложения.
     *
     * @param graphicsContext графический контекст для рисования
     * @param vertices        список вершин многоугольника
     * @param zBuffer         z-буфер для управления глубиной
     */
    public static void DrawGrid(final GraphicsContext graphicsContext, final ArrayList<Vector3f> vertices, final double[][] zBuffer) {

        // Преобразование координат вершин многоугольника
        int nVertices = vertices.size();

        // Обход рёбер многоугольника
        for (int i = 0; i < nVertices; i++) {
            // Берём текущую вершину и следующую по индексу (циклически)
            Vector3f v1 = vertices.get(i);
            Vector3f v2 = vertices.get((i + 1) % nVertices);

            // Преобразование в экранные координаты
            Point2D p1 = vertexToPoint(v1);
            Point2D p2 = vertexToPoint(v2);

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
            final Point2D p1,
            final Point2D p2,
            double z1,
            double z2,
            final double[][] zBuffer) {


        // Преобразование координат в целочисленные значения
        int x1 = (int) Math.round(p1.getX());
        int y1 = (int) Math.round(p1.getY());
        int x2 = (int) Math.round(p2.getX());
        int y2 = (int) Math.round(p2.getY());

        // Инициализация параметров алгоритма Брезенхема
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;

        int err = dx - dy;
        double dz = ((z2 - z1) / Math.max(dx, dy))+0.00000001; // Изменение глубины на шаг
        double currentZ = z1; // Начальная глубина

        // Основной цикл алгоритма Брезенхема
        while (true) {
            // Проверка координат на выход за границы
            if (x1 >= 0 && x1 < zBuffer.length && y1 >= 0 && y1 < zBuffer[0].length) {
                // Обновление z-буфера и рисование точки, если она ближе
                if (currentZ > zBuffer[x1][y1]) {
                    zBuffer[x1][y1] = currentZ;
                    graphicsContext.setStroke(Color.BLACK);
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
