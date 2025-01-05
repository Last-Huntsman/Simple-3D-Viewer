// Импортируем необходимые классы для работы с графикой, файловой системой, анимацией, моделями и интерфейсом
package com.cgvsu.gui;

import com.cgvsu.io.objReader.ObjReader;
import com.cgvsu.io.objWriter.ObjWriter;
import com.cgvsu.math.matrices.Matrix4x4;
import com.cgvsu.math.vectors.Vector2f;
import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.math.vectors.Vector4f;
import com.cgvsu.model.FinishedModel;
import com.cgvsu.model.Model;
import com.cgvsu.render_engine.Camera;
import com.cgvsu.render_engine.RenderEngine;
import com.cgvsu.render_engine.RenderModeFactory;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class GUIController {

    // Инициализация камеры со стартовыми параметрами: позиция, цель, углы, и параметры проекции
    private final Camera camera = new Camera(new Vector2f(0, 0), new Vector3f(40, 0, 0), new Vector3f(0, 0, 0), 1.0F, 1, 0.01F, 100);
    private final ModelsController modelController = new ModelsController(); // Контроллер для управления моделями

    // Константы для перемещения и вращения камеры
    private static final float TRANSLATION = 0.5F; // Шаг перемещения камеры
    private static final float ROTATION_ANGLE = 1.0F; // Угол поворота камеры

    private Model mesh; // Текущая загруженная модель

    @FXML
    private AnchorPane anchorPane; // Контейнер для интерфейса

    @FXML
    private Canvas canvas; // Холст для рендеринга сцены

    // Метки для отображения информации о камере и модели
    @FXML
    private Label positionLabel;

    @FXML
    private Label rotationLabel;

    @FXML
    private Label targetLabel;

    @FXML
    private Label modelLabel;

    // Обновление текстовых меток, отображающих параметры камеры и модели
    private void updateLabels() {
        Vector3f position = camera.getPosition(); // Позиция камеры
        Vector2f rotation = camera.getRotation(); // Углы поворота камеры
        Vector3f targetPosition = camera.getTarget(); // Целевая точка камеры
        Vector3f modelPosition = mesh.position; // Позиция модели
        Vector3f modelScale = mesh.scale; // Масштаб модели
        Vector3f modelRotation = mesh.rotation; // Поворот модели

        // Обновление текста в метках
        positionLabel.setText(String.format("Camera Position: (%.2f, %.2f, %.2f)",
                position.x, position.y, position.z));
        rotationLabel.setText(String.format("Camera Rotation: (%.2f°, %.2f°)",
                rotation.x, rotation.y));
        targetLabel.setText(String.format("Target Position: (%.2f, %.2f, %.2f)",
                targetPosition.x, targetPosition.y, targetPosition.z));
        modelLabel.setText(String.format("Model Position: (%.2f, %.2f, %.2f), Model Scale: (%.2f, %.2f, %.2f), Model Rotation: (%.2f°, %.2f°, %.2f°)",
                modelPosition.x, modelPosition.y, modelPosition.z,
                modelScale.x, modelScale.y, modelScale.z,
                modelRotation.x, modelRotation.y, modelRotation.z));
    }

    // Методы для обработки перемещения камеры
    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        camera.moveForwardWithoutTrigger(TRANSLATION); // Перемещаем камеру вперед
        mesh.scale = new Vector3f(3, 3, 3); // Пример изменения масштаба модели
        updateLabels(); // Обновляем метки
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        camera.moveBackwardWithoutTrigger(TRANSLATION); // Камера движется назад
        updateLabels();
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        camera.moveLeftWithoutTrigger(TRANSLATION); // Камера движется влево
        updateLabels();
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        camera.moveRightWithoutTrigger(TRANSLATION); // Камера движется вправо
        updateLabels();
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        camera.moveUpWithoutTrigger(TRANSLATION); // Камера движется вверх
        updateLabels();
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        camera.moveDownWithoutTrigger(TRANSLATION); // Камера движется вниз
        updateLabels();
    }

    // Методы для вращения камеры
    @FXML
    public void handleCameraRotateLeft(ActionEvent actionEvent) {
        camera.rotateWithoutTrigger(-ROTATION_ANGLE, 0); // Вращение камеры влево
        updateLabels();
    }

    @FXML
    public void handleCameraRotateRight(ActionEvent actionEvent) {
        camera.rotateWithoutTrigger(ROTATION_ANGLE, 0); // Вращение камеры вправо
        updateLabels();
    }

    @FXML
    public void handleCameraRotateUp(ActionEvent actionEvent) {
        camera.rotateWithoutTrigger(0, ROTATION_ANGLE); // Вращение камеры вверх
        updateLabels();
    }

    @FXML
    public void handleCameraRotateDown(ActionEvent actionEvent) {
        camera.rotateWithoutTrigger(0, -ROTATION_ANGLE); // Вращение камеры вниз
        updateLabels();
    }

    @FXML
    private void initialize() {
        // Привязка размеров холста к размерам контейнера
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        // Создание таймлайна для обновления сцены
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE); // Бесконечный цикл таймлайна

        RenderEngine renderEngine = new RenderEngine(); // Движок рендеринга

        // Обновление кадра каждые 15 миллисекунд
        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height); // Очистка холста
            camera.setAspectRatio((float) (height / width)); // Обновление соотношения сторон

            // Рендеринг модели, если она загружена
            if (mesh != null) {
                renderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) width, (int) height);
            }
        });

        Path fileName = Path.of("Simple3DViewer/models/3DModels/CaracalCube/caracal_cube.obj");

        try {
            String fileContent = Files.readString(fileName); // Чтение файла модели
            String modelName = getModelName(fileName);
            mesh = ObjReader.read(fileContent); // Парсинг модели
            FinishedModel loadedModel = new FinishedModel(mesh, modelName, RenderModeFactory.grid());
            modelController.addModel(loadedModel); // Добавление модели в контроллер
            modelController.addNameToNameSet(modelName);
            updateLabels();
        } catch (IOException ignored) {
        }

        timeline.getKeyFrames().add(frame); // Добавление кадра в таймлайн
        timeline.play(); // Запуск анимации
    }

    // Обработчик открытия текстуры
    @FXML
    private void onOpenTextureItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG (*.png)", "*.png"));
        fileChooser.setTitle("Load Texture");

        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            // Здесь можно добавить обработку текстуры
            String fileContent = Files.readString(fileName);
            mesh = ObjReader.read(fileContent);
        } catch (IOException exception) {
        }
    }
    @FXML
    private void onOpenModelMenuItemClick() {
        // Открывает диалоговое окно для выбора модели в формате *.obj
        FileChooser fileChooser = new FileChooser();
        // Устанавливаем фильтр для отображения только файлов с расширением *.obj
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        // Показываем диалоговое окно и получаем выбранный файл
        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;  // Если файл не выбран, выходим из метода
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            // Читаем содержимое файла и загружаем модель
            String fileContent = Files.readString(fileName);
            mesh = ObjReader.read(fileContent);  // Парсим файл и создаем объект модели
            updateLabels(); // Обновляем отображаемую информацию об объекте
        } catch (IOException exception) {
            // Если возникла ошибка при чтении файла, она игнорируется
        }
    }

    @FXML
    private void saveModelFile() {
        // Создаем объект для записи модели в файл
        ObjWriter objWriter = new ObjWriter();
        // Устанавливаем текущую модель для сохранения
        setCurrentModel(0);

        // Открываем диалоговое окно для выбора места сохранения
        FileChooser fileChooser = new FileChooser();
        // Устанавливаем имя файла по умолчанию
        fileChooser.setInitialFileName(modelController.currentModel.getName());
        // Устанавливаем фильтр для отображения только файлов с расширением *.obj
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Save model");

        File file = fileChooser.showSaveDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;  // Если файл не выбран, выходим из метода
        }

        String filename = file.getAbsolutePath();

        try {
            // Пишем текущую модель в файл
            objWriter.write(modelController.currentModel.model, filename);
        } catch (Exception e) {
            // Если произошла ошибка, отображаем сообщение об ошибке
            showError("Error", "Error while writing file");
        }
    }

    @FXML
    private void saveModifiedModelFile() {
        // Создаем объект для записи модели в файл
        ObjWriter objWriter = new ObjWriter();

        // Открываем диалоговое окно для выбора места сохранения
        FileChooser fileChooser = new FileChooser();
        // Устанавливаем фильтр для отображения только файлов с расширением *.obj
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Save model");

        File file = fileChooser.showSaveDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;  // Если файл не выбран, выходим из метода
        }

        String filename = file.getAbsolutePath();

        try {
            // Пишем трансформированную модель в файл
            objWriter.write(getTransformedModel(modelController.currentModel.model), filename);
        } catch (Exception e) {
            // Если произошла ошибка, отображаем сообщение об ошибке
            showError("Error", "Error while writing file");
        }
    }

    // Метод для получения трансформированной модели (например, с учетом изменения масштаба, вращения, положения)
    private Model getTransformedModel(Model model) {
        Model newModel = new Model(); // Создаем новую модель
        newModel.vertices = new ArrayList<>(); // Создаем новый список вершин

        // Получаем матрицу преобразования модели
        Matrix4x4 modelMatrix = model.getModelMatrix();

        // Для каждой вершины модели применяем преобразование
        for (Vector3f vertex : model.vertices) {
            // Переводим вершину в четырехмерный вектор (добавляем компонент W = 1)
            Vector4f vertex4 = new Vector4f(vertex.x, vertex.y, vertex.z, 1f);
            // Применяем матрицу преобразования
            vertex4 = modelMatrix.mulV(vertex4);
            // Преобразуем обратно в трехмерный вектор и добавляем в новую модель
            newModel.vertices.add(new Vector3f(vertex4.x, vertex4.y, vertex4.z));
        }
        return newModel; // Возвращаем новую модель
    }

    // Метод для генерации уникального имени модели на основе пути файла
    private String getModelName(Path fileName) {
        String modelName = fileName.getFileName().toString(); // Извлекаем имя файла
        if (modelController.getNamesSet().contains(modelName)) { // Проверяем, существует ли уже такое имя
            int counter = 0;
            while (modelController.getNamesSet().contains(modelName)) {
                // Генерируем новое имя, добавляя индекс в конец
                modelName = modelName.substring(0, (modelName.length() - 4 - (counter > 0 ? String.valueOf(counter).length() : 0))) + (counter + 1) + ".obj";
                counter++;
            }
        }
        return modelName; // Возвращаем уникальное имя
    }

    // Метод для установки текущей модели (по индексу)
    private void setCurrentModel(int index) {
        modelController.setCurrent(index); // Устанавливаем текущую модель в контроллере
    }

    // Метод для отображения сообщения об ошибке
    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR); // Создаем окно ошибки
        alert.setTitle(title); // Устанавливаем заголовок
        alert.setContentText(content); // Устанавливаем текст ошибки
        alert.showAndWait(); // Показываем окно и ожидаем его закрытия
    }

}
