package com.cgvsu.Utils;





import com.cgvsu.model.Polygon;

import java.util.ArrayList;

/**
 * Класс для триангуляции полигонов.
 * <p>
 * Методы позволяют разделять многоугольники (полигоны) на треугольники,
 * что полезно для рендеринга, вычислений и других задач, связанных с графикой.
 */
public class Triangulation {

    /**
     * Выполняет триангуляцию одного полигона.
     * Разбивает многоугольник на треугольники путем последовательного создания
     * треугольников, используя первую вершину и соседние вершины.
     *
     * @param poly исходный полигон, который нужно триангулировать
     * @return список треугольных полигонов
     */
    private static ArrayList<Polygon> triangulatePolygon(Polygon poly) {
        int vertexNum = poly.getVertexIndices().size(); // Количество вершин в исходном полигоне
        ArrayList<Polygon> polygons = new ArrayList<>(); // Список для хранения треугольных полигонов

        // Если передан уже треугольник, просто возвращаем его
        if (vertexNum == 3) {
            polygons.add(poly);
            return polygons;
        }

        // Создаем треугольники, соединяя первую вершину с остальными парами соседних вершин
        for (int i = 2; i < vertexNum - 1; i++) {
            ArrayList<Integer> vertex = new ArrayList<>(); // Вершины текущего треугольника
            vertex.add(poly.getVertexIndices().get(0)); // Первая вершина
            vertex.add(poly.getVertexIndices().get(i - 1)); // Предыдущая вершина
            vertex.add(poly.getVertexIndices().get(i)); // Текущая вершина

            Polygon currPoly = new Polygon(); // Новый треугольник
            currPoly.setVertexIndices(vertex); // Устанавливаем вершины
            polygons.add(currPoly); // Добавляем треугольник в список
        }

        // Создаем последний треугольник, если вершин больше трех
        if (vertexNum > 3) {
            ArrayList<Integer> vertex = new ArrayList<>();
            vertex.add(poly.getVertexIndices().get(0)); // Первая вершина
            vertex.add(poly.getVertexIndices().get(vertexNum - 2)); // Предпоследняя вершина
            vertex.add(poly.getVertexIndices().get(vertexNum - 1)); // Последняя вершина

            Polygon currPoly = new Polygon(); // Новый треугольник
            currPoly.setVertexIndices(vertex); // Устанавливаем вершины
            polygons.add(currPoly); // Добавляем треугольник в список
        }

        return polygons; // Возвращаем список треугольников
    }

    /**
     * Выполняет триангуляцию модели, представленной списком полигонов.
     * Каждый полигон из списка разбивается на треугольники.
     *
     * @param polygons список полигонов модели
     * @return новый список треугольных полигонов
     */
    public static ArrayList<Polygon> triangulateModel(ArrayList<Polygon> polygons) {
        ArrayList<Polygon> newModelPoly = new ArrayList<>(); // Список для хранения новых треугольников

        // Проходим по каждому полигону модели
        for (Polygon polygon : polygons) {
            // Выполняем триангуляцию текущего полигона и добавляем треугольники в общий список
            newModelPoly.addAll(
                    triangulatePolygon(polygon)
            );
        }

        return newModelPoly; // Возвращаем список всех треугольных полигонов
    }
}
