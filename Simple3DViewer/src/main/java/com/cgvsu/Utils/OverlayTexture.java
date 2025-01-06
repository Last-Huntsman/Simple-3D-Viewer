package com.cgvsu.Utils;

import com.cgvsu.math.vectors.Vector2f;
import com.cgvsu.math.vectors.Vector3f;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.List;

import static com.cgvsu.render_engine.GraphicConveyor.vertexToPoint;

public class OverlayTexture {

    public static void draw(
            List<Vector3f> vertices,                 // Вершины треугольника (x, y, z)
            List<Vector2f> textureCoords,            // Текстурные координаты (u, v)
            Image texture,                          // Изображение текстуры
            double[][] zBuffer,                     // Z-буфер
            PixelWriter pixelWriter                 // PixelWriter для записи пикселей
    ) {
        // Проверка корректности входных данных
        if (vertices.size() != 3 || textureCoords.size() != 3) {
            throw new IllegalArgumentException("Должно быть ровно 3 вершины и 3 текстурные координаты.");
        }

        // Сортируем вершины по Y и если необходимо, по X
        vertices.sort((v1, v2) -> Double.compare(v1.getY(), v2.getY()));

        Vector3f v1 = vertices.get(0), v2 = vertices.get(1), v3 = vertices.get(2);
        Point2D t1 = vertexToPoint(vertices.get(0)), t2 = vertexToPoint(vertices.get(1)), t3 = vertexToPoint(vertices.get(2));

        // Пропускаем треугольники, лежащие на одной горизонтальной линии
        if (v1.getY() == v2.getY() && v1.getY() == v3.getY()) return;

        // Разделяем треугольник на верхний и нижний
        if (v2.getY() == v3.getY()) {
            drawFlatBottom(v1, v2, v3, t1, t2, t3, texture, zBuffer, pixelWriter);
        } else if (v1.getY() == v2.getY()) {
            drawFlatTop(v1, v2, v3, t1, t2, t3, texture, zBuffer, pixelWriter);
        } else {
            // Разбиваем треугольник на два плоских
            Vector3f v4 = interpolateVertex(v1, v2, v3);
            Point2D t4 = interpolateTexCoord(t1, t2, t3);

            drawFlatBottom(v1, v2, v4, t1, t2, t4, texture, zBuffer, pixelWriter);
            drawFlatTop(v2, v4, v3, t2, t4, t3, texture, zBuffer, pixelWriter);
        }
    }

    // Метод для растеризации нижнего плоского треугольника
    private static void drawFlatBottom(Vector3f v1, Vector3f v2, Vector3f v3,
                                       Point2D t1, Point2D t2, Point2D t3,
                                       Image texture, double[][] zBuffer, PixelWriter pixelWriter) {
        double invSlope1 = (v2.getX() - v1.getX()) / (v2.getY() - v1.getY());
        double invSlope2 = (v3.getX() - v1.getX()) / (v3.getY() - v1.getY());

        double xStart = v1.getX();
        double xEnd = v1.getX();

        for (int y = (int) Math.ceil(v1.getY()); y <= (int) Math.ceil(v2.getY()); y++) {
            drawHLine((int) Math.ceil(xStart), (int) Math.ceil(xEnd), y, v1, v2, v3, t1, t2, t3, texture, zBuffer, pixelWriter);
            xStart += invSlope1;
            xEnd += invSlope2;
        }
    }

    // Метод для растеризации верхнего плоского треугольника
    private static void drawFlatTop(Vector3f v1, Vector3f v2, Vector3f v3,
                                    Point2D t1, Point2D t2, Point2D t3,
                                    Image texture, double[][] zBuffer, PixelWriter pixelWriter) {
        double invSlope1 = (v1.getX() - v3.getX()) / (v1.getY() - v3.getY());
        double invSlope2 = (v2.getX() - v3.getX()) / (v2.getY() - v3.getY());

        double xStart = v3.getX();
        double xEnd = v3.getX();

        for (int y = (int) Math.ceil(v3.getY()); y > (int) Math.ceil(v1.getY()); y--) {
            drawHLine((int) Math.ceil(xStart), (int) Math.ceil(xEnd), y, v1, v2, v3, t1, t2, t3, texture, zBuffer, pixelWriter);
            xStart -= invSlope1;
            xEnd -= invSlope2;
        }
    }

    /// Метод для рисования горизонтальной линии
    private static void drawHLine(int xStart, int xEnd, int y,
                                  Vector3f v1, Vector3f v2, Vector3f v3,
                                  Point2D t1, Point2D t2, Point2D t3,
                                  Image texture, double[][] zBuffer, PixelWriter pixelWriter) {
        for (int x = xStart; x <= xEnd; x++) {
            // Вычисление барицентрических координат для текущего пикселя
            Point2D bary = getBarycentricCoordinates(v1, v2, v3, x, y);

            // Пропускаем пиксели, которые находятся вне треугольника
            if (bary.getX() < 0 || bary.getY() < 0 || bary.getX() + bary.getY() > 1) continue;

            // Интерполяция текстурных координат
            Point2D texCoord = interpolateTextureCoords(bary, t1, t2, t3);

            // Ограничиваем текстурные координаты в диапазоне [0, 1]
            texCoord = new Point2D(Math.min(Math.max(texCoord.getX(), 0), 1),
                    Math.min(Math.max(texCoord.getY(), 0), 1));

            // Интерполяция глубины
            double z = interpolateDepth(bary, v1.getZ(), v2.getZ(), v3.getZ());

            // Проверка индексов для zBuffer
            if (x >= 0 && x < zBuffer.length && y >= 0 && y < zBuffer[0].length) {
                // Если текущая глубина больше, чем в zBuffer, обновляем пиксель
                if (z > zBuffer[x][y]) {
                    zBuffer[x][y] = z;

                    // Получаем цвет из текстуры
                    Color color = getTextureColor(texture, texCoord);

                    // Записываем пиксель в изображение
                    pixelWriter.setColor(x, y, color);
                }
            }
        }
    }


    // Вспомогательные методы

    // Метод для интерполяции вершины для разбивки треугольника
    private static Vector3f interpolateVertex(Vector3f v1, Vector3f v2, Vector3f v3) {
        float alpha = (v2.getY() - v1.getY()) / (v3.getY() - v1.getY());
        return new Vector3f(
                v1.getX() + alpha * (v3.getX() - v1.getX()),
                v2.getY(),
                v1.getZ() + alpha * (v3.getZ() - v1.getZ())
        );
    }

    // Метод для интерполяции текстурных координат для разбивки треугольника
    private static Point2D interpolateTexCoord(Point2D t1, Point2D t2, Point2D t3) {
        float alpha = (float) ((t2.getY() - t1.getY()) / (t3.getY() - t1.getY()));
        return new Point2D(
                t1.getX() + alpha * (t3.getX() - t1.getX()),
                t2.getY() + alpha * (t3.getY() - t1.getY())
        );
    }

    // Метод для интерполяции текстуры
    private static Color getTextureColor(Image texture, Point2D textureCoord) {
        PixelReader pixelReader = texture.getPixelReader();
        int tx = (int) (textureCoord.getX() * texture.getWidth());
        int ty = (int) (textureCoord.getY() * texture.getHeight());
        return pixelReader.getColor(tx, ty);
    }

    // Метод для интерполяции барицентрических координат
    private static Point2D getBarycentricCoordinates(Vector3f v1, Vector3f v2, Vector3f v3, double x, double y) {
        double denominator = (v2.getY() - v3.getY()) * (v1.getX() - v3.getX()) + (v3.getX() - v2.getX()) * (v1.getY() - v3.getY());
        double a = ((v2.getY() - v3.getY()) * (x - v3.getX()) + (v3.getX() - v2.getX()) * (y - v3.getY())) / denominator;
        double b = ((v3.getY() - v1.getY()) * (x - v3.getX()) + (v1.getX() - v3.getX()) * (y - v3.getY())) / denominator;
        return new Point2D(a, b);
    }

    // Метод для интерполяции глубины
    private static double interpolateDepth(Point2D baryCoords, double z1, double z2, double z3) {
        return z1 * baryCoords.getX() + z2 * baryCoords.getY() + z3 * (1 - baryCoords.getX() - baryCoords.getY());
    }
    // Метод для интерполяции текстурных координат с использованием барицентрических координат
    private static Point2D interpolateTextureCoords(Point2D baryCoords, Point2D t1, Point2D t2, Point2D t3) {
        double u = t1.getX() * baryCoords.getX() + t2.getX() * baryCoords.getY() + t3.getX() * (1 - baryCoords.getX() - baryCoords.getY());
        double v = t1.getY() * baryCoords.getX() + t2.getY() * baryCoords.getY() + t3.getY() * (1 - baryCoords.getX() - baryCoords.getY());
        return new Point2D(u, v);
    }

}
