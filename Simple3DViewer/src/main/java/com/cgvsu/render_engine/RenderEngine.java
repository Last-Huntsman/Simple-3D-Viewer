package com.cgvsu.render_engine;

import java.util.ArrayList;

import com.cgvsu.Pavel.math.matrices.Matrix4x4;
import com.cgvsu.Pavel.math.vectors.Vector3f;
import javafx.scene.canvas.GraphicsContext;

import com.cgvsu.model.Model;

import javax.vecmath.Point2f;

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
    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Model mesh,
            final int width,
            final int height) {

        // Создание модельной матрицы (единичной матрицы, поскольку повороты, масштабирование и трансляции здесь не заданы).
        Matrix4x4 modelMatrix = rotateScaleTranslate();

        // Получение матрицы вида из объекта камеры.
        Matrix4x4 viewMatrix = camera.getViewMatrix();

        // Получение матрицы проекции из объекта камеры.
        Matrix4x4 projectionMatrix = camera.getProjectionMatrix();

        // Объединение (умножение) матриц модельной, видовой и проекционной.
        Matrix4x4 modelViewProjectionMatrix = new Matrix4x4(modelMatrix.elements);
        modelViewProjectionMatrix.multiplyMM(viewMatrix); // Умножение на матрицу вида.
        modelViewProjectionMatrix.multiplyMM(projectionMatrix); // Умножение на матрицу проекции.

        // Количество полигонов в модели.
        final int nPolygons = mesh.polygons.size();

        // Перебор всех полигонов модели.
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {

            // Количество вершин в текущем полигоне.
            final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();

            // Список для хранения преобразованных экранных координат вершин полигона.
            ArrayList<Point2f> resultPoints = new ArrayList<>();

            // Перебор всех вершин текущего полигона.
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {

                // Получение координат вершины из модели.
                Vector3f vertex = mesh.vertices.get(
                        mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));

                // Преобразование вершины в формат Vector3f (можно опустить, если структура совпадает).
                Vector3f vertexVecmath = new Vector3f(vertex.x, vertex.y, vertex.z);

                // Преобразование координат вершины с помощью объединенной матрицы.
                Point2f resultPoint = vertexToPoint(
                        multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertexVecmath),
                        width,
                        height);

                // Добавление преобразованной вершины в список.
                resultPoints.add(resultPoint);
            }

            // Рисование рёбер полигона.
            for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                // Линия между текущей и предыдущей вершинами.
                graphicsContext.strokeLine(
                        resultPoints.get(vertexInPolygonInd - 1).x,
                        resultPoints.get(vertexInPolygonInd - 1).y,
                        resultPoints.get(vertexInPolygonInd).x,
                        resultPoints.get(vertexInPolygonInd).y);
            }

            // Замыкание полигона (соединение последней вершины с первой).
            if (nVerticesInPolygon > 0) {
                graphicsContext.strokeLine(
                        resultPoints.get(nVerticesInPolygon - 1).x,
                        resultPoints.get(nVerticesInPolygon - 1).y,
                        resultPoints.get(0).x,
                        resultPoints.get(0).y);
            }
        }
    }
}
