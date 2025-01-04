package com.cgvsu.gui;

import com.cgvsu.math.matrices.Matrix4x4;
import com.cgvsu.math.vectors.Vector2f;
import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.math.vectors.Vector4f;
import com.cgvsu.render_engine.RenderEngine;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;

import com.cgvsu.model.Model;
import com.cgvsu.Ilya.ObjReader;
import com.cgvsu.render_engine.Camera;

public class CameraController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private Model mesh;

    @FXML
    private Label positionLabel;

    @FXML
    private Label rotationLabel;

    private void updateLabels() {
        Vector3f position = camera.getPosition();
        Vector2f rotation = camera.getRotation();

        positionLabel.setText(String.format("Camera Position: (%.2f, %.2f, %.2f)",
                position.x, position.y, position.z));
        rotationLabel.setText(String.format("Camera Rotation: (%.2f°, %.2f°)",
                rotation.x, rotation.y));
    }

    private final Camera camera = new Camera(new Vector2f(90, 0), new Vector3f(0, 0, 40), new Vector3f(0, 0, 0), 1.0F, 1, 0.01F, 100);

    private static final float TRANSLATION = 0.5F;
    private static final float ROTATION_ANGLE = 1.0F;

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        camera.moveForward(TRANSLATION);
        updateLabels();
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        camera.moveBackward(TRANSLATION);
        updateLabels();
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        camera.moveLeft(TRANSLATION);
        updateLabels();

    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        camera.moveRight(TRANSLATION);
        updateLabels();
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        camera.moveUp(TRANSLATION);
        updateLabels();
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        camera.moveDown(TRANSLATION);
        updateLabels();
    }

    @FXML
    public void handleCameraRotateLeft(ActionEvent actionEvent) {
        camera.rotate(-ROTATION_ANGLE, 0);
        updateLabels();
    }

    @FXML
    public void handleCameraRotateRight(ActionEvent actionEvent) {
        camera.rotate(ROTATION_ANGLE, 0);
        updateLabels();
    }

    @FXML
    public void handleCameraRotateUp(ActionEvent actionEvent) {
        camera.rotate(0, ROTATION_ANGLE);
        updateLabels();
    }

    @FXML
    public void handleCameraRotateDown(ActionEvent actionEvent) {
        camera.rotate(0, -ROTATION_ANGLE);
        updateLabels();
    }


    @FXML
    private void initialize() {


        // Устанавливаем размеры холста в зависимости от размеров панели
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        // Инициализация таймлайна для регулярного обновления сцены
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);  // Таймлайн будет работать бесконечно

        RenderEngine renderEngine = new RenderEngine();


        // Создаём новый ключевой кадр, который обновляет сцену каждые 15 миллисекунд
        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            // Очищаем холст перед каждым рендерингом
            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);

            // Обновляем соотношение сторон для камеры в зависимости от размеров холста
            camera.setAspectRatio((float) (height / width));

            // Если модель загружена, рендерим её
            if (mesh != null) {
                renderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) width, (int) height);
            }
        });



        // Добавляем ключевой кадр в таймлайн и начинаем анимацию
        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    @FXML
    private void onOpenTextureIntemClick() {
        // Создаем диалоговое окно для выбора файла с фильтром для *.obj
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG (*.png)", "*.png"));
        fileChooser.setTitle("Load Texture");

        // Показываем диалоговое окно для выбора файла
        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;  // Если файл не выбран, ничего не делаем
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            // Чтение содержимого файла и загрузка модели
            String fileContent = Files.readString(fileName);
            mesh = ObjReader.read(fileContent);  // Загружаем модель из содержимого файла
        } catch (IOException exception) {
            // Обработка ошибок при чтении файла (например, если файл повреждён)
        }
    }

    // Метод для открытия файла модели (.obj)
    @FXML
    private void onOpenModelMenuItemClick() {
        // Создаем диалоговое окно для выбора файла с фильтром для *.obj
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        // Показываем диалоговое окно для выбора файла
        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;  // Если файл не выбран, ничего не делаем
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            // Чтение содержимого файла и загрузка модели
            String fileContent = Files.readString(fileName);
            mesh = ObjReader.read(fileContent);  // Загружаем модель из содержимого файла
            updateLabels();

        } catch (IOException exception) {
            // Обработка ошибок при чтении файла (например, если файл повреждён)
        }
    }

    private Model getTransformedModel(Model model) {
        Model newModel = new Model();
        newModel.vertices = new ArrayList<>();
        Matrix4x4 modelMatrix = model.getModelMatrix();
        for (Vector3f vertex : model.vertices){
            Vector4f vertex4 = new Vector4f(vertex.x, vertex.y, vertex.z, 1f);
            vertex4 = modelMatrix.mulV(vertex4);
            newModel.vertices.add(new Vector3f(vertex4.x, vertex4.y, vertex.z));
        }
        return newModel;
    }
}
