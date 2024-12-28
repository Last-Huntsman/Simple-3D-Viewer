package com.cgvsu;

import com.cgvsu.Pavel.math.vectors.Vector3f;
import com.cgvsu.render_engine.RenderEngine;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;

import com.cgvsu.model.Model;
import com.cgvsu.Ilya.ObjReader;
import com.cgvsu.render_engine.Camera;

public class GuiController {

    // Шаг перемещения камеры
    final private float TRANSLATION = 0.5F;

    @FXML
    AnchorPane anchorPane;  // Панель, в которой расположен холст

    @FXML
    private Canvas canvas;  // Холст для отображения 3D модели

    private Model mesh = null;  // Переменная для хранения загруженной 3D модели

    // Камера, с которой будет происходить рендеринг
    private Camera camera = new Camera(
            new Vector3f(0, 00, 100),  // Позиция камеры (изначально далеко от центра сцены)
            new Vector3f(0, 0, 0),     // Точка, на которую камера будет смотреть (центральная точка сцены)
            1.0F, 1, 0.01F, 100);      // Дополнительные параметры камеры (соотношение сторон, дальние и ближние плоскости)

    private Timeline timeline;  // Таймлайн для обновлений сцены (анимировать рендеринг)

    // Метод инициализации, который вызывается при запуске GUI
    @FXML
    private void initialize() {
        // Устанавливаем размеры холста в зависимости от размеров панели
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        // Инициализация таймлайна для регулярного обновления сцены
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);  // Таймлайн будет работать бесконечно

        // Создаём новый ключевой кадр, который обновляет сцену каждые 15 миллисекунд
        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            // Очищаем холст перед каждым рендерингом
            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);

            // Обновляем соотношение сторон для камеры в зависимости от размеров холста
            camera.setAspectRatio((float) (width / height));

            // Если модель загружена, рендерим её
            if (mesh != null) {
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) width, (int) height);
            }
        });

        // Добавляем ключевой кадр в таймлайн и начинаем анимацию
        timeline.getKeyFrames().add(frame);
        timeline.play();
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
        } catch (IOException exception) {
            // Обработка ошибок при чтении файла (например, если файл повреждён)
        }
    }

    // Методы для перемещения камеры по сцене

    // Двигаем камеру вперёд
    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, -TRANSLATION));
    }

    // Двигаем камеру назад
    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, TRANSLATION));
    }

    // Двигаем камеру влево
    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(TRANSLATION, 0, 0));
    }

    // Двигаем камеру вправо
    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(-TRANSLATION, 0, 0));
    }

    // Двигаем камеру вверх
    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, TRANSLATION, 0));
    }

    // Двигаем камеру вниз
    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, -TRANSLATION, 0));
    }
}
