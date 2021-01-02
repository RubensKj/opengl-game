package com.rubenskj.engine;

import com.rubenskj.engine.model.RawModel;
import com.rubenskj.engine.model.TexturedModel;
import com.rubenskj.engine.render.Loader;
import com.rubenskj.engine.render.Renderer;
import com.rubenskj.engine.shaders.StaticShader;
import com.rubenskj.engine.textures.ModelTexture;

import static com.rubenskj.engine.io.WindowManager.*;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class EngineApplication {

    public static void main(String[] args) {
        long window = getInstance();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

        float[] vertices = {
                -0.5f, 0.5f, 0f,//v0
                -0.5f, -0.5f, 0f,//v1
                0.5f, -0.5f, 0f,//v2
                0.5f, 0.5f, 0f,//v3
        };

        int[] indices = {
                0, 1, 3,//top left triangle (v0, v1, v3)
                3, 1, 2//bottom right triangle (v3, v1, v2)
        };

        float[] textureCoords = {
                0, 0, // V0
                0, 1, // V1
                1, 1, // V2
                1, 0  // V3
        };

        RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("simple_texture"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        while (!glfwWindowShouldClose(window)) {
            renderer.prepare();

            shader.start();
            renderer.render(texturedModel);
            shader.stop();

            updateWindow();
        }

        shader.cleanUp();
        loader.cleanUp();
        closeWindow();
    }
}
