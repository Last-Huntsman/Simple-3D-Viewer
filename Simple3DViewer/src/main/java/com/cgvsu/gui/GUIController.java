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
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.Image;


import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GUIController {

    // Инициализация камеры со стартовыми параметрами: позиция, цель, углы, и параметры проекции
    private final Camera camera = new Camera(new Vector2f(0, 0), new Vector3f(100, 0, 0), new Vector3f(0, 0, 0), 1.0F, 1, 0.01F, 100);
    private final ModelsController modelController = new ModelsController(); // Контроллер для управления моделями

    // Константы для перемещения и вращения камеры
    private static final float TRANSLATION = 0.5F; // Шаг перемещения камеры
    private static final float ROTATION_ANGLE = 1.0F; // Угол поворота камеры

    private final List<Model> meshes = new ArrayList<>(); // Список загруженных моделей
    private final List<Image> textures = new ArrayList<>();

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

        // Обновление текста в метках камеры
        positionLabel.setText(String.format("Camera Position: (%.2f, %.2f, %.2f)",
                position.x, position.y, position.z));
        rotationLabel.setText(String.format("Camera Rotation: (%.2f°, %.2f°)",
                rotation.x, rotation.y));
        targetLabel.setText(String.format("Target Position: (%.2f, %.2f, %.2f)",
                targetPosition.x, targetPosition.y, targetPosition.z));

        // Обновление текста для всех загруженных моделей
        StringBuilder modelInfo = new StringBuilder();
        for (FinishedModel finishedModel : modelController.getModels()) {
            Model mesh = finishedModel.getModel();
            Vector3f modelPosition = mesh.position; // Позиция модели
            Vector3f modelScale = mesh.scale; // Масштаб модели
            Vector3f modelRotation = mesh.rotation; // Поворот модели

            modelInfo.append(String.format("Model: %s, Position: (%.2f, %.2f, %.2f), Scale: (%.2f, %.2f, %.2f), Rotation: (%.2f°, %.2f°, %.2f°)\n",
                    finishedModel.getName(),
                    modelPosition.x, modelPosition.y, modelPosition.z,
                    modelScale.x, modelScale.y, modelScale.z,
                    modelRotation.x, modelRotation.y, modelRotation.z));
        }

        modelLabel.setText(modelInfo.toString());
    }


    // Методы для обработки перемещения камеры
    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        camera.moveForwardWithoutTrigger(TRANSLATION); // Перемещаем камеру вперед
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
    private void handleModelClick(MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();

        for (FinishedModel finishedModel : modelController.getModels()) {
            Model model = finishedModel.getModel();

            // Проверка попадания клика в модель
            if (isMouseOverModel(mouseX, mouseY, model)) {
                modelController.setCurrent(finishedModel.getName());
                updateLabels();
                break;
            }
        }
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

    private double lastMouseX = -1;
    private double lastMouseY = -1;

    private void handleMousePressed(MouseEvent event) {
        lastMouseX = event.getX();
        lastMouseY = event.getY();
    }

    private void handleMouseRotation(MouseEvent event) {
        if (lastMouseX == -1 || lastMouseY == -1) {
            // Если координаты не заданы, игнорируем
            return;
        }

        // Текущие координаты мыши
        double currentMouseX = event.getX();
        double currentMouseY = event.getY();

        // Вычисляем разницу
        double deltaX = currentMouseX - lastMouseX;
        double deltaY = currentMouseY - lastMouseY;

        // Обновляем координаты для следующего шага
        lastMouseX = currentMouseX;
        lastMouseY = currentMouseY;

        // Чувствительность мыши
        final float MOUSE_SENSITIVITY = 0.05f;

        // Перевод разницы в углы
        float yaw = (float) (-deltaX * MOUSE_SENSITIVITY);
        float pitch = (float) (-deltaY * MOUSE_SENSITIVITY); // Инвертируем ось Y

        // Поворачиваем камеру
        camera.rotateWithoutTrigger(yaw, pitch);

        // Обновляем интерфейс (если нужно)
        updateLabels();
    }
    private boolean isMouseOverModel(double mouseX, double mouseY, Model model) {
        // Проверяем, попадает ли мышь в область проекции модели
        // Упростим задачу: проверим попадание в прямоугольник
        Vector3f position = model.position; // Позиция модели в 3D
        float size = 10.0f; // Условный размер модели для проверки

        // Преобразуем координаты модели в 2D
        Point projected = camera.projectTo2D(position, canvas.getWidth(), canvas.getHeight());

        return mouseX >= projected.x - size && mouseX <= projected.x + size &&
                mouseY >= projected.y - size && mouseY <= projected.y + size;
    }

    private void handleMouseReleased(MouseEvent event) {
        lastMouseX = -1;
        lastMouseY = -1;
    }

    private void handleMouseScroll(ScrollEvent event) {
        // Чувствительность зума
        final float ZOOM_SENSITIVITY = 0.1f;

        // Движение камеры: прокрутка вверх (положительное значение) приближает, вниз — отдаляет
        float zoomAmount = -(float)(event.getDeltaY()) * ZOOM_SENSITIVITY;

        // Изменение положения камеры вдоль её направления
        camera.zoom(zoomAmount);

        // Обновление интерфейса (если нужно)
        updateLabels();
    }

    @FXML
    private void initialize() {
        // Привязка размеров холста к размерам контейнера
            anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
            anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

            canvas.setOnMousePressed(this::handleMousePressed);
            canvas.setOnMouseDragged(this::handleMouseRotation);
            canvas.setOnMouseReleased(this::handleMouseReleased);
            canvas.setOnScroll(this::handleMouseScroll);

            Timeline timeline = new Timeline();
            timeline.setCycleCount(Animation.INDEFINITE);

            KeyFrame frame = getKeyFrame();
            timeline.getKeyFrames().add(frame);
            timeline.play();
        }


    private KeyFrame getKeyFrame() {
        RenderEngine renderEngine = new RenderEngine();

        return new KeyFrame(Duration.millis(40), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (height / width));

            for (FinishedModel finishedModel : modelController.getModels()) {
                Model model = finishedModel.getModel();
                Image texture = finishedModel.getRenderMode().getTexture();


                renderEngine.render(canvas.getGraphicsContext2D(), camera, model, (int) width, (int) height, texture, true,true,true , Color.RED, 0.5 );
            }
        });
    }


    // Обработчик открытия текстуры
    @FXML
    private void onOpenTextureItemClick() {
        FileChooser fileChooser = new FileChooser();

        // Устанавливаем фильтр для файлов PNG
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG (*.png)", "*.png"));

        // Устанавливаем заголовок окна
        fileChooser.setTitle("Load Texture");

        // Устанавливаем корневую папку проекта в качестве начального каталога
        File projectRoot = new File(System.getProperty("user.dir"));
        if (projectRoot.exists()) {
            fileChooser.setInitialDirectory(projectRoot);
        }


        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        try {
            // Загружаем PNG изображение
            Image textureImage = new Image(file.toURI().toString());

            // Печать для проверки
            System.out.println("Текстура загружена: " + file.getName());

        } catch (Exception exception) {
            // Обработка ошибок при загрузке изображения
            System.out.println("Ошибка при загрузке текстуры: " + exception.getMessage());
        }
    }

    @FXML
    private void onOpenModelMenuItemClick() {
        // Открывает диалоговое окно для выбора модели в формате *.obj
        FileChooser fileChooser = new FileChooser();
        // Устанавливаем фильтр для отображения только файлов с расширением *.obj
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");
        // Устанавливаем корневую папку проекта в качестве начального каталога
        File projectRoot = new File(System.getProperty("user.dir"));
        if (projectRoot.exists()) {
            fileChooser.setInitialDirectory(projectRoot);
        }
        //Показываем диалоговое окно и получаем выбранный файл
        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;  // Если файл не выбран, выходим из метода
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            // Читаем содержимое файла и загружаем модель
            String fileContent = Files.readString(fileName);
            Model mesh = ObjReader.read(fileContent);// Парсим файл и создаем объект модели
            String modelName = getModelName(fileName);
            meshes.add(mesh);

            FinishedModel loadedModel = new FinishedModel(mesh, modelName, RenderModeFactory.grid(), false);
            modelController.addModel(loadedModel); // Добавление модели в контроллер
            modelController.addNameToNameSet(modelName);
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
        //setCurrentModel(0);

        // Открываем диалоговое окно для выбора места сохранения
        FileChooser fileChooser = new FileChooser();
        // Устанавливаем имя файла по умолчанию
        fileChooser.setInitialFileName(modelController.getCurrent().getName());
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
            objWriter.write(modelController.getCurrent().model, filename);
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
            objWriter.write(getTransformedModel(modelController.getCurrent().model), filename);
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
    /*private void setCurrentModel(int index) {
        modelController.setCurrent(index); // Устанавливаем текущую модель в контроллере
    }*/

    // Метод для отображения сообщения об ошибке
    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR); // Создаем окно ошибки
        alert.setTitle(title); // Устанавливаем заголовок
        alert.setContentText(content); // Устанавливаем текст ошибки
        alert.showAndWait(); // Показываем окно и ожидаем его закрытия
    }

}
