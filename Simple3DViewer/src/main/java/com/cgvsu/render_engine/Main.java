package com.cgvsu.render_engine;

import com.cgvsu.Pavel.math.vectors.Vector3f;

public class Main {
    public static void main(String[] args) {
        Vector3f position = new Vector3f(0, 0, 5);
        Vector3f target = new Vector3f(0, 0, 0);
        Vector3f direction = new Vector3f(0, 0, -1);

        Camera camera = new Camera(
                new Vector3f(0, 0, 5), // Позиция камеры
                new Vector3f(0, 0, 0), // Точка, на которую смотрит камера
                70,                   // Поле зрения
                16f / 9f,             // Соотношение сторон
                0.1f,                 // Ближняя плоскость отсечения
                100f                  // Дальняя плоскость отсечения
        );

        camera.movePosition(direction, 2.0f);

        System.out.println("Camera Position: " + camera.getPosition());
        System.out.println("Camera Target: " + camera.getTarget());
    }
}
