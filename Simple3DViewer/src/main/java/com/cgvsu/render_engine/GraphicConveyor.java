package com.cgvsu.render_engine;

import com.cgvsu.Pavel.math.matrices.Matrix3x3;
import com.cgvsu.Pavel.math.matrices.Matrix4x4;
import com.cgvsu.Pavel.math.vectors.Vector3f;

import javafx.geometry.Point2D;;

/**
 * Утилитарный класс для выполнения различных графических преобразований и операций.
 */
public class GraphicConveyor {

    /**
     * Создает единичную матрицу трансформации, которая не выполняет никаких преобразований.
     *
     * @return Единичная матрица (4x4).
     */
    public static Matrix4x4 rotateScaleTranslate() {
        float[] matrix = new float[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1};
        return new Matrix4x4(matrix);
    }

    /**
     * Создает матрицу вида ("смотри на") для камеры.
     * По умолчанию вектор "вверх" равен (0, 1, 0).
     *
     * @param eye    Позиция камеры (точка наблюдения).
     * @param target Точка, на которую смотрит камера.
     * @return Матрица вида (4x4).
     */
    public static Matrix4x4 lookAt(Vector3f eye, Vector3f target) {
        return lookAt(eye, target, new Vector3f(0F, 1.0F, 0F));
    }

    /**
     * Создает матрицу вида ("смотри на") для камеры с указанным направлением "вверх".
     *
     * @param eye    Позиция камеры (точка наблюдения).
     * @param target Точка, на которую смотрит камера.
     * @param up     Вектор, определяющий направление "вверх".
     * @return Матрица вида (4x4).
     */
    public static Matrix4x4 lookAt(Vector3f eye, Vector3f target, Vector3f up) {
        Vector3f resultX = new Vector3f();
        Vector3f resultY = new Vector3f();
        Vector3f resultZ = new Vector3f();

        resultZ.sub(target, eye);
        resultX.cross(up, resultZ);
        resultY.cross(resultZ, resultX);

        resultX.normalize();
        resultY.normalize();
        resultZ.normalize();

        Matrix4x4 T = new Matrix4x4(
                1, 0, 0, -eye.x,
                0, 1, 0, -eye.y,
                0, 0, 1, -eye.z,
                0, 0, 0, 1
        );
        Matrix4x4 P = new Matrix4x4(
                resultX.x, resultX.y, resultX.z, 0,
                resultY.x, resultY.y, resultY.z, 0,
                resultZ.x, resultZ.y, resultZ.z, 0,
                0, 0, 0, 1
        );
        Matrix4x4 result = new Matrix4x4();
        result.mul(P, T);
        return result;
    }

    /**
     * Создает перспективную проекционную матрицу.
     *
     * @param fov         Угол обзора (в радианах).
     * @param aspectRatio Соотношение сторон экрана.
     * @param nearPlane   Ближняя плоскость отсечения.
     * @param farPlane    Дальняя плоскость отсечения.
     * @return Матрица перспективной проекции (4x4).
     */
    public static Matrix4x4 perspective(final float fov, final float aspectRatio, final float nearPlane, final float farPlane) {
        Matrix4x4 result = new Matrix4x4();

        float tangentMinusOneDegree = (float) (1.0F / (Math.tan(fov * 0.5F))); // Нужно ли делить на 2?  Вроде как да.

        result.elements[0] = tangentMinusOneDegree;
        result.elements[5] = tangentMinusOneDegree/aspectRatio;
        result.elements[10] = (farPlane + nearPlane) / (farPlane - nearPlane);
        result.elements[11] = 2 * (nearPlane * farPlane) / (nearPlane - farPlane);
        result.elements[14] = 1;
        return result;
    }

    /**
     * Умножает матрицу 4x4 на вектор-столбец 3D, возвращая преобразованный вектор.
     *
     * @param matrix Матрица 4x4.
     * @param vertex Вектор 3D (координаты вершины).
     * @return Преобразованный вектор 3D.
     */
    public static Vector3f multiplyMatrix4ByVector3(final Matrix4x4 matrix, final Vector3f vertex) {
        final float x = (vertex.x * matrix.elements[0]) + (vertex.y * matrix.elements[1]) + (vertex.z * matrix.elements[2]) + matrix.elements[3];
        final float y = (vertex.x * matrix.elements[4]) + (vertex.y * matrix.elements[5]) + (vertex.z * matrix.elements[6]) + matrix.elements[7];
        final float z = (vertex.x * matrix.elements[8]) + (vertex.y * matrix.elements[9]) + (vertex.z * matrix.elements[10]) + matrix.elements[11];
        final float w = (vertex.x * matrix.elements[12]) + (vertex.y * matrix.elements[13]) + (vertex.z * matrix.elements[14]) + matrix.elements[15];
        return new Vector3f(x / w, y / w, z / w);
    }

    /**
     * Возвращает точку на основе вектора
     *
     * @param vertex Координаты вершины.
     * @return Экранные координаты точки.
     */
    public static Point2D vertexToPoint(final Vector3f vertex) {
        return new Point2D(vertex.x, vertex.y);
    }

    public static Vector3f vertexToBord(final Vector3f vertex, final int width, final int height) {
        return new Vector3f(vertex.x * width + width / 2.0F, -vertex.y * height + height / 2.0F, vertex.z);
    }
}
