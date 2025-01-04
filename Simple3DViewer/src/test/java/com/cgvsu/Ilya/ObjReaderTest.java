package com.cgvsu.Ilya;


import com.cgvsu.io.objReader.ObjReader;
import com.cgvsu.io.objReader.ObjReaderException;
import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ObjReaderTest {

    @Test
    void testValidFile() {
        String fileContent = """
                v 1.0 2.0 3.0
                v 4.0 5.0 6.0
                v 7.0 8.0 9.0
                f 1 2 3
                """;

        Model model = ObjReader.read(fileContent);

        assertEquals(3, model.vertices.size());
        assertEquals(new Vector3f(1.0f, 2.0f, 3.0f), model.vertices.get(0));
        assertEquals(1, model.polygons.size());
        assertEquals(List.of(0, 1, 2), model.polygons.get(0).getVertexIndices());
    }

    @Test
    void testFileWithMissingData() {
        String fileContent = """
                v 1.0 2.0 3.0
                v 4.0 5.0 6.0
                # No polygons
                """;

        ObjReaderException exception = assertThrows(
                ObjReaderException.class,
                () -> ObjReader.read(fileContent)
        );
        assertEquals("The OBJ file contains no polygons.", exception.getMessage());
    }

    @Test
    void testEmptyFile() {
        String fileContent = "";

        ObjReaderException exception = assertThrows(
                ObjReaderException.class,
                () -> ObjReader.read(fileContent)
        );
        assertEquals("The OBJ file contains no vertices.", exception.getMessage());
    }

    @Test
    void testInvalidVertexFormat() {
        String fileContent = """
                v 1.0 2.0
                """;

        ObjReaderException exception = assertThrows(
                ObjReaderException.class,
                () -> ObjReader.read(fileContent)
        );
        assertTrue(exception.getMessage().contains("Too few vertex arguments"));
    }

    @Test
    void testInvalidFaceIndexOutOfRange() {
        String fileContent = """
                v 1.0 2.0 3.0
                v 4.0 5.0 6.0
                f 1 3
                """;

        ObjReaderException exception = assertThrows(
                ObjReaderException.class,
                () -> ObjReader.read(fileContent)
        );
        assertTrue(exception.getMessage().contains("Vertex index out of bounds"));
    }

    @Test
    void testFileWithUnknownToken() {
        String fileContent = """
                v 1.0 2.0 3.0
                unknown_token 4.0 5.0 6.0
                """;

        Model model = ObjReader.read(fileContent);

        assertEquals(1, model.vertices.size());
        // Unknown tokens should not cause an exception.
    }

    @Test
    void testFaceWithMissingTextureAndNormal() {
        String fileContent = """
                v 1.0 2.0 3.0
                v 4.0 5.0 6.0
                v 7.0 8.0 9.0
                f 1/ 2/ 3/
                """;

        Model model = ObjReader.read(fileContent);

        assertEquals(1, model.polygons.size());
        Polygon polygon = model.polygons.get(0);

        assertEquals(List.of(0, 1, 2), polygon.getVertexIndices());
        assertEquals(List.of(-1, -1, -1), polygon.getTextureVertexIndices()); // No texture indices
        assertEquals(List.of(-1, -1, -1), polygon.getNormalIndices());       // No normal indices
    }

    @Test
    void testFaceWithIncompleteData() {
        String fileContent = """
                v 1.0 2.0 3.0
                v 4.0 5.0 6.0
                v 7.0 8.0 9.0
                f 1// 2// 3//
                """;

        Model model = ObjReader.read(fileContent);

        assertEquals(1, model.polygons.size());
        Polygon polygon = model.polygons.get(0);

        assertEquals(List.of(0, 1, 2), polygon.getVertexIndices());
        assertEquals(List.of(-1, -1, -1), polygon.getTextureVertexIndices()); // No texture indices
        assertEquals(List.of(-1, -1, -1), polygon.getNormalIndices());       // No normal indices
    }

    @Test
    void testInvalidFaceFormat() {
        String fileContent = """
                v 1.0 2.0 3.0
                v 4.0 5.0 6.0
                v 7.0 8.0 9.0
                f 1/2/3/4
                """;

        ObjReaderException exception = assertThrows(
                ObjReaderException.class,
                () -> ObjReader.read(fileContent)
        );
        assertTrue(exception.getMessage().contains("Invalid element size"));
    }
}
