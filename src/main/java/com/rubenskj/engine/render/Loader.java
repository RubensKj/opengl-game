package com.rubenskj.engine.render;

import com.rubenskj.engine.util.FileUtil;
import com.rubenskj.engine.model.RawModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;

public class Loader {

    private static final Logger LOGGER = Logger.getLogger(Loader.class.getName());

    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();
    private List<Integer> textures = new ArrayList<>();

    public RawModel loadToVAO(float[] positions, float[] texturesCoords, int[] indices) {
        int vaoId = createVAO();

        bindIndicesBuffer(indices);
        storeDataInAttributeList(0, 3, positions);
        storeDataInAttributeList(1, 2, texturesCoords);
        unbindVAO();

        return new RawModel(vaoId, indices.length);
    }

    public int loadTexture(String fileName) {
        int[] x = new int[1];
        int[] y = new int[1];
        int[] component = new int[1];

        ByteBuffer pixels = stbi_load_from_memory(FileUtil.loadResource(fileName), x, y, component, 4);

        if (pixels == null) {
            LOGGER.info("Failed to load texture: " + fileName);
            return 0;
        }

        int textureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureId);

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, x[0], y[0], 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);

        textures.add(textureId);

        return textureId;
    }

    public void cleanUp() {
        vaos.forEach(GL30::glDeleteVertexArrays);
        vbos.forEach(GL15::glDeleteBuffers);
        textures.forEach(GL11::glDeleteTextures);
    }

    private int createVAO() {
        int vaoId = GL30.glGenVertexArrays();

        vaos.add(vaoId);

        GL30.glBindVertexArray(vaoId);

        return vaoId;
    }

    private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
        int vboId = GL15.glGenBuffers();

        vbos.add(vboId);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void unbindVAO() {
        GL30.glBindVertexArray(0);
    }

    private void bindIndicesBuffer(int[] indices) {
        int vboId = GL15.glGenBuffers();

        vbos.add(vboId);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboId);

        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    private IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);

        buffer.put(data);
        buffer.flip();

        return buffer;
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);

        buffer.put(data);
        buffer.flip();

        return buffer;
    }
}
