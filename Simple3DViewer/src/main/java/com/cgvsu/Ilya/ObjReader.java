package com.cgvsu.Ilya;

import com.cgvsu.Pavel.math.vectors.Vector2f;
import com.cgvsu.Pavel.math.vectors.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ObjReader {

    private static final String OBJ_VERTEX_TOKEN = "v";  // Токен для вершин
    private static final String OBJ_TEXTURE_TOKEN = "vt"; // Токен для текстурных координат
    private static final String OBJ_NORMAL_TOKEN = "vn";  // Токен для нормалей
    private static final String OBJ_FACE_TOKEN = "f";     // Токен для полигонов

    public static Model read(String fileContent) {
        Model result = new Model();

        int lineInd = 0;
        Scanner scanner = new Scanner(fileContent);

        boolean hasVertices = false;
        boolean hasPolygons = false;

        while (scanner.hasNextLine()) {
            final String line = scanner.nextLine().trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue; // Пропускаем пустые строки и комментарии
            }

            ArrayList<String> wordsInLine = new ArrayList<>(Arrays.asList(line.split("\\s+")));
            if (wordsInLine.isEmpty()) {
                continue;
            }

            final String token = wordsInLine.remove(0);
            ++lineInd;

            if (!isRecognizedToken(token)) {
                continue; // Игнорируем строки с непредусмотренными токенами
            }

            try {
                switch (token) {
                    case OBJ_VERTEX_TOKEN -> {
                        result.vertices.add(parseVertex(wordsInLine, lineInd));
                        hasVertices = true;
                    }
                    case OBJ_TEXTURE_TOKEN -> result.textureVertices.add(parseTextureVertex(wordsInLine, lineInd));
                    case OBJ_NORMAL_TOKEN -> result.normals.add(parseNormal(wordsInLine, lineInd));
                    case OBJ_FACE_TOKEN -> {
                        result.polygons.add(parseFace(wordsInLine, result.vertices.size(),
                                result.textureVertices.size(), result.normals.size(), lineInd));
                        hasPolygons = true;
                    }
                }
            } catch (ObjReaderException | NumberFormatException e) {
                System.err.println("Error at line " + lineInd + ": " + e.getMessage());
            }
        }

        if (!hasVertices) {
            throw new ObjReaderException("The OBJ file contains no vertices.", lineInd);
        }

        if (!hasPolygons) {
            throw new ObjReaderException("The OBJ file contains no polygons.", lineInd);
        }

        return result;
    }

    private static boolean isRecognizedToken(String token) {
        return token.equals(OBJ_VERTEX_TOKEN) || token.equals(OBJ_TEXTURE_TOKEN)
                || token.equals(OBJ_NORMAL_TOKEN) || token.equals(OBJ_FACE_TOKEN);
    }

    protected static Vector3f parseVertex(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        if (wordsInLineWithoutToken.size() < 3) {
            throw new ObjReaderException("Too few vertex arguments at line ", lineInd);
        }
        try {
            return new Vector3f(
                    Float.parseFloat(wordsInLineWithoutToken.get(0)),
                    Float.parseFloat(wordsInLineWithoutToken.get(1)),
                    Float.parseFloat(wordsInLineWithoutToken.get(2))
            );
        } catch (NumberFormatException e) {
            throw new ObjReaderException("Failed to parse vertex at line ", lineInd);
        }
    }

    protected static Vector2f parseTextureVertex(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        if (wordsInLineWithoutToken.size() < 2) {
            throw new ObjReaderException("Too few texture vertex arguments at line ", lineInd);
        }
        try {
            return new Vector2f(
                    Float.parseFloat(wordsInLineWithoutToken.get(0)),
                    Float.parseFloat(wordsInLineWithoutToken.get(1))
            );
        } catch (NumberFormatException e) {
            throw new ObjReaderException("Failed to parse texture vertex at line ", lineInd);
        }
    }

    protected static Vector3f parseNormal(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        if (wordsInLineWithoutToken.size() < 3) {
            throw new ObjReaderException("Too few normal arguments at line ", lineInd);
        }
        try {
            return new Vector3f(
                    Float.parseFloat(wordsInLineWithoutToken.get(0)),
                    Float.parseFloat(wordsInLineWithoutToken.get(1)),
                    Float.parseFloat(wordsInLineWithoutToken.get(2))
            );
        } catch (NumberFormatException e) {
            throw new ObjReaderException("Failed to parse normal at line ", lineInd);
        }
    }

    protected static Polygon parseFace(final ArrayList<String> wordsInLineWithoutToken,
                                       int vertexCount, int textureCount, int normalCount, int lineInd) {
        ArrayList<Integer> onePolygonVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonNormalIndices = new ArrayList<>();

        for (String s : wordsInLineWithoutToken) {
            parseFaceWord(s, onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, lineInd);
        }

        for (int index : onePolygonVertexIndices) {
            if (index < 0 || index >= vertexCount) {
                throw new ObjReaderException("Vertex index out of bounds at line ", lineInd);
            }
        }

        for (int index : onePolygonTextureVertexIndices) {
            if (index != -1 && (index < 0 || index >= textureCount)) {
                throw new ObjReaderException("Texture vertex index out of bounds at line ", lineInd);
            }
        }

        for (int index : onePolygonNormalIndices) {
            if (index != -1 && (index < 0 || index >= normalCount)) {
                throw new ObjReaderException("Normal index out of bounds at line ", lineInd);
            }
        }

        Polygon result = new Polygon();
        result.setVertexIndices(onePolygonVertexIndices);
        result.setTextureVertexIndices(onePolygonTextureVertexIndices);
        result.setNormalIndices(onePolygonNormalIndices);
        return result;
    }

    protected static void parseFaceWord(String wordInLine,
                                        ArrayList<Integer> onePolygonVertexIndices,
                                        ArrayList<Integer> onePolygonTextureVertexIndices,
                                        ArrayList<Integer> onePolygonNormalIndices, int lineInd) {
        String[] wordIndices = wordInLine.split("/");
        try {
            onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);

            if (wordIndices.length > 1 && !wordIndices[1].isEmpty()) {
                onePolygonTextureVertexIndices.add(Integer.parseInt(wordIndices[1]) - 1);
            } else {
                onePolygonTextureVertexIndices.add(-1);
            }

            if (wordIndices.length > 2 && !wordIndices[2].isEmpty()) {
                onePolygonNormalIndices.add(Integer.parseInt(wordIndices[2]) - 1);
            } else {
                onePolygonNormalIndices.add(-1);
            }
        } catch (NumberFormatException e) {
            throw new ObjReaderException("Invalid face format at line ", lineInd);
        }
    }
}
