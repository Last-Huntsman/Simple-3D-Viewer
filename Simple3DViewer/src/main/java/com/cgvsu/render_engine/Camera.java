package com.cgvsu.render_engine;

import com.cgvsu.Pavel.math.matrices.Matrix4x4;
import com.cgvsu.Pavel.math.vectors.Vector3f;

/**
 * Класс Camera представляет виртуальную камеру, которая используется для
 * рендеринга сцены в 3D пространстве.
 */
public class Camera {

    // Позиция камеры в мировых координатах
    private Vector3f position;

    // Целевая точка, на которую смотрит камера
    private Vector3f target;

    // Угол обзора (в радианах)
    private float fov;

    // Соотношение сторон кадра (ширина / высота)
    private float aspectRatio;

    // Расстояние до ближней плоскости отсечения
    private float nearPlane;

    // Расстояние до дальней плоскости отсечения
    private float farPlane;

    /**
     * Конструктор для создания камеры с заданными параметрами.
     *
     * @param position    Позиция камеры
     * @param target      Целевая точка камеры
     * @param fov         Угол обзора (в радианах)
     * @param aspectRatio Соотношение сторон кадра
     * @param nearPlane   Ближняя плоскость отсечения
     * @param farPlane    Дальняя плоскость отсечения
     */
    public Camera(final Vector3f position, final Vector3f target, final float fov, final float aspectRatio, final float nearPlane, final float farPlane) {
        this.position = position;
        this.target = target;
        this.fov = fov;
        this.aspectRatio = aspectRatio;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
    }

    /**
     * Устанавливает новую позицию камеры.
     *
     * @param position Новая позиция камеры
     */
    public void setPosition(final Vector3f position) {
        this.position = position;
    }

    /**
     * Устанавливает новую целевую точку камеры.
     *
     * @param target Новая целевая точка
     */
    public void setTarget(final Vector3f target) {
        this.target = target;
    }

    /**
     * Устанавливает новое соотношение сторон кадра.
     *
     * @param aspectRatio Новое соотношение сторон
     */
    public void setAspectRatio(final float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    /**
     * Возвращает текущую позицию камеры.
     *
     * @return Позиция камеры
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * Возвращает текущую целевую точку камеры.
     *
     * @return Целевая точка камеры
     */
    public Vector3f getTarget() {
        return target;
    }

    /**
     * Перемещает камеру на указанное смещение.
     *
     * @param translation Вектор смещения
     */
    public void movePosition(final Vector3f translation) {
        this.position.add(translation);
    }

    /**
     * Перемещает целевую точку камеры на указанное смещение.
     *
     * @param translation Вектор смещения
     */
    public void moveTarget(final Vector3f translation) {
        this.target.add(translation);
    }

    /**
     * Создает и возвращает матрицу вида для камеры.
     *
     * @return Матрица вида, определяющая ориентацию камеры в пространсве
     */
    Matrix4x4 getViewMatrix() {
        return GraphicConveyor.lookAt(position, target);
    }

    /**
     * Создает и возвращает проекционную матрицу для камеры.
     *
     * @return Проекционная матрица, основанная на параметрах камеры
     */
    Matrix4x4 getProjectionMatrix() {
        return GraphicConveyor.perspective(fov, aspectRatio, nearPlane, farPlane);
    }
}
