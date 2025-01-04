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

    public void moveForward(float distance) {
        Vector3f direction = calculateDirection();
        direction.scale(-distance);
        position.add(direction);
        target.add(direction);
    }

    public void moveBackward(float distance) {
        moveForward(-distance);
    }

    public void moveRight(float distance) {
        Vector3f direction = calculateRightVector();
        direction.scale(-distance);
        position.add(direction);
        target.add(direction);
    }

    public void moveLeft(float distance) {
        moveRight(-distance);
    }

    public void moveUp(float distance) {
        Vector3f up = new Vector3f(0, 1, 0);
        up.scale(distance);
        position.add(up);
        target.add(up);
    }

    public void moveDown(float distance) {
        moveUp(-distance);
    }

    public void rotate(float yaw, float pitch) {
        rotation.x += yaw;
        rotation.y += pitch;

        rotation.y = Math.max(-90.0f, Math.min(90.0f, rotation.y));

        Vector3f direction = calculateDirection();
        target.set(
                position.x + direction.x,
                position.y + direction.y,
                position.z + direction.z
        );
    }


    private Vector3f calculateDirection() {
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

    private Vector3f calculateRightVector() {
        Vector3f direction = calculateDirection();
        Vector3f res = new Vector3f(direction.z, 0, -direction.x);
        res.normalize();
        return res;
    }

}
