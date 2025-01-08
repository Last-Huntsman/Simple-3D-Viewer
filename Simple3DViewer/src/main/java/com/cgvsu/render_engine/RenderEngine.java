package com.cgvsu.render_engine;

import com.cgvsu.Utils.*;
import com.cgvsu.math.matrices.Matrix4x4;
import com.cgvsu.math.vectors.Vector2f;
import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import static com.cgvsu.render_engine.GraphicConveyor.multiplyMatrix4ByVector3;
import static com.cgvsu.render_engine.GraphicConveyor.vertexToBord;

/**
 * Класс для рендеринга 3D-моделей на 2D-канвас с использованием матриц преобразований.
 */
public class RenderEngine {


    TextureRasterisator textureRasterisator;

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
        if(texture!=null) {
             textureRasterisator = new TextureRasterisator(texture, graphicsContext.getPixelWriter(), 0.5 , camera.getPosition());
        }
        TriangleRasterisator triangleRasterisator = new TriangleRasterisator(graphicsContext.getPixelWriter(), Color.GREEN, 0.5, camera.getPosition());
        // Триангуляция и расчет нормалей
        mesh.polygons = Triangulation.triangulateModel(mesh.polygons);
//        mesh.normals = FindNormals.findNormals(mesh);

        // Создание модельной матрицы.
        Matrix4x4 modelMatrix = mesh.getModelMatrix();
        // Получение матрицы вида из объекта камеры.
        Matrix4x4 viewMatrix = camera.getViewMatrix();
        // Получение матрицы проекции из объекта камеры.
        Matrix4x4 projectionMatrix = camera.getProjectionMatrix();

        // Объединение (умножение) матриц модельной, видовой и проекционной.
        Matrix4x4 modelViewProjectionMatrix = new Matrix4x4(projectionMatrix);
        modelViewProjectionMatrix.mul(viewMatrix); // Умножение на матрицу вида.
        modelViewProjectionMatrix.mul(modelMatrix); // Умножение на матрицу проекции.


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

            final Polygon polygon = mesh.polygons.get(polygonInd);
            final int nVerticesInPolygon = polygon.getVertexIndices().size();
            // Список для хранения преобразованных экранных координат вершин полигона.
            ArrayList<Vector3f> resultVectors = new ArrayList<>();
            ArrayList<Vector3f> polygonNormals = new ArrayList<>();
            ArrayList<Vector2f> polygonTexture = new ArrayList<>();
            ArrayList<Vector3f> polygonVertex = new ArrayList<>();
            // Перебор всех вершин текущего полигона.
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {

                // Получение координат вершины из модели.
                Vector3f vertex = mesh.vertices.get(polygon.getVertexIndices().get(vertexInPolygonInd));
                Vector3f mirVertex = modelMatrix.mul(vertex);

                polygonVertex.add(mirVertex);
                polygonTexture.add(mesh.textureVertices.get(polygon.getTextureVertexIndices().get(vertexInPolygonInd)));
                polygonNormals.add(mesh.normals.get(polygon.getNormalIndices().get(vertexInPolygonInd)));

                resultVectors.add(vertexToBord(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex), width, height));
            }


            if (nVerticesInPolygon == 3) {

                /// Нужно будет оптимизировать перевод координат в точку
                //ВНИМАНИЕ!!! требуется реализация случаев при которых метод вызывается
                if (mesh.polygons.get(polygonInd).getTextureVertexIndices().size() == 3 && texture!=null && true) {
                    textureRasterisator.draw(resultVectors, polygonTexture, polygonVertex,
                            polygonNormals,
                            zBuffer, true);
                }

//                if(true) {
//                    triangleRasterisator.draw(resultVectors,
//                            polygonVertex, polygonNormals,
//                            zBuffer,
//                            true);
//                }
            }

            if (nVerticesInPolygon > 1 && true) { // Не убирайте true - это будут флаги
                PictureProcess.rasterizePolygon(graphicsContext, resultVectors, zBuffer);
            }

        }

    }

}