package com.cgvsu.render_engine;

import com.cgvsu.Utils.*;
import com.cgvsu.math.matrices.Matrix4x4;
import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.model.Model;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static com.cgvsu.render_engine.GraphicConveyor.multiplyMatrix4ByVector3;
import static com.cgvsu.render_engine.GraphicConveyor.vertexToBord;

/**
 * Класс для рендеринга 3D-моделей на 2D-канвас с использованием матриц преобразований.
 */
public class RenderEngine {

    private Model mesh;


    /**
     * Метод для отрисовки 3D-модели на 2D-канвасе.
     *
     * @param graphicsContext Контекст графики, используемый для рисования на канвасе.
     * @param camera          Камера, определяющая точку наблюдения, проекцию и видовые параметры.
     * @param mesh            3D-модель, содержащая вершины и полигоны.
     * @param width           Ширина канваса.
     * @param height          Высота канваса.
     */
    public void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Model mesh,
            final int width,
            final int height, Image texture) {
//        RenderUtils renderUtils = new RenderUtils(graphicsContext.getPixelWriter(), Color.BLACK, 1);
        TriangleRasterisator rasterisator = new TriangleRasterisator(graphicsContext.getPixelWriter(), Color.GREEN, 0.5, camera);
       // Триангуляция и расчет нормалей
        mesh.polygons = Triangulation.triangulateModel(mesh.polygons);
        mesh.normals = FindNormals.findNormals(mesh);
        this.mesh = mesh;

        // Создание модельной матрицы.
        Matrix4x4 modelMatrix = mesh.getModelMatrix();
        // Получение матрицы вида из объекта камеры.
        Matrix4x4 viewMatrix = camera.getViewMatrix();
        // Получение матрицы проекции из объекта камеры.
        Matrix4x4 projectionMatrix = camera.getProjectionMatrix();

        // Объединение (умножение) матриц модельной, видовой и проекционной.
        Matrix4x4 modelViewProjectionMatrix = new Matrix4x4(modelMatrix.elements);
        modelViewProjectionMatrix.mul(projectionMatrix); // Умножение на матрицу вида.
        modelViewProjectionMatrix.mul(viewMatrix); // Умножение на матрицу проекции.




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

                // Добавление преобразованной вершины в список.
                resultVectors.add(vertexToBord(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex), width, height));
            }

            if (nVerticesInPolygon == 3) { /// Нужно будет оптимизировать перевод координат в точку
//                PictureProcess.showTriangle(graphicsContext, resultVectors, zBuffer);
                //ВНИМАНИЕ!!! требуется реализация случаев при которых метод вызывается
//                if (mesh.polygons.get(polygonInd).getTextureVertexIndices().size() == 3 && true) {
//                    renderUtils.draw(mesh, polygonInd,
//                            resultVectors,
//                            texture,
//                            zBuffer, false, false);
//                }
                rasterisator.draw(resultVectors,
                        new ArrayList<>(List.of(
                                mesh.normals.get(mesh.polygons.get(polygonInd).getNormalIndices().get(0)),
                                mesh.normals.get(mesh.polygons.get(polygonInd).getNormalIndices().get(1)),
                                mesh.normals.get(mesh.polygons.get(polygonInd).getNormalIndices().get(2)
                                ))),




                        zBuffer,
                        true);
            }

            if (nVerticesInPolygon > 1 && true) { // Не убирайте true - это будут флаги
                PictureProcess.rasterizePolygon(graphicsContext, resultVectors, zBuffer);
            }

        }

    }

}