package com.rubenskj.engine.io;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;

public class WindowManager {

    private static long WINDOW;

    public static int WIDHT = 1280;
    public static int HEIGHT = 720;

    private static final int FPS_CAP = 120;

    public static long getInstance() {
        if (WINDOW == 0) {
            createWindow();
        }

        return WINDOW;
    }

    private static void createWindow() {
        if (!glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW!");
        }

        setWindowHints();

        build("Our First Window", WIDHT, HEIGHT, 0, 0);
    }

    private static void setWindowHints() {
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_REFRESH_RATE, FPS_CAP);

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

    public static void updateWindow() {
        glfwPollEvents();

        glfwSwapBuffers(getInstance());
    }

    public static void closeWindow() {
        glfwDestroyWindow(getInstance());

        glfwTerminate();
    }
}
