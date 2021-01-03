package com.rubenskj.engine.entities;

import com.rubenskj.engine.io.WindowManager;
import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

public class Mouse {

    private static float DELTA_X = 0;
    private static float DELTA_Y = 0;

    private static float ACTUAL_POSITION_X = 0;
    private static float ACTUAL_POSITION_Y = 0;

    private static float OLD_POSITION_X = 0;
    private static float OLD_POSITION_Y = 0;

    private static double newX = 400;
    private static double newY = 300;

    private static double prevX = 0;
    private static double prevY = 0;

    private Mouse() {
    }

    public static void updateDeltaValues() {
        long window = WindowManager.getInstance();

        DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer y = BufferUtils.createDoubleBuffer(1);

        glfwGetCursorPos(window, x, y);
        x.rewind();
        y.rewind();

        newX = x.get(0);
        newY = y.get(0);

        OLD_POSITION_X = ACTUAL_POSITION_X;
        OLD_POSITION_Y = ACTUAL_POSITION_Y;

        ACTUAL_POSITION_X = (float) newX;
        ACTUAL_POSITION_Y = (float) newY;

        double deltaX = newX - OLD_POSITION_X;
        double deltaY = newY - OLD_POSITION_Y;

        DELTA_X = (float) deltaX;
        DELTA_Y = (float) deltaY;

    }

    public static float getDeltaX() {
        return DELTA_X;
    }

    public static float getDeltaY() {
        return DELTA_Y;
    }

    public static float getActualPositionX() {
        return ACTUAL_POSITION_X;
    }

    public static float getActualPositionY() {
        return ACTUAL_POSITION_Y;
    }

    public static float getOldPositionX() {
        return OLD_POSITION_X;
    }

    public static float getOldPositionY() {
        return OLD_POSITION_Y;
    }
}
