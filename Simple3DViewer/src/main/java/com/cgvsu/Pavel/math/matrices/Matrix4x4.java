package com.cgvsu.Pavel.math.matrices;

import com.cgvsu.Pavel.math.vectors.Vector3f;
import com.cgvsu.Pavel.math.vectors.Vector4f;

/**
 * Класс Matrix4x4 для работы с матрицами размером 4x4.
 */
public class Matrix4x4 {
    public float[] elements;

    public Matrix4x4() {
        this.elements = new float[] {
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0
        };
    }

    // Конструктор
    public Matrix4x4(float[] elements) {
        if (elements == null || elements.length != 16) {
            throw new IllegalArgumentException("Matrix4x4: некорректные размеры матрицы.");
        }
        this.elements = new float[16];
        System.arraycopy(elements, 0, this.elements, 0, 16);
    }


    public final void set(Matrix3x3 var1) {
        elements[0] = var1.elements[0];
        elements[1] = var1.elements[1];
        elements[2] = var1.elements[2];
        elements[3] = 0.0F;
        elements[4] = var1.elements[3];
        elements[5] = var1.elements[4];
        elements[6] = var1.elements[5];
        elements[7] = 0.0F;
        elements[8] = var1.elements[6];
        elements[9] = var1.elements[7];
        elements[10] = var1.elements[8];
        elements[11] = 0.0F;
        elements[12] = 0.0F;
        elements[13] = 0.0F;
        elements[14] = 0.0F;
        elements[15] = 1.0F;
    }

    public Matrix4x4 multiplyMM(Matrix4x4 m2) {
        float[] result = new float[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i * 4 + j] = 0;
                for (int k = 0; k < 4; k++) {
                    result[i * 4 + j] += this.elements[i * 4 + k] * m2.elements[k * 4 + j];
                }
            }
        }
        return new Matrix4x4(result);
    }

    public Matrix4x4 multiplyMM(Matrix4x4 m1, Matrix4x4 m2) {
        float[] result = new float[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i * 4 + j] = 0;
                for (int k = 0; k < 4; k++) {
                    result[i * 4 + j] += m1.elements[i * 4 + k] * m2.elements[k * 4 + j];
                }
            }
        }
        System.arraycopy(result, 0, this.elements, 0, 16);
        return this;
    }



    public Vector3f multiplyMV(Vector3f v3) {
        float x = elements[0] * v3.x + elements[1] * v3.y + elements[2] * v3.z + elements[3];
        float y = elements[4] * v3.x + elements[5] * v3.y + elements[6] * v3.z + elements[7];
        float z = elements[8] * v3.x + elements[9] * v3.y + elements[10] * v3.z + elements[11];
        return new Vector3f(x, y, z);
    }


    
    public Vector4f multiplyMV(Vector4f v2) {
        float[] result = new float[4];
        for (int i = 0; i < 4; i++) {
            result[i] = this.elements[i * 4] * v2.x +
                    this.elements[i * 4 + 1] * v2.y +
                    this.elements[i * 4 + 2] * v2.z +
                    this.elements[i * 4 + 3] * v2.w;

        }
        return new Vector4f(result[0], result[1], result[2], result[3]);
    }


    public void setElement(int row, int col, float value) {
        if (row < 0 || row >= 4 || col < 0 || col >= 4) {
            throw new IndexOutOfBoundsException("Индексы строки и столбца должны быть в диапазоне [0, 3].");
        }
        elements[row * 4 + col] = value;
    }

    public void setColumn(int col, float[] values) {
        if (col < 0 || col >= 4) {
            throw new IndexOutOfBoundsException("Индекс столбца должен быть в диапазоне [0, 3].");
        }
        if (values == null || values.length != 4) {
            throw new IllegalArgumentException("Массив должен содержать ровно 4 элемента.");
        }
        elements[col] = values[0];
        elements[4 + col] = values[1];
        elements[2 * 4 + col] = values[2];
        elements[3 * 4 + col] = values[3];
    }


    public void setColumn(int col, Vector4f vector) {
        if (col < 0 || col >= 4) {
            throw new IndexOutOfBoundsException("Индекс столбца должен быть в диапазоне [0, 3].");
        }
        elements[col] = vector.x;
        elements[4 + col] = vector.y;
        elements[2 * 4 + col] = vector.z;
        elements[3 * 4 + col] = vector.w;
    }

    // Реализация методов интерфейса Matrix
    
    public Matrix4x4 add(Matrix4x4 m2) {
        float[] result = new float[16];
        for (int i = 0; i < 16; i++) {
            result[i] = this.elements[i] + m2.elements[i];
        }
        return new Matrix4x4(result);
    }

    
    public Matrix4x4 subtract(Matrix4x4 m2) {
        float[] result = new float[16];
        for (int i = 0; i < 16; i++) {
            result[i] = this.elements[i] - m2.elements[i];
        }
        return new Matrix4x4(result);
    }

    
    public Matrix4x4 transpose() {
        float[] result = new float[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[j * 4 + i] = this.elements[i * 4 + j];
            }
        }
        return new Matrix4x4(result);
    }


    public float determinant() {
        float det = 0;
        for (int i = 0; i < 4; i++) {
            det += (i % 2 == 0 ? 1 : -1) * elements[i] * minorDeterminant(i);
        }
        return det;
    }

    private float minorDeterminant(int col) {
        float[] minor = getMinor(elements, 0, col);
        return determinant3x3(minor);
    }

    public Matrix4x4 identity() {
        return new Matrix4x4(new float[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        });
    }


    public Matrix4x4 inverse() {
        float det = determinant();
        if (Math.abs(det) < 1e-6) {
            throw new IllegalArgumentException("Matrix4x4X.inverse: матрица вырождена, обратной нет.");
        }
        float[] cofactor = new float[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                float[] minor = getMinor(elements, i, j);
                cofactor[i * 4 + j] = ((i + j) % 2 == 0 ? 1 : -1) * determinant3x3(minor);
            }
        }
        float[] adj = new float[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adj[j * 4 + i] = cofactor[i * 4 + j];
            }
        }
        float[] inverse = new float[16];
        for (int i = 0; i < 16; i++) {
            inverse[i] = adj[i] / det;
        }
        return new Matrix4x4(inverse);
    }

    private float[] getMinor(float[] matrix, int row, int col) {
        float[] minor = new float[9];
        int minorIndex = 0;
        for (int i = 0; i < 4; i++) {
            if (i == row) continue;
            for (int j = 0; j < 4; j++) {
                if (j == col) continue;
                minor[minorIndex++] = matrix[i * 4 + j];
            }
        }
        return minor;
    }

    private float determinant3x3(float[] matrix) {
        return matrix[0] * (matrix[4] * matrix[8] - matrix[5] * matrix[7])
                - matrix[1] * (matrix[3] * matrix[8] - matrix[5] * matrix[6])
                + matrix[2] * (matrix[3] * matrix[7] - matrix[4] * matrix[6]);
    }

    
    public String toString() {
        StringBuilder builder = new StringBuilder("Matrix4x4X{\n");
        for (int i = 0; i < 4; i++) {
            builder.append("  [");
            for (int j = 0; j < 4; j++) {
                builder.append(elements[i * 4 + j]).append(j < 3 ? ", " : "");
            }
            builder.append("]\n");
        }
        builder.append("}");
        return builder.toString();
    }

    
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Matrix4x4 matrix = (Matrix4x4) obj;
        for (int i = 0; i < 16; i++) {
            if (Math.abs(this.elements[i] - matrix.elements[i]) > 1e-6) {
                return false;
            }
        }
        return true;
    }


}