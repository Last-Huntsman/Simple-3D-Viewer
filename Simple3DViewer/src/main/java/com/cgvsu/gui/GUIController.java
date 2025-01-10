package com.cgvsu.gui;

import com.cgvsu.Utils.Eraser;
import com.cgvsu.Utils.FindNormals;
import com.cgvsu.Utils.Triangulation;
import com.cgvsu.io.objReader.ObjReader;
import com.cgvsu.io.objWriter.ObjWriter;
import com.cgvsu.math.matrices.Matrix4x4;
import com.cgvsu.math.vectors.Vector2f;
import com.cgvsu.math.vectors.Vector3f;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import com.cgvsu.math.vectors.Vector4f;
import com.cgvsu.model.FinishedModel;
import com.cgvsu.model.Model;
import com.cgvsu.render_engine.Camera;
import com.cgvsu.render_engine.RenderEngine;
import com.cgvsu.render_engine.RenderModeFactory;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GUIController {

    // Инициализация камеры со стартовыми параметрами: позиция, цель, углы, и параметры проекции
    private final Camera camera = new Camera(new Vector2f(0, 0), new Vector3f(100, 0, 0), new Vector3f(0, 0, 0), 1.0F, 1, 0.01F, 100, "First");
    private final CameraController cameraController = new CameraController();
    private final ModelsController modelController = new ModelsController(); // Контроллер для управления моделями

    // Константы для перемещения и вращения камеры
    private static final float TRANSLATION = 1F; // Шаг перемещения камеры
    private static final float ROTATION_ANGLE = 1.0F; // Угол поворота камеры

    private final List<Model> meshes = new ArrayList<>(); // Список загруженных моделей
    private final List<Image> textures=new ArrayList<>();

    private List<TitledPane> titledPanes = new ArrayList<>();
    @FXML
    private AnchorPane anchorPane; // Контейнер для интерфейса
    @FXML
    private CheckBox usePoligonMesh, useTexture, useLight, newFileCheckBox, hangingNormalsCheckBox, hangingTexturesCheckBox,
            hangingPolygonsCheckBox;
    @FXML
    private TextField vertexIndicesField;
    @FXML
    private Button deleteVerticesButton;


    @FXML
    private Canvas canvas; // Холст для рендеринга сцены

    // Метки для отображения информации о камере и модели
    @FXML
    private Label positionLabel;

    @FXML
    private TitledPane titledPane1, titledPane2, titlePane3, titledPane4;

    @FXML
    private Label rotationLabel;

    @FXML
    private Label targetLabel;

    @FXML
    private Label modelLabel;

    // Обновление текстовых меток, отображающих параметры камеры и модели
    private void updateLabels() {
        Vector3f position = camera.getPosition();// Позиция камеры
        Vector2f rotation = camera.getRotation(); // Углы поворота камеры
        Vector3f targetPosition = camera.getTarget(); // Целевая точка камеры


        // Обновление текста в метках камеры
        positionLabel.setText(String.format("Camera Position: (%.2f, %.2f, %.2f)",
                position.x, position.y, position.z));
        rotationLabel.setText(String.format("Camera Rotation: (%.2f°, %.2f°)",
                rotation.x, rotation.y));
        targetLabel.setText(String.format("Target Position: (%.2f, %.2f, %.2f)",
                targetPosition.x, targetPosition.y, targetPosition.z));

        /*Label cameraInfoLabel = new Label(String.format(
                "Camera Information:\n" +
                        "Position: (%.2f, %.2f, %.2f)\n" +
                        "Rotation: (%.2f°, %.2f°)\n" +
                        "Target: (%.2f, %.2f, %.2f)",
                camera.getPosition().x, camera.getPosition().y, camera.getPosition().z,
                camera.getRotation().x, camera.getRotation().y,
                camera.getTarget().x, camera.getTarget().y, camera.getTarget().z
        ));*/

        // Создание содержимого для TitledPane1
        VBox titledPaneContent = new VBox();
        titledPaneContent.setSpacing(10); // Задаем расстояние между элементами

        for (Camera cam : cameraController.getCameras()) {
            Vector3f camPosition = cam.getPosition();
            Vector2f camRotation = cam.getRotation();
            Vector3f camTarget = cam.getTarget();
            Label cameraInfoLabel = new Label(String.format(
                    "Camera: %s\nPosition: (%.2f, %.2f, %.2f)\nRotation: (%.2f°, %.2f°)\nTarget: (%.2f, %.2f, %.2f)",
                    cam.getName(),
                    camPosition.x, camPosition.y, camPosition.z,
                    camRotation.x, camRotation.y,
                    camTarget.x, camTarget.y, camTarget.z));

            // Создание текстовых полей для ввода новых значений
            TextField positionXField = new TextField(String.format("%.2f", camPosition.x));
            TextField positionYField = new TextField(String.format("%.2f", camPosition.y));
            TextField positionZField = new TextField(String.format("%.2f", camPosition.z));

            TextField rotationXField = new TextField(String.format("%.2f", camRotation.x));
            TextField rotationYField = new TextField(String.format("%.2f", camRotation.y));

            TextField targetXField = new TextField(String.format("%.2f", camTarget.x));
            TextField targetYField = new TextField(String.format("%.2f", camTarget.y));
            TextField targetZField = new TextField(String.format("%.2f", camTarget.z));

            // Кнопка для применения изменений
            Button applyButton = new Button("Apply Changes");
            Label finalCameraInfoLabel = cameraInfoLabel;
            applyButton.setOnAction(event -> {
                try {
                    String posXText = positionXField.getText().replace(',', '.');
                    String posYText = positionYField.getText().replace(',', '.');
                    String posZText = positionZField.getText().replace(',', '.');

                    String rotXText = rotationXField.getText().replace(',', '.');
                    String rotYText = rotationYField.getText().replace(',', '.');

                    String targetXText = targetXField.getText().replace(',', '.');
                    String targetYText = targetYField.getText().replace(',', '.');
                    String targetZText = targetZField.getText().replace(',', '.');

                    // Применение новых значений из текстовых полей
                    camPosition.set(
                            Float.parseFloat(posXText),
                            Float.parseFloat(posYText),
                            Float.parseFloat(posZText)
                    );

                    camRotation.set(
                            Float.parseFloat(rotXText),
                            Float.parseFloat(rotYText)
                    );

                    camTarget.set(
                            Float.parseFloat(targetXText),
                            Float.parseFloat(targetYText),
                            Float.parseFloat(targetZText)
                    );

                    // Обновляем текст в метке
                    finalCameraInfoLabel.setText(String.format(
                            "Camera: %s\nPosition: (%.2f, %.2f, %.2f)\nRotation: (%.2f°, %.2f°)\nTarget: (%.2f, %.2f, %.2f)",
                            cam.getName(),
                            camPosition.x, camPosition.y, camPosition.z,
                            camRotation.x, camRotation.y,
                            camTarget.x, camTarget.y, camTarget.z));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: " + e.getMessage());
                }
            });
        }
        // Обновление информации о всех загруженных моделях
        for (FinishedModel finishedModel : modelController.getModels()) {
            Model mesh = finishedModel.getModel();
            Vector3f modelPosition = mesh.position; // Позиция модели
            Vector3f modelScale = mesh.scale; // Масштаб модели
            Vector3f modelRotation = mesh.rotation; // Поворот модели

            // Создание текстовых полей для ввода новых значений
            TextField positionXField = new TextField(String.format("%.2f", modelPosition.x));
            TextField positionYField = new TextField(String.format("%.2f", modelPosition.y));
            TextField positionZField = new TextField(String.format("%.2f", modelPosition.z));

            TextField scaleXField = new TextField(String.format("%.2f", modelScale.x));
            TextField scaleYField = new TextField(String.format("%.2f", modelScale.y));
            TextField scaleZField = new TextField(String.format("%.2f", modelScale.z));

            TextField rotationXField = new TextField(String.format("%.2f", modelRotation.x));
            TextField rotationYField = new TextField(String.format("%.2f", modelRotation.y));
            TextField rotationZField = new TextField(String.format("%.2f", modelRotation.z));

            Label modelInfoLabel = new Label(String.format(
                    "Model: %s\nPosition: (%.2f, %.2f, %.2f)\nScale: (%.2f, %.2f, %.2f)\nRotation: (%.2f°, %.2f°, %.2f°)",
                    finishedModel.getName(),
                    modelPosition.x, modelPosition.y, modelPosition.z,
                    modelScale.x, modelScale.y, modelScale.z,
                    modelRotation.x, modelRotation.y, modelRotation.z));

            // Кнопка для применения изменений
            Button applyButton = new Button("Apply Changes");
            applyButton.setOnAction(event -> {
                try {
                    String positionXText = positionXField.getText().replace(',', '.');
                    String positionYText = positionYField.getText().replace(',', '.');
                    String positionZText = positionZField.getText().replace(',', '.');

                    String scaleXText = scaleXField.getText().replace(',', '.');
                    String scaleYText = scaleYField.getText().replace(',', '.');
                    String scaleZText = scaleZField.getText().replace(',', '.');

                    String rotationXText = rotationXField.getText().replace(',', '.');
                    String rotationYText = rotationYField.getText().replace(',', '.');
                    String rotationZText = rotationZField.getText().replace(',', '.');

                    // Применение новых значений из текстовых полей
                    modelPosition.set(
                            Float.parseFloat(positionXText),
                            Float.parseFloat(positionYText),
                            Float.parseFloat(positionZText)
                    );

                    modelScale.set(
                            Float.parseFloat(scaleXText),
                            Float.parseFloat(scaleYText),
                            Float.parseFloat(scaleZText)
                    );

                    modelRotation.set(
                            Float.parseFloat(rotationXText),
                            Float.parseFloat(rotationYText),
                            Float.parseFloat(rotationZText)
                    );

                    // Применяем обновленные значения к модели
                    mesh.position = modelPosition;
                    mesh.scale = modelScale;
                    mesh.rotation = modelRotation;

                    // Обновляем метку модели с новыми значениями
                    modelInfoLabel.setText(String.format(
                            "Model: %s\nPosition: (%.2f, %.2f, %.2f)\nScale: (%.2f, %.2f, %.2f)\nRotation: (%.2f°, %.2f°, %.2f°)",
                            finishedModel.getName(),
                            modelPosition.x, modelPosition.y, modelPosition.z,
                            modelScale.x, modelScale.y, modelScale.z,
                            modelRotation.x, modelRotation.y, modelRotation.z));
                } catch (NumberFormatException e) {
                    // Обработка ошибок ввода
                    System.out.println("Invalid input." + e.getMessage());
                }
            });

            // Кнопка для сброса модели
            Button resetButton = new Button("Reset");
            resetButton.setOnAction(event -> {
                // Восстанавливаем начальные значения модели
                mesh.position = new Vector3f();
                mesh.scale = new Vector3f(1, 1, 1);
                mesh.rotation = new Vector3f();

                // Обновляем текст в метке
                modelInfoLabel.setText(String.format(
                        "Model: %s\nPosition: (%.2f, %.2f, %.2f)\nScale: (%.2f, %.2f, %.2f)\nRotation: (%.2f°, %.2f°, %.2f°)",
                        finishedModel.getName(),
                        mesh.position.x, mesh.position.y, mesh.position.z,
                        mesh.scale.x, mesh.scale.y, mesh.scale.z,
                        mesh.rotation.x, mesh.rotation.y, mesh.rotation.z));
            });

            // Создание TitledPane для модели
            TitledPane modelTitledPane = new TitledPane(finishedModel.getName(), new VBox(10,
                    modelInfoLabel,
                    new Label("Position:"), positionXField, positionYField, positionZField,
                    new Label("Scale:"), scaleXField, scaleYField, scaleZField,
                    new Label("Rotation:"), rotationXField, rotationYField, rotationZField,
                    applyButton, resetButton));

            // Добавление TitledPane для модели в общий список
            titledPaneContent.getChildren().add(modelTitledPane);
        }

        // Устанавливаем содержимое в TitledPane1
        titledPane1.setContent(titledPaneContent);
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
        float pitch = (float) (-deltaY * MOUSE_SENSITIVITY);

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
        float zoomAmount = -(float) (event.getDeltaY()) * ZOOM_SENSITIVITY;

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

        titledPanes = Arrays.asList(titledPane1, titledPane2, titlePane3, titledPane4);
        canvas.setOnMousePressed(this::handleMousePressed);
        canvas.setOnMouseDragged(this::handleMouseRotation);
        canvas.setOnMouseReleased(this::handleMouseReleased);
        canvas.setOnScroll(this::handleMouseScroll);
        cameraController.addCamera(camera);

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
                renderEngine.render(canvas.getGraphicsContext2D(), camera, model, (int) width, (int) height, texture, useTexture.isSelected(), usePoligonMesh.isSelected(), useLight.isSelected(), Color.RED, 0.5);
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
            textures.clear();
            textures.add(textureImage);
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
            mesh.polygons = Triangulation.triangulateModel(mesh.polygons);
            mesh.normals = FindNormals.findNormals(mesh);
            String modelName = getModelName(fileName);
            meshes.add(mesh);
            camera.rotateWithoutTrigger(0, 0);

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

    @FXML
    private void handleDeleteVertices() {
        FinishedModel currentModel =modelController.getModels().get(0);
        try {
            // Получение индексов вершин из текстового поля
            String indicesText = vertexIndicesField.getText();
            List<Integer> indices = Arrays.stream(indicesText.split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            // Получение флажков
            boolean createNewFile = newFileCheckBox.isSelected();
            boolean keepHangingNormalIndices = hangingNormalsCheckBox.isSelected();
            boolean keepHangingTextureIndices = hangingTexturesCheckBox.isSelected();
            boolean keepHangingPolygons = hangingPolygonsCheckBox.isSelected();

            // Удаление вершин с использованием класса Eraser
            Model modifiedModel = Eraser.vertexDelete(
                    currentModel.model,   // Текущая модель (должна быть определена в контроллере)
                    indices,
                    createNewFile,
                    keepHangingNormalIndices,
                    keepHangingTextureIndices,
                    keepHangingPolygons
            );

            // Если новый файл создаётся, обновляем отображение или сохраняем его
            if (createNewFile) {
                currentModel.model = modifiedModel;
                // Обновить Canvas или другие элементы интерфейса, если требуется
            } else {
                // Обновить текущую модель
                currentModel.model = modifiedModel;
                // Обновить Canvas
            }

            // Отображение сообщения об успехе

        } catch (NumberFormatException e) {
            showMessage("Ошибка: Введите корректные индексы вершин через запятую.");
        } catch (Exception e) {
            showMessage("Ошибка при удалении вершин: " + e.getMessage());
        }
    }

    private void showMessage(String message) {
        // Пример простого отображения сообщения
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Сообщение");
        alert.setContentText(message);
        alert.showAndWait();
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
