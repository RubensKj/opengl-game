package com.rubenskj.core;

import com.rubenskj.core.graphics.*;
import com.rubenskj.core.io.Window;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class EngineMainApplication {

    public static void main(String[] args) {

        Window window = Window.create(640, 480);

        Mesh mesh = new Mesh();

        mesh.create(new float[] {
                -1,-1,0,  0,1,
                0,1,0,    0.5f,0,
                1,-1,0,   1,1
        });

        Shader shader = new Shader();
        shader.create("basic");

        Texture texture = new Texture();
        texture.create("/textures/checker.png");

        Camera camera = new Camera();
        Transform transform = new Transform();

        camera.setPerspective((float) Math.toRadians(70), 640.0f / 480.0f, 0.01f, 1000.0f);
        camera.setPosition(new Vector3f(0, 1, 3));
        camera.setRotation(new Quaternionf(new AxisAngle4f((float) Math.toRadians(-30), new Vector3f(1, 0, 0))));

        boolean isRunning = true;

        float x = 0;

        while (isRunning) {
            isRunning = !window.update();

            x++;
            transform.setPosition(new Vector3f((float) Math.sin(Math.toRadians(x)), 0, 0));
            transform.getRotation().rotateAxis((float) Math.toRadians(1), 0, 1, 0);

            glClear(GL_COLOR_BUFFER_BIT);

            shader.useShader();
            shader.setCamera(camera);
            shader.setTransform(transform);
            shader.setSampleTexture(0);
            texture.bind();
            mesh.draw();

            window.swapBuffers();

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        texture.destroy();
        mesh.destroy();
        shader.destroy();

        window.free();
    }
}
