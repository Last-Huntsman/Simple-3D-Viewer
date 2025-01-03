package com.cgvsu.render_engine;

import com.cgvsu.Pavel.math.matrices.Matrix4x4;
import com.cgvsu.Pavel.math.vectors.Vector3f;

/**
 * Класс Camera представляет виртуальную камеру, которая используется для
 * рендеринга сцены в 3D пространстве.
 */
public class Camera {

    private Vector3f position;
    private Vector3f target;
    private final float fov;
    private float aspectRatio;
    private final float nearPlane;
    private final float farPlane;

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
    public Camera(
            final Vector3f position,
            final Vector3f target,
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        this.position = position;
        this.target = target;
        this.fov = fov;
        this.aspectRatio = aspectRatio;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
    }

    public Matrix4x4 getViewMatrix() {
        return GraphicConveyor.lookAt(position, target);
    }

    public Matrix4x4 getProjectionMatrix() {
        return GraphicConveyor.perspective(fov, aspectRatio, nearPlane, farPlane);
    }

    public void movePosition(final Vector3f translation) {
        this.position.add(translation);
    }

    public void movePosition(Vector3f direction, float amount) {
        direction.normalize();
        direction.scale(amount);
        position.add(direction);
        target.add(direction);
    }

    public void rotateAroundTarget(float angleX, float angleY) {
        // Вычисляем новое положение камеры на основе углов
        float radius = position.distance(target);
        float x = (float) (target.x + radius * Math.sin(angleX) * Math.cos(angleY));
        float y = (float) (target.y + radius * Math.sin(angleY));
        float z = (float) (target.z + radius * Math.cos(angleX) * Math.cos(angleY));
        position.set(x, y, z);
    }

    public void rotate(float angleX, float angleY) {
        Vector3f direction = new Vector3f(target);
        direction.sub(position);
        direction.normalize();

        float cosAngleX = (float) Math.cos(angleX);
        float sinAngleX = (float) Math.sin(angleX);

        float newX = direction.x * cosAngleX - direction.z * sinAngleX;
        float newZ = direction.x * sinAngleX + direction.z * cosAngleX;

        direction.x = newX;
        direction.z = newZ;

        float cosAngleY = (float) Math.cos(angleY);
        float sinAngleY = (float) Math.sin(angleY);

        float newY = direction.y * cosAngleY - direction.z * sinAngleY;
        newZ = direction.y * sinAngleY + direction.z * cosAngleY;

        direction.y = newY;
        direction.z = newZ;

        target.set(position.x + direction.x, position.y + direction.y, position.z + direction.z);
    }


    public Vector3f getTarget() {
        return target;
    }

    public void setTarget(final Vector3f target) {
        this.target = target;
    }

    public void setAspectRatio(final float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public Vector3f getPosition() {
        return position;
    }

}
