package com.cgvsu.render_engine;

import com.cgvsu.math.matrices.Matrix4x4;
import com.cgvsu.math.vectors.Vector3f;

import javafx.geometry.Point2D;;

public class GraphicConveyor {

    public static float cos(float angle){
        return (float) Math.cos(angle);
    }

    public static float sin(float angle){
        return (float) Math.sin(angle);
    }

    public static Matrix4x4 scale(float sx, float sy, float sz) {
        return new Matrix4x4(new float[]{
                sx, 0, 0, 0,
                0, sy, 0, 0,
                0, 0, sz, 0,
                0, 0, 0,  1
        });
    }

    public static Matrix4x4 rotate(float alpha, float beta, float gamma) {
        Matrix4x4 rotateZ = new Matrix4x4(new float[]{
                cos(alpha), sin(alpha), 0, 0,
                -sin(alpha), cos(alpha), 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        });
        Matrix4x4 rotateY = new Matrix4x4(new float[]{
                cos(beta), 0, sin(beta), 0,
                0, 1, 0, 0,
                -sin(beta), 0, cos(beta), 0,
                0, 0, 0, 1
        });
        Matrix4x4 rotateX = new Matrix4x4(new float[]{
                1, 0, 0, 0,
                0, cos(gamma), sin(gamma), 0,
                0, -sin(gamma), cos(gamma), 0,
                0, 0, 0, 1
        });

        rotateZ.mul(rotateY);
        rotateZ.mul(rotateX);
        return rotateZ;
    }

    public static Matrix4x4 transform(float tx, float ty, float tz) {
        return new Matrix4x4(new float[]{
                1, 0, 0, tx,
                0, 1, 0, ty,
                0, 0, 1, tz,
                0, 0, 0, 1
        });
    }

    public static Matrix4x4 lookAt(Vector3f eye, Vector3f target) {
        return lookAt(eye, target, new Vector3f(0F, 1.0F, 0F));
    }

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

    public static Vector3f multiplyMatrix4ByVector3(final Matrix4x4 matrix, final Vector3f vertex) {
        final float x = (vertex.x * matrix.elements[0]) + (vertex.y * matrix.elements[1]) + (vertex.z * matrix.elements[2]) + matrix.elements[3];
        final float y = (vertex.x * matrix.elements[4]) + (vertex.y * matrix.elements[5]) + (vertex.z * matrix.elements[6]) + matrix.elements[7];
        final float z = (vertex.x * matrix.elements[8]) + (vertex.y * matrix.elements[9]) + (vertex.z * matrix.elements[10]) + matrix.elements[11];
        final float w = (vertex.x * matrix.elements[12]) + (vertex.y * matrix.elements[13]) + (vertex.z * matrix.elements[14]) + matrix.elements[15];
        return new Vector3f(x / w, y / w, z / w);
    }

    public static Point2D vertexToPoint(final Vector3f vertex) {
        return new Point2D(vertex.x, vertex.y);
    }

    public static Vector3f vertexToBord(final Vector3f vertex, final int width, final int height) {
        return new Vector3f(vertex.x * width + width / 2.0F, -vertex.y * height + height / 2.0F, vertex.z);
    }
}
