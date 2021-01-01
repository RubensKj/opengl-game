package com.rubenskj.engine;

import com.rubenskj.engine.model.Loader;
import com.rubenskj.engine.model.RawModel;
import com.rubenskj.engine.render.Renderer;

import static com.rubenskj.engine.io.WindowManager.*;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class EngineApplication {

    public static void main(String[] args) {
        long window = getInstance();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();

        float[] vertices = {
                -0.5f, 0.5f, 0f,//v0
                -0.5f, -0.5f, 0f,//v1
                0.5f, -0.5f, 0f,//v2
                0.5f, 0.5f, 0f,//v3
        };

        int[] indices = {
                0,1,3,//top left triangle (v0, v1, v3)
                3,1,2//bottom right triangle (v3, v1, v2)
        };

        RawModel rawModel = loader.loadToVAO(vertices, indices);

        while (!glfwWindowShouldClose(window)) {
            renderer.prepare();

            renderer.render(rawModel);

            updateWindow();
        }

        loader.cleanUp();
        closeWindow();
    }
}
