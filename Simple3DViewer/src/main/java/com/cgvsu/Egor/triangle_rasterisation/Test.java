package com.cgvsu.Egor.triangle_rasterisation;

// Импорт необходимых классов для отрисовки, текстур, математических операций и работы с JavaFX

import com.cgvsu.Egor.triangle_rasterisation.color.GradientTexture;
import com.cgvsu.Egor.triangle_rasterisation.color.MonotoneTexture;
import com.cgvsu.Egor.triangle_rasterisation.rasterisers.TriangleRasterisator;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

/**
 * Класс для тестирования отрисовки треугольников с использованием JavaFX.
 */
public class Test extends Application {

    // Вершины треугольника
    private Point2D p1;
    private Point2D p2;
    private Point2D p3;

    /**
     * Главный метод для запуска приложения.
     * @param args аргументы командной строки
     */
    public static void main(String args[]) {
        launch(args);
    }

    /**
     * Метод для отображения случайного треугольника на холсте.
     * @param gc Графический контекст, используемый для рисования на холсте.
     */
    public void showTriangle(GraphicsContext gc) {
        // Очистка холста перед отрисовкой нового треугольника
        gc.clearRect(0, 0, 1000, 1000);

        // Растеризатор для отрисовки треугольника
        TriangleRasterisator tr = new TriangleRasterisator(gc.getPixelWriter());

        // Генератор случайных чисел для координат вершин
        Random rnd = new Random();

        // Однотонная текстура (не используется в этом методе)
        MonotoneTexture mt = new MonotoneTexture(Color.BLACK);

        // Градиентная текстура для треугольника
        GradientTexture gt = new GradientTexture(Color.CYAN, Color.YELLOW, Color.BLUE);

        // Генерация случайных точек для вершин треугольника
        p1 = new Point2D(rnd.nextInt(1000), rnd.nextInt(1000));
        p2 = new Point2D(rnd.nextInt(1000), rnd.nextInt(1000));
        p3 = new Point2D(rnd.nextInt(1000), rnd.nextInt(1000));

        // Отрисовка треугольника с помощью растеризатора
//        tr.draw(p1, p2, p3, gt);
    }

    /**
     * Основной метод JavaFX, вызываемый при запуске приложения.
     * @param scene объект Stage для настройки окна.
     */
    @Override
    public void start(Stage scene) throws Exception {
        // Установка заголовка окна
        scene.setTitle("Test");

        // Создание группы для размещения холста
        Group root = new Group();

        // Создание холста размером 1000x1000
        Canvas canvas = new Canvas(1000, 1000);

        // Получение графического контекста для рисования
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Отображение первого треугольника
        showTriangle(gc);

        // Добавление холста в группу
        root.getChildren().add(canvas);

        // Установка сцены и отображение окна
        scene.setScene(new Scene(root));
        scene.show();

        // Обработчик события нажатия клавиш
        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                // Закрытие окна при нажатии клавиши ESCAPE
                if (t.getCode() == KeyCode.ESCAPE) {
                    Stage sb = (Stage) scene.getScene().getWindow();
                    sb.close();
                }
            }
        });

        // Обработчик события нажатия пробела
        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                // При нажатии пробела очищается группа и рисуется новый треугольник
                if (t.getCode() == KeyCode.SPACE) {
                    root.getChildren().clear(); // Очистка группы
                    showTriangle(gc);           // Отображение нового треугольника
                    root.getChildren().add(canvas); // Повторное добавление холста
                }
            }
        });

    }
}
