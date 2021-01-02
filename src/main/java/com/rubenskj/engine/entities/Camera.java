package com.rubenskj.engine.entities;

import com.rubenskj.engine.io.WindowManager;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

public class Camera {

    private Vector3f position = new Vector3f(100, 35, 50);
    private float pitch;
    private float yaw;
    private float roll;

    public Camera() {
    }

    public void move() {
        long window = WindowManager.getInstance();

        if (glfwGetKey(window, GLFW_KEY_KP_5) == GL_TRUE) {
            position.y += 0.2f;
        }

        if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GL_TRUE) {
            position.y -= 0.2f;
        }

        if (glfwGetKey(window, GLFW_KEY_KP_2) == GL_TRUE) {
            pitch += 0.4f;
        }

        if (glfwGetKey(window, GLFW_KEY_KP_8) == GL_TRUE) {
            pitch -= 0.4f;
        }

        if (glfwGetKey(window, GLFW_KEY_KP_6) == GL_TRUE) {
            yaw += 0.7f;
        }

        if (glfwGetKey(window, GLFW_KEY_KP_4) == GL_TRUE) {
            yaw -= 0.7f;
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
