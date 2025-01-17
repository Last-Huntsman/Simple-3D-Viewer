package com.cgvsu.Utils;

import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindNormals {

    // Метод для вычисления нормалей для всех вершин модели и обновления индексов нормалей в полигонах
    public static ArrayList<Vector3f> findNormals(Model m) {

        List<Polygon> polygons = m.polygons;  // Список всех полигонов модели
        List<Vector3f> vertices = m.vertices;  // Список всех вершин модели

        ArrayList<Vector3f> temporaryNormals = new ArrayList<>();  // Временные нормали для каждого полигона
        ArrayList<Vector3f> normals = new ArrayList<>();  // Итоговые нормали для всех вершин

        // Для каждого полигона находим нормаль
        for (Polygon p : polygons) {
            temporaryNormals.add(findPolygonsNormals(
                    vertices.get(p.getVertexIndices().get(0)),  // Вершина 1 полигона
                    vertices.get(p.getVertexIndices().get(1)),  // Вершина 2 полигона
                    vertices.get(p.getVertexIndices().get(2))   // Вершина 3 полигона
            ));
        }

        // Создаем отображение для группировки нормалей по индексам вершин
        Map<Integer, List<Integer>> vertexPolygonsMap = new HashMap<>();
        for (int j = 0; j < polygons.size(); j++) {
            List<Integer> vertexIndices = polygons.get(j).getVertexIndices();  // Индексы вершин полигона
            for (Integer index : vertexIndices) {
                if (!vertexPolygonsMap.containsKey(index)) {
                    vertexPolygonsMap.put(index, new ArrayList<>());
                }
                vertexPolygonsMap.get(index).add(j);  // Добавляем нормаль полигона для каждой вершины
            }
        }
        Map<Integer, Integer> mapVertex_to_Normal = new HashMap<>();
        // Для каждой вершины находим итоговую нормаль, усредняя нормали всех прилегающих к ней полигонов
        for (Integer key : vertexPolygonsMap.keySet()) {
            normals.add(findVertexNormals(temporaryNormals,vertexPolygonsMap.get(key)));

            if(!mapVertex_to_Normal.containsKey(key)){
                mapVertex_to_Normal.put(key,normals.size()-1);
            }
        }

        // Обновляем индексы нормалей в полигонах, ссылаясь на индекс нормали для каждой вершины
        for (Polygon p : polygons) {
            ArrayList<Integer> polygonnormals = new ArrayList<>();
            for (int i = 0; i < p.getVertexIndices().size(); i++) {
                polygonnormals.add(mapVertex_to_Normal.get(p.getVertexIndices().get(i)));
            }
            p.setNormalIndices(polygonnormals);
        }
        return normals;  // Возвращаем список нормалей для всех вершин
    }

    // Метод для вычисления нормали для полигона, заданного тремя вершинами
    public static Vector3f findPolygonsNormals(Vector3f... vs) {
        // Вычисляем два вектора, образующие стороны полигона
        Vector3f a = new Vector3f();
        Vector3f b = new Vector3f();
        a.sub(vs[0], vs[1]);
        b.sub(vs[0], vs[2]);
        // Вычисляем векторное произведение для нахождения нормали
        Vector3f c = vectorProduct(a, b);
        if (determinant(a, b, c) < 0) {
            c = vectorProduct(b, a);  // Если ориентация нормали неправильная, инвертируем её
        }
        return normalize(c);  // Нормализуем и возвращаем нормаль
    }

    // Метод для вычисления нормали вершины как усреднённой нормали всех прилегающих полигонов
    public static Vector3f findVertexNormals(List<Vector3f> vs, List<Integer> list) {
        float xs = 0, ys = 0, zs = 0;
        // Суммируем нормали всех прилегающих полигонов
        for (Integer ind : list) {
            Vector3f v=vs.get(ind);
            xs += v.x;
            ys += v.y;
            zs += v.z;
        }

        // Усредняем значения
        xs /= vs.size();
        ys /= vs.size();
        zs /= vs.size();

        return normalize(new Vector3f(xs, ys, zs));  // Нормализуем и возвращаем итоговую нормаль
    }

    // Метод для вычисления детерминанта из трёх векторов
    public static double determinant(Vector3f a, Vector3f b, Vector3f c) {
        return a.x * (b.y * c.z) - a.y * (b.x * c.z - c.x * b.z) + a.z * (b.x * c.y - c.x * b.y);
    }

    // Метод для нормализации вектора
    public static Vector3f normalize(Vector3f v) {
        if (v == null) {
            return null;  // Если вектор равен null, возвращаем null
        }

        // Вычисление длины вектора
        float length = (float) Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z);

        // Если длина вектора равна 0, возвращаем нулевой вектор
        if (length == 0) {
            return new Vector3f(0, 0, 0);
        }

        // Возвращаем нормализованный вектор
        return new Vector3f(v.x / length, v.y / length, v.z / length);
    }

    // Метод для вычисления векторного произведения двух векторов
    public static Vector3f vectorProduct(Vector3f a, Vector3f b) {
        return new Vector3f(a.y * b.z - b.y * a.z, -a.x * b.z + b.x * a.z, a.x * b.y - b.x * a.y);
    }
}
