package com.cgvsu.render_engine;

import com.cgvsu.Egor.FindNormals;
import com.cgvsu.Egor.Triangulation;
import com.cgvsu.Pavel.math.matrices.Matrix4x4;
import com.cgvsu.Pavel.math.vectors.Vector3f;
import com.cgvsu.model.Model;
import javafx.scene.canvas.GraphicsContext;

import javax.vecmath.Point2f;
import java.util.ArrayList;

import static com.cgvsu.render_engine.GraphicConveyor.*;

/**
 * Класс для рендеринга 3D-моделей на 2D-канвас с использованием матриц преобразований.
 */
public class RenderEngine {

    /**
     * Метод для отрисовки 3D-модели на 2D-канвасе.
     *
     * @param graphicsContext Контекст графики, используемый для рисования на канвасе.
     * @param camera          Камера, определяющая точку наблюдения, проекцию и видовые параметры.
     * @param mesh            3D-модель, содержащая вершины и полигоны.
     * @param width           Ширина канваса.
     * @param height          Высота канваса.
     */
    public static void render(final GraphicsContext graphicsContext, final Camera camera, final Model mesh, final int width, final int height) {

        // Отрисовка координатных осей
        graphicsContext.setStroke(javafx.scene.paint.Color.GRAY);
        graphicsContext.setLineWidth(1);
        graphicsContext.strokeLine(0, height / 2.0, width, height / 2.0); // Ось X
        graphicsContext.strokeLine(width / 2.0, 0, width / 2.0, height); // Ось Y

        // Отрисовка сетки
        int step = 50;
        graphicsContext.setStroke(javafx.scene.paint.Color.LIGHTGRAY);
        graphicsContext.setLineWidth(0.5);
        for (int x = step; x < width; x += step) {
            graphicsContext.strokeLine(x, 0, x, height);
            graphicsContext.strokeLine(width - x, 0, width - x, height);
        }
        for (int y = step; y < height; y += step) {
            graphicsContext.strokeLine(0, y, width, y);
            graphicsContext.strokeLine(0, height - y, width, height - y);
        }


        // Создание модельной матрицы (единичной матрицы, поскольку повороты, масштабирование и трансляции здесь не заданы).
        Matrix4x4 modelMatrix = rotateScaleTranslate();

        // Получение матрицы вида из объекта камеры.
        Matrix4x4 viewMatrix = camera.getViewMatrix();

        // Получение матрицы проекции из объекта камеры.
        Matrix4x4 projectionMatrix = camera.getProjectionMatrix();

        // Объединение (умножение) матриц модельной, видовой и проекционной.
        Matrix4x4 modelViewProjectionMatrix = new Matrix4x4(modelMatrix.elements);

        modelViewProjectionMatrix.mul(projectionMatrix); // Умножение на матрицу вида.
        modelViewProjectionMatrix.mul(viewMatrix); // Умножение на матрицу проекции.

        Triangulation.triangulateModel(mesh.polygons);
        FindNormals.findNormals(mesh);


        double[][] zBuffer = new double[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                zBuffer[x][y] = Double.NEGATIVE_INFINITY;
            }
        }

        // Количество полигонов в модели.
        final int nPolygons = mesh.polygons.size();

        // Перебор всех полигонов модели.
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {

            // Количество вершин в текущем полигоне.
            final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();
            // Список для хранения преобразованных экранных координат вершин полигона.
            ArrayList<Vector3f> resultVectors = new ArrayList<>();
            // Перебор всех вершин текущего полигона.
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {

                // Получение координат вершины из модели.
                Vector3f vertex = mesh.vertices.get(
                        mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));

                // Преобразование вершины в формат Vector3f (можно опустить, если структура совпадает).
                Vector3f vertexVecmath = new Vector3f(vertex.x, vertex.y, vertex.z);
                // Добавление преобразованной вершины в список.
                /**
                 * Проверить вот эту строчку
                  */
                resultVectors.add(vertexToBord(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertexVecmath),width, height));
            }
            if (nVerticesInPolygon > 1 && true) { // Не убирайте true - это будут флаги
                PictureProcess.rasterizePolygon(graphicsContext, resultVectors,zBuffer );
            }
            if(nVerticesInPolygon == 3 && true){
                PictureProcess.showTriangle(graphicsContext,resultVectors,zBuffer);
            }
        }
    }

}