package com.cgvsu.render_engine;

import com.cgvsu.math.matrices.Matrix4x4;
import com.cgvsu.math.vectors.Vector2f;
import com.cgvsu.math.vectors.Vector3f;

import java.awt.*;

// Класс Camera представляет камеру, используемую для отображения трехмерной сцены.
public class Camera {

    // Положение камеры в пространстве.
    private final Vector3f position;

    // Точка, на которую смотрит камера.
    private final Vector3f target;

    // Вращение камеры в пространстве: yaw (x) и pitch (y).
    private final Vector2f rotation;

    // Поле зрения (угол обзора в градусах).
    private final float fov;

    // Соотношение сторон экрана (ширина / высота).
    private float aspectRatio;

    // Ближняя плоскость отсечения.
    private final float nearPlane;

    // Дальняя плоскость отсечения.
    private final float farPlane;
    private final String cameraName;

    // Конструктор камеры, инициализирующий все параметры.
    public Camera(
            final Vector2f rotation,
            final Vector3f position,
            final Vector3f target,
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane,
            String cameraName) {
        this.rotation = rotation; // Угол поворота камеры.
        this.position = position; // Позиция камеры.
        this.target = target;     // Точка, на которую камера направлена.
        this.fov = fov;           // Угол обзора.
        this.aspectRatio = aspectRatio; // Соотношение сторон экрана.
        this.nearPlane = nearPlane; // Ближняя плоскость отсечения.
        this.farPlane = farPlane;
        this.cameraName = cameraName;
    }

    // Получить текущее положение камеры.
    public Vector3f getPosition() {
        return position;
    }

    // Получить текущую точку, на которую смотрит камера.
    public Vector3f getTarget() {
        return target;
    }

    // Получить текущее вращение камеры.
    public Vector2f getRotation() {
        return rotation;
    }

    // Вычислить матрицу вида для камеры.
    public Matrix4x4 getViewMatrix() {
        return GraphicConveyor.lookAt(position, target);
    }

    // Вычислить матрицу проекции для камеры.
    public Matrix4x4 getProjectionMatrix() {
        return GraphicConveyor.perspective(fov, aspectRatio, nearPlane, farPlane);
    }

    // Установить новое соотношение сторон экрана.
    public void setAspectRatio(final float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    /**
     * Методы для свободного перемещения камеры
     */

    // Переместить камеру вперёд относительно текущего направления.
    public void moveForwardWithoutTrigger(float distance) {
        Vector3f direction = calculateDirectionWithoutTrigger(); // Вычисляем направление камеры.
        direction.scale(distance); // Умножаем направление на расстояние.
        position.add(direction); // Изменяем положение камеры.
        target.add(direction);   // Также сдвигаем точку цели.
    }

    // Переместить камеру назад.
    public void moveBackwardWithoutTrigger(float distance) {
        moveForwardWithoutTrigger(-distance); // Движение назад — это движение вперёд с отрицательным расстоянием.
    }

    // Переместить камеру вправо.
    public void moveRightWithoutTrigger(float distance) {
        Vector3f direction = calculateRightVectorWithoutTrigger(); // Вычисляем вектор направления вправо.
        direction.scale(-distance); // Масштабируем вектор.
        position.add(direction); // Изменяем положение камеры.
        target.add(direction);   // Сдвигаем цель.
    }

    // Переместить камеру влево.
    public void moveLeftWithoutTrigger(float distance) {
        moveRightWithoutTrigger(-distance); // Аналогично движению вправо, но с отрицательным расстоянием.
    }

    // Переместить камеру вверх.
    public void moveUpWithoutTrigger(float distance) {
        Vector3f up = new Vector3f(0, -1, 0); // Направление вверх (вдоль оси Y).
        up.scale(distance); // Масштабируем.
        position.add(up); // Изменяем положение камеры.
        target.add(up);   // Сдвигаем цель.
    }

    // Переместить камеру вниз.
    public void moveDownWithoutTrigger(float distance) {
        moveUpWithoutTrigger(-distance); // Аналогично движению вверх, но с отрицательным расстоянием.
    }

    // Ограничить вертикальное вращение камеры.
    private void clampPitch() {
        // Угол поворота (pitch) ограничивается диапазоном [-90, 90] градусов.
        rotation.y = Math.max(-89.0f, Math.min(89.0f, rotation.y));
    }

    // Вращение камеры без триггеров.
    public void rotateWithoutTrigger(float yaw, float pitch) {
        rotation.x += yaw;   // Изменяем угол yaw (горизонтальное вращение).
        rotation.y += pitch; // Изменяем угол pitch (вертикальное вращение).
        clampPitch();        // Ограничиваем вертикальное вращение.

        // Пересчитываем направление камеры на основе новых углов.
        Vector3f direction = calculateDirectionWithoutTrigger();
        target.set(
                position.x + direction.x,
                position.y + direction.y,
                position.z + direction.z
        );
    }

    // Приближение или отдаление камеры (зум).
    public void zoom(float amount) {
        // Вычисляем новое положение камеры, двигаясь в направлении текущего взгляда.
        Vector3f direction = calculateDirectionWithoutTrigger(); // Направление взгляда камеры.
        direction.scale(-amount); // Масштабируем вектор.
        position.add(direction); // Изменяем позицию камеры.
        target.add(direction);   // Сдвигаем цель.
    }

    // Вычислить направление взгляда камеры.
    private Vector3f calculateDirectionWithoutTrigger() {
        // Вычисляем направление на основе углов yaw (x) и pitch (y).
        float cosPitch = (float) Math.cos(Math.toRadians(rotation.y)); // Косинус угла pitch.
        float sinPitch = (float) Math.sin(Math.toRadians(rotation.y)); // Синус угла pitch.
        float cosYaw = (float) Math.cos(Math.toRadians(rotation.x));   // Косинус угла yaw.
        float sinYaw = (float) Math.sin(Math.toRadians(rotation.x));   // Синус угла yaw.

        // Направление камеры.
        Vector3f res = new Vector3f(
                -cosYaw * cosPitch,
                   -sinPitch,
                sinYaw * cosPitch
        );
        res.normalize(); // Нормализуем вектор для единичной длины.
        return res;
    }

    // Вычислить вектор "вправо" относительно текущего взгляда камеры.
    private Vector3f calculateRightVectorWithoutTrigger() {
        Vector3f direction = calculateDirectionWithoutTrigger(); // Текущее направление взгляда камеры.
        Vector3f res = new Vector3f(direction.z, 0, -direction.x); // Перпендикулярный вектор.
        res.normalize(); // Нормализуем вектор.
        return res;
    }

    public Point projectTo2D(Vector3f point3D, double screenWidth, double screenHeight) {
        // Угол поля зрения в радианах
        float fovRadians = (float) Math.toRadians(fov);

        // Фактор масштабирования
        float scale = (float) (1 / Math.tan(fovRadians / 2));

        // Координаты из Vector3f
        float x = point3D.x;
        float y = point3D.y;
        float z = point3D.z;

        // Преобразование координат
        float projectedX = x / (-z) * scale * aspectRatio;
        float projectedY = y / (-z) * scale;

        // Преобразование в координаты экрана
        int screenX = (int) ((projectedX + 1) * 0.5 * screenWidth);
        int screenY = (int) ((1 - projectedY) * 0.5 * screenHeight);

        return new Point(screenX, screenY);
    }

    public String getName(){
        return cameraName;
    }
}
