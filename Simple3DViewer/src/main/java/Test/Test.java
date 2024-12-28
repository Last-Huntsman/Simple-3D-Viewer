package Test;

import com.cgvsu.Ilya.Eraser;
import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test {

    static Path fileName = Path.of("C:\\Users\\zuzuk\\IdeaProjects\\Task3\\ObjReaderInitial\\tests\\SimpleModelsForReaderTests\\caracal_cube.obj");
    static String fileContent;
    static Model model;

    // Инициализация модели перед каждым тестом
    @BeforeEach
    public void setUp() {
        try {
            fileContent = Files.readString(fileName);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла: " + fileName, e);
        }
        model = ObjReader.read(fileContent);
    }

    @org.junit.jupiter.api.Test
    public void testDeleteSingleVertex() throws IOException {
        // Удаляем одну вершину
        List<Integer> verticesToDelete = List.of(0);
        Model modifiedModel = Eraser.vertexDelete(model, verticesToDelete, true, false, false, true);

        // Проверяем количество оставшихся вершин
        assertEquals(model.vertices.size() - 1, modifiedModel.vertices.size(), "Вершина не была удалена!");

        // Проверяем вывод в OBJ-формате (опционально)
        modifiedModel.exportToOBJ();
    }

    @org.junit.jupiter.api.Test
    public void testDeleteMultipleVertices() throws IOException {
        // Удаляем несколько вершин
        List<Integer> verticesToDelete = Arrays.asList(0, 1, 2);
        Model modifiedModel = Eraser.vertexDelete(model, verticesToDelete, true, false, false, true);

        // Проверяем количество оставшихся вершин
        assertEquals(model.vertices.size() - 3, modifiedModel.vertices.size(), "Несколько вершин не были удалены!");

        // Проверяем вывод в OBJ-формате (опционально)
        modifiedModel.exportToOBJ();
    }

    @org.junit.jupiter.api.Test
    public void testKeepHangingPolygons() throws IOException {
        // Удаляем вершины, оставляя висящие полигоны
        List<Integer> verticesToDelete = Arrays.asList(0, 1);
        Model modifiedModel = Eraser.vertexDelete(model, verticesToDelete, false, false, true, true);

        // Проверяем корректность модификации
        assertEquals(model.vertices.size() , modifiedModel.vertices.size(), "Висящие полигоны не обработаны корректно!");

        // Проверяем вывод в OBJ-формате (опционально)
        modifiedModel.exportToOBJ();
    }

    @org.junit.jupiter.api.Test
    public void testKeepTexturesAndNormals() throws IOException {
        // Удаляем вершины, но сохраняем текстуры и нормали
        List<Integer> verticesToDelete = Arrays.asList(0, 1);
        Model modifiedModel = Eraser.vertexDelete(model, verticesToDelete, false, true, true, false);

        // Проверяем, что текстуры и нормали остались
        assertEquals(model.textureVertices.size(), modifiedModel.textureVertices.size(), "Текстуры были удалены!");
        assertEquals(model.normals.size(), modifiedModel.normals.size(), "Нормали были удалены!");

        // Проверяем вывод в OBJ-формате (опционально)
        modifiedModel.exportToOBJ();
    }

    @org.junit.jupiter.api.Test
    public void testRemoveAllVertices() throws IOException {
        // Удаляем все вершины
        List<Integer> verticesToDelete = Arrays.asList(0, 1, 2, 3, 4,5,6,7);
        Model modifiedModel = Eraser.vertexDelete(model, verticesToDelete, true, false, false, true);

        // Проверяем, что вершины удалены
        assertEquals(0, modifiedModel.vertices.size(), "Не все вершины удалены!");

        // Проверяем вывод в OBJ-формате (опционально)
        modifiedModel.exportToOBJ();
    }
}
