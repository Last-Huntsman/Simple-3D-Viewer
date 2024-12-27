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
import java.util.Random;

import static com.cgvsu.render_engine.GraphicConveyor.vertexToPoint;

public  class PictureProcess {


    public static void showTriangle(GraphicsContext gc, Point2D p1, Point2D p2, Point2D p3) {

        // Растеризатор для отрисовки треугольника
        TriangleRasterisator tr = new TriangleRasterisator(gc.getPixelWriter());


        // Однотонная текстура (не используется в этом методе)
        MonotoneTexture mt = new MonotoneTexture(Color.BLACK);

        // Градиентная текстура для треугольника - не нужна
//        GradientTexture gt = new GradientTexture(Color.CYAN, Color.YELLOW, Color.BLUE);


        // Отрисовка треугольника с помощью растеризатора
        tr.draw(p1, p2, p3, mt);
    }

    private static void rasterizePolygon(
            final GraphicsContext graphicsContext,
            final ArrayList<Vector3f> vertices,
            final double[][] zBuffer,
            final int width,
            final int height) {

        // Растровизация рёбер полигона и применение Z-буфера.
        int nVertices = vertices.size();

        for (int i = 0; i < nVertices; i++) {
            Vector3f v1 = vertices.get(i);
            Vector3f v2 = vertices.get((i + 1) % nVertices);

            Point2f p1 = vertexToPoint(v1, width, height);
            Point2f p2 = vertexToPoint(v2, width, height);

            drawLineWithZBuffer(graphicsContext, p1, p2, v1.z, v2.z, zBuffer);
        }
    }

    private static void drawLineWithZBuffer(
            final GraphicsContext graphicsContext,
            final Point2f p1,
            final Point2f p2,
            final double z1,
            final double z2,
            final double[][] zBuffer) {

        int x1 = Math.round(p1.x);
        int y1 = Math.round(p1.y);
        int x2 = Math.round(p2.x);
        int y2 = Math.round(p2.y);

        // Брезенхем для линий.
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;

        int err = dx - dy;
        double dz = (z2 - z1) / Math.max(dx, dy);

        double currentZ = z1;

        while (true) {
            if (x1 >= 0 && x1 < zBuffer.length && y1 >= 0 && y1 < zBuffer[0].length) {
                if (currentZ > zBuffer[x1][y1]) {
                    zBuffer[x1][y1] = currentZ;
                    graphicsContext.strokeRect(x1, y1, 1, 1);
                }
            }

            if (x1 == x2 && y1 == y2) break;

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }

            currentZ += dz;
        }
    }

}
