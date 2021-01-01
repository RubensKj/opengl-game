package com.rubenskj.core.io;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;

public class Window {

    private static long WINDOW;

    private static int WIDHT;
    private static int HEIGHT;

    public static long getInstance() {
        if (WINDOW == 0) {
            create(WIDHT, HEIGHT);

            return WINDOW;
        }

        return WINDOW;
    }

    public static Window create(int width, int height) {
        if (!glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW!");
        }

        init(width, height);

        setWindowHints();

        build("My OpenGL Program", width, height, 0, 0);

        return new Window();
    }

    private static void init(int width, int height) {
        WIDHT = width;
        HEIGHT = height;
    }

    private static void setWindowHints() {
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    }

    private static long build(String title, int width, int height, int monitor, int share) {
        long window = glfwCreateWindow(width, height, title, monitor, share);

        WINDOW = window;

        if (window == 0) {
            throw new IllegalStateException("Failed to create the window!");
        }

        setCapabilities(window);

        showWindow(window);

        return WINDOW;
    }

    private static void setCapabilities(long window) {
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
    }

    private static void showWindow(long window) {
        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        glfwSetWindowPos(window, (videoMode.width() - WIDHT) / 2, (videoMode.height() - HEIGHT) / 2);

        glfwShowWindow(window);
    }

    public void free() {
        glfwDestroyWindow(WINDOW);

        glfwTerminate();
    }

    public boolean update() {
        glfwPollEvents();

        return glfwWindowShouldClose(WINDOW);
    }

    public void swapBuffers() {
        glfwSwapBuffers(WINDOW);
    }
}
