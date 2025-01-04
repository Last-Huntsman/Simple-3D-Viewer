package com.cgvsu.math;

import com.cgvsu.math.matrices.Matrix4x4;
import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.math.vectors.Vector4f;
import com.cgvsu.io.objReader.ObjReader;
import com.cgvsu.model.Model;
import com.cgvsu.render_engine.GraphicConveyor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

public class AffineTests {
    @Test
    public void testAffineScaleX() throws IOException {
        Model model = ObjReader.read("./models/3DModels/CaracalCube/caracal_cube.obj");
        ArrayList<Vector3f> originVertices = new ArrayList<>();
        for (Vector3f vertex : model.vertices) {
            originVertices.add(new Vector3f(vertex.x * 2, vertex.y, vertex.z));
        }

        model.scale = new Vector3f(2f, 1f, 1f);
        Matrix4x4 modelMatrix = model.getModelMatrix();
        ArrayList<Vector3f> resultVertices = new ArrayList<>();
        for (Vector3f vertex : model.vertices){
            Vector4f vertex4 = modelMatrix.mulV(new Vector4f(vertex.x, vertex.y, vertex.z, 1));
            resultVertices.add(new Vector3f(vertex4.x, vertex4.y, vertex4.z));
        }

        for (int i = 0; i < originVertices.size(); i++){
            Assertions.assertEquals(originVertices.get(i), resultVertices.get(i));
        }
    }

    @Test
    public void testAffineScaleY() throws IOException {
        Model model = ObjReader.read("./models/3DModels/CaracalCube/caracal_cube.obj");
        ArrayList<Vector3f> originVertices = new ArrayList<>();
        for (Vector3f vertex : model.vertices) {
            originVertices.add(new Vector3f(vertex.x, vertex.y * 2, vertex.z));
        }

        model.scale = new Vector3f(1f, 2f, 1f);
        Matrix4x4 modelMatrix = model.getModelMatrix();
        ArrayList<Vector3f> resultVertices = new ArrayList<>();
        for (Vector3f vertex : model.vertices){
            Vector4f vertex4 = modelMatrix.mulV(new Vector4f(vertex.x, vertex.y, vertex.z, 1));
            resultVertices.add(new Vector3f(vertex4.x, vertex4.y, vertex4.z));
        }

        for (int i = 0; i < originVertices.size(); i++){
            Assertions.assertEquals(originVertices.get(i), resultVertices.get(i));
        }
    }

    @Test
    public void testAffineScaleZ() throws IOException {
        Model model = ObjReader.read("./models/3DModels/CaracalCube/caracal_cube.obj");
        ArrayList<Vector3f> originVertices = new ArrayList<>();
        for (Vector3f vertex : model.vertices) {
            originVertices.add(new Vector3f(vertex.x, vertex.y, vertex.z * 2));
        }

        model.scale = new Vector3f(1f, 1f, 2f);
        Matrix4x4 modelMatrix = model.getModelMatrix();
        ArrayList<Vector3f> resultVertices = new ArrayList<>();
        for (Vector3f vertex : model.vertices){
            Vector4f vertex4 = modelMatrix.mulV(new Vector4f(vertex.x, vertex.y, vertex.z, 1));
            resultVertices.add(new Vector3f(vertex4.x, vertex4.y, vertex4.z));
        }

        for (int i = 0; i < originVertices.size(); i++){
            Assertions.assertEquals(originVertices.get(i), resultVertices.get(i));
        }
    }

    @Test
    public void testAffineScaleXY() throws IOException {
        Model model = ObjReader.read("./models/3DModels/CaracalCube/caracal_cube.obj");
        ArrayList<Vector3f> originVertices = new ArrayList<>();
        for (Vector3f vertex : model.vertices) {
            originVertices.add(new Vector3f(vertex.x * 2, vertex.y * 2, vertex.z));
        }

        model.scale = new Vector3f(2f, 2f, 1f);
        Matrix4x4 modelMatrix = model.getModelMatrix();
        ArrayList<Vector3f> resultVertices = new ArrayList<>();
        for (Vector3f vertex : model.vertices){
            Vector4f vertex4 = modelMatrix.mulV(new Vector4f(vertex.x, vertex.y, vertex.z, 1));
            resultVertices.add(new Vector3f(vertex4.x, vertex4.y, vertex4.z));
        }

        for (int i = 0; i < originVertices.size(); i++){
            Assertions.assertEquals(originVertices.get(i), resultVertices.get(i));
        }
    }

    @Test
    public void testAffineScaleXZ() throws IOException {
        Model model = ObjReader.read("./models/3DModels/CaracalCube/caracal_cube.obj");
        ArrayList<Vector3f> originVertices = new ArrayList<>();
        for (Vector3f vertex : model.vertices) {
            originVertices.add(new Vector3f(vertex.x * 2, vertex.y, vertex.z * 2));
        }

        model.scale = new Vector3f(2f, 1f, 2f);
        Matrix4x4 modelMatrix = model.getModelMatrix();
        ArrayList<Vector3f> resultVertices = new ArrayList<>();
        for (Vector3f vertex : model.vertices){
            Vector4f vertex4 = modelMatrix.mulV(new Vector4f(vertex.x, vertex.y, vertex.z, 1));
            resultVertices.add(new Vector3f(vertex4.x, vertex4.y, vertex4.z));
        }

        for (int i = 0; i < originVertices.size(); i++){
            Assertions.assertEquals(originVertices.get(i), resultVertices.get(i));
        }
    }

    @Test
    public void testAffineScaleYZ() throws IOException {
        Model model = ObjReader.read("./models/3DModels/CaracalCube/caracal_cube.obj");
        ArrayList<Vector3f> originVertices = new ArrayList<>();
        for (Vector3f vertex : model.vertices) {
            originVertices.add(new Vector3f(vertex.x, vertex.y * 2, vertex.z * 2));
        }

        model.scale = new Vector3f(1f, 2f, 2f);
        Matrix4x4 modelMatrix = model.getModelMatrix();
        ArrayList<Vector3f> resultVertices = new ArrayList<>();
        for (Vector3f vertex : model.vertices){
            Vector4f vertex4 = modelMatrix.mulV(new Vector4f(vertex.x, vertex.y, vertex.z, 1));
            resultVertices.add(new Vector3f(vertex4.x, vertex4.y, vertex4.z));
        }

        for (int i = 0; i < originVertices.size(); i++){
            Assertions.assertEquals(originVertices.get(i), resultVertices.get(i));
        }
    }

    @Test
    public void testAffineScaleXYZ() throws IOException {
        Model model = ObjReader.read("./models/3DModels/CaracalCube/caracal_cube.obj");
        ArrayList<Vector3f> originVertices = new ArrayList<>();
        for (Vector3f vertex : model.vertices) {
            originVertices.add(new Vector3f(vertex.x * 2, vertex.y * 2, vertex.z * 2));
        }

        model.scale = new Vector3f(2f, 2f, 2f);
        Matrix4x4 modelMatrix = model.getModelMatrix();
        ArrayList<Vector3f> resultVertices = new ArrayList<>();
        for (Vector3f vertex : model.vertices){
            Vector4f vertex4 = modelMatrix.mulV(new Vector4f(vertex.x, vertex.y, vertex.z, 1));
            resultVertices.add(new Vector3f(vertex4.x, vertex4.y, vertex4.z));
        }

        for (int i = 0; i < originVertices.size(); i++){
            Assertions.assertEquals(originVertices.get(i), resultVertices.get(i));
        }
    }

    @Test
    public void testAffineRotationZ() throws IOException {
        float phi = (float) (Math.PI) / 4;
        Model model = ObjReader.read("./models/3DModels/CaracalCube/caracal_cube.obj");
        ArrayList<Vector3f> originVertices = new ArrayList<>();
        for (Vector3f vertex : model.vertices) {
            originVertices.add(new Vector3f(
                    vertex.x * GraphicConveyor.cos(phi) + vertex.y * GraphicConveyor.sin(phi),
                    -vertex.x * GraphicConveyor.sin(phi) + vertex.y * GraphicConveyor.cos(phi),
                    vertex.z
            ));
        }

        model.rotation = new Vector3f(0f, 0f, phi);
        Matrix4x4 modelMatrix = model.getModelMatrix();
        ArrayList<Vector3f> resultVertices = new ArrayList<>();
        for (Vector3f vertex : model.vertices){
            Vector4f vertex4 = modelMatrix.mulV(new Vector4f(vertex.x, vertex.y, vertex.z, 1));
            resultVertices.add(new Vector3f(vertex4.x, vertex4.y, vertex4.z));
        }

        for (int i = 0; i < originVertices.size(); i++){
            Assertions.assertEquals(originVertices.get(i), resultVertices.get(i));
        }
    }

    @Test
    public void testAffineRotationY() throws IOException {
        float phi = (float) (Math.PI) / 4;
        Model model = ObjReader.read("./models/3DModels/CaracalCube/caracal_cube.obj");
        ArrayList<Vector3f> originVertices = new ArrayList<>();
        for (Vector3f vertex : model.vertices) {
            originVertices.add(new Vector3f(
                    vertex.x * GraphicConveyor.cos(phi) + vertex.z * GraphicConveyor.sin(phi),
                    vertex.y,
                    -vertex.x * GraphicConveyor.sin(phi) + vertex.z * GraphicConveyor.cos(phi)
            ));
        }

        model.rotation = new Vector3f(0f, phi, 0f);
        Matrix4x4 modelMatrix = model.getModelMatrix();
        ArrayList<Vector3f> resultVertices = new ArrayList<>();
        for (Vector3f vertex : model.vertices){
            Vector4f vertex4 = modelMatrix.mulV(new Vector4f(vertex.x, vertex.y, vertex.z, 1));
            resultVertices.add(new Vector3f(vertex4.x, vertex4.y, vertex4.z));
        }

        for (int i = 0; i < originVertices.size(); i++){
            Assertions.assertEquals(originVertices.get(i), resultVertices.get(i));
        }
    }

    @Test
    public void testAffineRotationX() throws IOException {
        float phi = (float) (Math.PI) / 4;
        Model model = ObjReader.read("./models/3DModels/CaracalCube/caracal_cube.obj");
        ArrayList<Vector3f> originVertices = new ArrayList<>();
        for (Vector3f vertex : model.vertices) {
            originVertices.add(new Vector3f(
                    vertex.x,
                    vertex.y * GraphicConveyor.cos(phi) + vertex.z * GraphicConveyor.sin(phi),
                    -vertex.y * GraphicConveyor.sin(phi) + vertex.z * GraphicConveyor.cos(phi)
            ));
        }

        model.rotation = new Vector3f(phi, 0f, 0f);
        Matrix4x4 modelMatrix = model.getModelMatrix();
        ArrayList<Vector3f> resultVertices = new ArrayList<>();
        for (Vector3f vertex : model.vertices){
            Vector4f vertex4 = modelMatrix.mulV(new Vector4f(vertex.x, vertex.y, vertex.z, 1));
            resultVertices.add(new Vector3f(vertex4.x, vertex4.y, vertex4.z));
        }

        for (int i = 0; i < originVertices.size(); i++){
            Assertions.assertEquals(originVertices.get(i), resultVertices.get(i));
        }
    }

    @Test
    public void testAffineTransform() throws IOException {
        Model model = ObjReader.read("./models/3DModels/CaracalCube/caracal_cube.obj");
        ArrayList<Vector3f> originVertices = new ArrayList<>();
        for (Vector3f vertex : model.vertices) {
            originVertices.add(new Vector3f(
                    vertex.x + 5f,
                    vertex.y + 4f,
                    vertex.z + 3f
            ));
        }

        model.position.add(new Vector3f(5f, 4f, 3f));
        Matrix4x4 modelMatrix = model.getModelMatrix();
        ArrayList<Vector3f> resultVertices = new ArrayList<>();
        for (Vector3f vertex : model.vertices){
            Vector4f vertex4 = modelMatrix.mulV(new Vector4f(vertex.x, vertex.y, vertex.z, 1));
            resultVertices.add(new Vector3f(vertex4.x, vertex4.y, vertex4.z));
        }

        for (int i = 0; i < originVertices.size(); i++){
            Assertions.assertEquals(originVertices.get(i), resultVertices.get(i));
        }
    }
}
