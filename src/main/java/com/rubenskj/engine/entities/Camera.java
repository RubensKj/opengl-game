package com.rubenskj.engine.entities;

import com.rubenskj.engine.io.WindowManager;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

public class Camera {

    private Vector3f position = new Vector3f(0, 0, 0);
    private float pitch;
    private float yaw;
    private float roll;

    public Camera() {
    }

    public void move() {
        long window = WindowManager.getInstance();

        if (glfwGetKey(window, GLFW_KEY_W) == GL_TRUE) {
            position.z -= 0.02f;
        }

        if (glfwGetKey(window, GLFW_KEY_D) == GL_TRUE) {
            position.x += 0.02f;
        }

        if (glfwGetKey(window, GLFW_KEY_A) == GL_TRUE) {
            position.x -= 0.02f;
        }

        if (glfwGetKey(window, GLFW_KEY_S) == GL_TRUE) {
            position.z += 0.02f;
        }
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
}
