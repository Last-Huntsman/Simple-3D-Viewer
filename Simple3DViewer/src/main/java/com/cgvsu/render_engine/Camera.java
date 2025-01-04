package com.cgvsu.render_engine;

import com.cgvsu.math.matrices.Matrix4x4;
import com.cgvsu.math.vectors.Vector2f;
import com.cgvsu.math.vectors.Vector3f;

public class Camera {

    private Vector3f position;
    private Vector3f target;
    private Vector2f rotation;
    private final float fov;
    private float aspectRatio;
    private final float nearPlane;
    private final float farPlane;

    public Camera(
            final Vector2f rotation,
            final Vector3f position,
            final Vector3f target,
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        this.rotation = rotation;
        this.position = position;
        this.target = target;
        this.fov = fov;
        this.aspectRatio = aspectRatio;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getTarget() {
        return target;
    }

    public Vector2f getRotation() {
        return rotation;
    }

    public Matrix4x4 getViewMatrix() {
        return GraphicConveyor.lookAt(position, target);
    }

    public Matrix4x4 getProjectionMatrix() {
        return GraphicConveyor.perspective(fov, aspectRatio, nearPlane, farPlane);
    }

    public void setAspectRatio(final float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    /**
     * Методы для свободного перемещения камеры
     */

    public void moveForwardWithoutTrigger(float distance) {
        Vector3f direction = calculateDirectionWithoutTrigger();
        direction.scale(-distance);
        position.add(direction);
        target.add(direction);
    }

    public void moveBackwardWithoutTrigger(float distance) {
        moveForwardWithoutTrigger(-distance);
    }

    public void moveRightWithoutTrigger(float distance) {
        Vector3f direction = calculateRightVectorWithoutTrigger();
        direction.scale(-distance);
        position.add(direction);
        target.add(direction);
    }

    public void moveLeftWithoutTrigger(float distance) {
        moveRightWithoutTrigger(-distance);
    }

    public void moveUpWithoutTrigger(float distance) {
        Vector3f up = new Vector3f(0, 1, 0);
        up.scale(distance);
        position.add(up);
        target.add(up);
    }

    public void moveDownWithoutTrigger(float distance) {
        moveUpWithoutTrigger(-distance);
    }

    public void rotateWithoutTrigger(float yaw, float pitch) {
        rotation.x += yaw;
        rotation.y += pitch;

        rotation.y = Math.max(-90.0f, Math.min(90.0f, rotation.y));

        Vector3f direction = calculateDirectionWithoutTrigger();
        target.set(
                position.x + direction.x,
                position.y + direction.y,
                position.z + direction.z
        );
    }

    private Vector3f calculateDirectionWithoutTrigger() {
        float cosPitch = (float) Math.cos(Math.toRadians(rotation.y));
        float sinPitch = (float) Math.sin(Math.toRadians(rotation.y));
        float cosYaw = (float) Math.cos(Math.toRadians(rotation.x));
        float sinYaw = (float) Math.sin(Math.toRadians(rotation.x));
        Vector3f res = new Vector3f(
                cosYaw * cosPitch,
                sinPitch,
                sinYaw * cosPitch
        );
        res.normalize();
        return res;
    }

    private Vector3f calculateRightVectorWithoutTrigger() {
        Vector3f direction = calculateDirectionWithoutTrigger();
        Vector3f res = new Vector3f(direction.z, 0, -direction.x);
        res.normalize();
        return res;
    }



    /**
     * Методы для перемещения камеры относительно триггера
     */

    public void moveForwardAroundTrigger(float distance) {
        Vector3f direction = calculateDirectionAroundTrigger();
        direction.scale(distance);
        position.add(direction);
        updateTargetAroundTrigger();
    }

    public void moveBackwardAroundTrigger(float distance) {
        moveForwardAroundTrigger(-distance);
    }

    public void moveRightAroundTrigger(float distance) {
        Vector3f right = calculateRightVectorAroundTrigger();
        right.scale(distance);
        position.add(right);
        updateTargetAroundTrigger();
    }

    public void moveLeftAroundTrigger(float distance) {
        moveRightAroundTrigger(-distance);
    }

    public void moveUpAroundTrigger(float distance) {
        Vector3f up = new Vector3f(0, 1, 0);
        up.scale(distance);
        position.add(up);
        updateTargetAroundTrigger();
    }

    public void moveDownAroundTrigger(float distance) {
        moveUpAroundTrigger(-distance);
    }

    public void rotateAroundTrigger(float yaw, float pitch) {
        rotation.x += yaw;
        rotation.y += pitch;

        rotation.y = Math.max(-90.0f, Math.min(90.0f, rotation.y));

        Vector3f direction = calculateDirectionAroundTrigger();
        position.set(
                target.x - direction.x,
                target.y - direction.y,
                target.z - direction.z
        );
    }

    private Vector3f calculateDirectionAroundTrigger() {
        float cosPitch = (float) Math.cos(Math.toRadians(rotation.y));
        float sinPitch = (float) Math.sin(Math.toRadians(rotation.y));
        float cosYaw = (float) Math.cos(Math.toRadians(rotation.x));
        float sinYaw = (float) Math.sin(Math.toRadians(rotation.x));

        Vector3f direction = new Vector3f(
                cosYaw * cosPitch,
                sinPitch,
                sinYaw * cosPitch
        );
        direction.normalize();
        return direction;
    }

    private Vector3f calculateRightVectorAroundTrigger() {
        Vector3f direction = calculateDirectionAroundTrigger();
        Vector3f up = new Vector3f(0, 1, 0);
        Vector3f right = new Vector3f();
        right.cross(direction, up);
        right.normalize();
        return right;
    }

    private void updateTargetAroundTrigger() {
        Vector3f direction = calculateDirectionAroundTrigger();
        target.set(
                position.x + direction.x,
                position.y + direction.y,
                position.z + direction.z
        );
    }

}
