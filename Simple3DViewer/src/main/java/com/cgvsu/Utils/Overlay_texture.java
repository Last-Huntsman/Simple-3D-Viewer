package com.cgvsu.Utils;


import com.cgvsu.math.Baricentrics_Triangle.Barycentric;
import com.cgvsu.math.Baricentrics_Triangle.Triangle;
import com.cgvsu.math.vectors.Vector2f;
import com.cgvsu.math.vectors.Vector3f;
import javafx.geometry.Point2D;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.cgvsu.render_engine.GraphicConveyor.vertexToPoint;

public class Overlay_texture {


        public static void overlay_texture(
                ArrayList<Vector3f> resultVectors,
                ArrayList<Vector2f> textureVertexIndices,
                Image texture,
                double[][] zBuffer,
                PixelWriter pixelWriter) {


            // Преобразуем вершины в 2D точки
            Point2D p0 = vertexToPoint(resultVectors.get(0));
            Point2D p1 = vertexToPoint(resultVectors.get(1));
            Point2D p2 = vertexToPoint(resultVectors.get(2));

            // Текстурные координаты вершин
            Vector2f t0 = textureVertexIndices.get(0);
            Vector2f t1 = textureVertexIndices.get(1);
            Vector2f t2 = textureVertexIndices.get(2);

            // Сортируем вершины по координате Y
            List<Point2D> vertices = List.of(p0, p1, p2);
            vertices.sort((v1, v2) -> Double.compare(v1.getY(), v2.getY()));

            p0 = vertices.get(0);
            p1 = vertices.get(1);
            p2 = vertices.get(2);

            // Соответствующая сортировка текстурных координат
            Vector2f[] textures = {t0, t1, t2};
            if (vertices.get(0) == p1) {
                textures = new Vector2f[]{t1, t0, t2};
            } else if (vertices.get(0) == p2) {
                textures = new Vector2f[]{t2, t0, t1};
            }

            t0 = textures[0];
            t1 = textures[1];
            t2 = textures[2];

            // Разделяем треугольник на два подтреугольника
            scanlineFill(p0, p1, p2, t0, t1, t2, texture, zBuffer, pixelWriter);
        }

        private static void scanlineFill(
                Point2D p0, Point2D p1, Point2D p2,
                Vector2f t0, Vector2f t1, Vector2f t2,
                Image texture, double[][] zBuffer, PixelWriter pixelWriter) {

            PixelReader textureReader = texture.getPixelReader();

            // Разделяем треугольник на верхний и нижний
            fillPart(p0, p1, p2, t0, t1, t2, textureReader, zBuffer, pixelWriter, true);
            fillPart(p1, p2, p0, t1, t2, t0, textureReader, zBuffer, pixelWriter, false);
        }

        private static void fillPart(
                Point2D p1, Point2D p2, Point2D p3,
                Vector2f t1, Vector2f t2, Vector2f t3,
                PixelReader textureReader, double[][] zBuffer, PixelWriter pixelWriter,
                boolean isUpperPart) {

            int yStart = (int) Math.ceil(p1.getY());
            int yEnd = (int) (isUpperPart ? Math.floor(p2.getY()) : Math.floor(p3.getY()));

            for (int y = yStart; y <= yEnd; y++) {
                // Вычисляем X-координаты левой и правой границы строки
                double xLeft = interpolateX(p1, p2, y);
                double xRight = interpolateX(p1, p3, y);

                // Текстурные координаты для левой и правой границы
                Vector2f tLeft = interpolateTexture(p1, p2, t1, t2, y);
                Vector2f tRight = interpolateTexture(p1, p3, t1, t3, y);

                // Ограничиваем диапазон X внутри экрана
                int xStart = (int) Math.ceil(Math.min(xLeft, xRight));
                int xEnd = (int) Math.floor(Math.max(xLeft, xRight));

                for (int x = xStart; x <= xEnd; x++) {
                    // Интерполируем текстурные координаты вдоль строки
                    double alpha = (x - xLeft) / (xRight - xLeft);
                    double u = tLeft.x + alpha * (tRight.x - tLeft.x);
                    double v = tLeft.y + alpha * (tRight.y - tLeft.y);

//                    // Получаем текстурный цвет
//                    javafx.scene.paint.Color color = textureReader.getColor(
//                            Math.min((int) (u * texture.getWidth()), (int) texture.getWidth() - 1),
//                            Math.min((int) (v * texture.getHeight()), (int) texture.getHeight() - 1)
//                    );
//
//                    // Обновляем пиксель на экране, если он ближе
//                    double z = calculateDepth(p1, p2, p3, y, x);
//                    if (z < zBuffer[x][y]) {
//                        zBuffer[x][y] = z;
//                        pixelWriter.setColor(x, y, color);
//                    }
                }
            }
        }

        private static double interpolateX(Point2D p1, Point2D p2, int y) {
            if (p1.getY() == p2.getY()) return p1.getX();
            return p1.getX() + (y - p1.getY()) * (p2.getX() - p1.getX()) / (p2.getY() - p1.getY());
        }

        private static Vector2f interpolateTexture(Point2D p1, Point2D p2, Vector2f t1, Vector2f t2, int y) {
            if (p1.getY() == p2.getY()) return t1;
            double alpha = (y - p1.getY()) / (p2.getY() - p1.getY());
            return new Vector2f((float) (t1.x + alpha * (t2.x - t1.x)),
                    (float) (t1.y + alpha * (t2.y - t1.y))
            );
        }



    // Метод для вычисления пересечения линии с горизонтальной прямой по Y
    private static double getXForY(Point2D p1, Point2D p2, int y) {
        return p1.getX() + ((y - p1.getY()) / (p2.getY() - p1.getY())) * (p2.getX() - p1.getX());
    }


    // Метод для вычисления глубины пикселя
    private static double calculateDepth(double z1, double z2, double z3, Barycentric barycentric) {
        return barycentric.getLambda1() * z1 + barycentric.getLambda2() * z2 + barycentric.getLambda3() * z3;
    }

    // Метод для сортировки вершин по Y
    private static List<Point2D> sortedVertices(Triangle triangle) {
        List<Point2D> vertices = new ArrayList<>();
        vertices.add(triangle.getPoint1());
        vertices.add(triangle.getPoint2());
        vertices.add(triangle.getPoint3());
        vertices.sort(Comparator.comparing(Point2D::getY).thenComparing(Point2D::getX));
        return vertices;
    }



}
