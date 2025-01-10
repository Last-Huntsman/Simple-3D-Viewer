
package com.cgvsu.gui;
import com.cgvsu.render_engine.Camera;

import java.util.ArrayList;
import java.util.List;

public class CameraController {
    private final List<Camera> cameras = new ArrayList<>();
    private Camera activeCamera;

    /**
     * Добавляет новую камеру в список.
     *
     * @param camera Новая камера для добавления.
     */
    public void addCamera(Camera camera) {
        if (camera != null) {
            cameras.add(camera);
            if (activeCamera == null) {
                activeCamera = camera; // Устанавливаем первую добавленную камеру активной
            }
        }
    }

    /**
     * Удаляет камеру из списка.
     *
     * @param camera Камера для удаления.
     */
    public void removeCamera(Camera camera) {
        if (camera != null && cameras.remove(camera)) {
            if (camera.equals(activeCamera)) {
                activeCamera = cameras.isEmpty() ? null : cameras.get(0); // Переключаем активную камеру
            }
        }
    }

    /**
     * Возвращает список всех камер.
     *
     * @return Список камер.
     */
    public List<Camera> getCameras() {
        return new ArrayList<>(cameras); // Возвращаем копию списка
    }

    /**
     * Устанавливает активную камеру.
     *
     * @param camera Камера для установки активной.
     */
    public void setActiveCamera(Camera camera) {
        if (camera != null && cameras.contains(camera)) {
            activeCamera = camera;
        }
    }

    /**
     * Возвращает активную камеру.
     *
     * @return Активная камера.
     */
    public Camera getActiveCamera() {
        return activeCamera;
    }

    /**
     * Возвращает камеру по имени.
     *
     * @param name Имя камеры.
     * @return Камера с указанным именем или null, если не найдена.
     */
    public Camera getCameraByName(String name) {
        for (Camera camera : cameras) {
            if (camera.getName().equals(name)) {
                return camera;
            }
        }
        return null;
    }

    /**
     * Удаляет все камеры из списка.
     */
    public void clear() {
        cameras.clear();
        activeCamera = null;
    }
}

