package com.cgvsu.Ilya;

import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.*;

// Класс, реализующий удаление вершин и сопутствующих данных из модели
public class Eraser {

    // Метод для удаления указанных вершин из модели и возможного создания нового файла
    public static Model vertexDelete(Model model, List<Integer> index, boolean new_file, boolean hanging_NormalIndices, boolean hanging_TexturelIndices, boolean hanging_polygons) {
        // Новый объект модели для хранения изменённых данных
        Model modelrez = new Model();
        // Список для хранения новых полигонов
        ArrayList<Polygon> polygons = new ArrayList<>();

        // Карты для отслеживания связей индексов вершин, нормалей и текстурных вершин
        Map<Integer, Integer> connectionVertexIndices = new HashMap<>();
        Map<Integer, Integer> connectionNormalIndices = new HashMap<>();
        Map<Integer, Integer> connectiontextureVertexIndices = new HashMap<>();

        // Множества для хранения индексов нормалей и текстурных вершин, которые нужно удалить
        Set<Integer> deletenormals = new HashSet<>();
        Set<Integer> deletetextureVertices = new HashSet<>();

        // Проходим по всем полигонам в модели
        for (int i = 0; i < model.polygons.size(); i++) {
            Polygon polygon = model.polygons.get(i);
            Polygon polygonrez = new Polygon();

            // Списки для хранения новых индексов вершин, нормалей и текстурных вершин для текущего полигона
            List<Integer> textureVertexIndices = new ArrayList<>(polygon.getTextureVertexIndices().size());
            List<Integer> normalIndices = new ArrayList<>(polygon.getNormalIndices().size());
            List<Integer> vertexIndices = new ArrayList<>(polygon.getVertexIndices().size());

            int k = 0;
            // Проверяем, сколько вершин из текущего полигона нужно удалить
            for (int j = 0; j < polygon.getVertexIndices().size(); j++) {
                if (!index.contains(polygon.getVertexIndices().get(j))) k++; // Считаем количество вершин, которые не нужно удалять
            }
            // Если все вершины полигона нужно удалить, пропускаем этот полигон
            if (k < 3) continue;

            // Обрабатываем каждую вершину полигона
            for (int j = 0; j < polygon.getVertexIndices().size(); j++) {

                // Если вершина входит в список удаляемых, то добавляем соответствующие нормали и текстурные вершины для удаления
                if (index.contains(polygon.getVertexIndices().get(j))) {
                    deletenormals.add(polygon.getNormalIndices().get(j));
                    deletetextureVertices.add(polygon.getTextureVertexIndices().get(j));
                } else {
                    // Если вершина не удаляется, проверяем, есть ли она уже в новой модели, иначе добавляем её
                    if (!connectionVertexIndices.containsKey(polygon.getVertexIndices().get(j))) {
                        vertexIndices.add(modelrez.vertices.size());
                        connectionVertexIndices.put(polygon.getVertexIndices().get(j), modelrez.vertices.size());
                        modelrez.vertices.add(new_file ? model.vertices.get(polygon.getVertexIndices().get(j)).clone() : model.vertices.get(polygon.getVertexIndices().get(j)));
                    } else {
                        vertexIndices.add(connectionVertexIndices.get(polygon.getVertexIndices().get(j)));
                    }

                    // Обработка текстурных вершин
                    if (!hanging_TexturelIndices) {
                        if (j < polygon.getTextureVertexIndices().size() && !connectiontextureVertexIndices.containsKey(polygon.getTextureVertexIndices().get(j))) {
                            textureVertexIndices.add(modelrez.textureVertices.size());
                            connectiontextureVertexIndices.put(polygon.getTextureVertexIndices().get(j), modelrez.textureVertices.size());
                            modelrez.textureVertices.add(new_file ? model.textureVertices.get(polygon.getTextureVertexIndices().get(j)).clone() : model.textureVertices.get(polygon.getTextureVertexIndices().get(j)));
                        } else {
                            textureVertexIndices.add(connectiontextureVertexIndices.get(polygon.getTextureVertexIndices().get(j)));
                        }
                    }

                    // Обработка нормалей
                    if (!hanging_NormalIndices) {
                        if (j < polygon.getNormalIndices().size() && !connectionNormalIndices.containsKey(polygon.getNormalIndices().get(j))) {
                            normalIndices.add(modelrez.normals.size());
                            connectionNormalIndices.put(polygon.getNormalIndices().get(j), modelrez.normals.size());
                            modelrez.normals.add(new_file ? model.normals.get(polygon.getNormalIndices().get(j)).clone() : model.normals.get(polygon.getNormalIndices().get(j)));
                        } else {
                            normalIndices.add(connectionNormalIndices.get(polygon.getNormalIndices().get(j)));
                        }
                    }
                }
            }

            // Если необходимо сохранить полигоны с висячими индексов, или если все вершины полигона удалены, добавляем полигон в результат
            if (hanging_polygons || k == polygon.getVertexIndices().size()) {
                polygonrez.setNormalIndices(hanging_NormalIndices ? polygon.getNormalIndices() : new ArrayList<>(normalIndices));
                polygonrez.setTextureVertexIndices(hanging_TexturelIndices ? polygon.getTextureVertexIndices() : new ArrayList<>(textureVertexIndices));
                polygonrez.setVertexIndices(new ArrayList<>(vertexIndices));
                polygons.add(polygonrez);
            }

        }

        // Обработка текстурных вершин в модели
        if (hanging_TexturelIndices)
            modelrez.textureVertices = new_file ? model.cloneTextureVertices() : model.textureVertices;
        else {
            // Добавляем текстурные вершины, которые не были удалены
            for (int i = 0; i < model.textureVertices.size(); i++) {
                if (!connectiontextureVertexIndices.containsKey(i) && !deletetextureVertices.contains(i))
                    modelrez.textureVertices.add(model.textureVertices.get(i));
            }
        }

        // Обработка нормалей в модели
        if (hanging_NormalIndices) modelrez.normals = new_file ? model.cloneNormals() : model.normals;
        else {
            // Добавляем нормали, которые не были удалены
            for (int i = 0; i < model.normals.size(); i++) {
                if (!connectionNormalIndices.containsKey(i) && !deletenormals.contains(i))
                    modelrez.normals.add(model.normals.get(i));
            }
        }

        // Устанавливаем полигоны для новой модели
        modelrez.polygons = polygons;

        // Если нужно создать новый файл, возвращаем изменённую модель, иначе просто обновляем текущую
        if (new_file) return modelrez;
        else return model = modelrez;
    }
}
