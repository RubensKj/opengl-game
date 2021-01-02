package com.rubenskj.engine.entities;

import com.rubenskj.engine.io.WindowManager;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWScrollCallback;

public class Camera {

    private boolean mouseLocked = false;

    private float distanceFromPlayer = 50;
    private float angleAroundPlayer = 0;

    private Vector3f position = new Vector3f(0, 0, 0);
    private float pitch = 20;
    private float yaw = 0;
    private float roll;
    private Player player;

    public Camera(Player player) {
        this.player = player;
    }

    public void move() {

        // 19th Episode WATCH
        calculateZoom();
        calculatePitch();
        calculateAngleAroundPlayer();

        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();

        calculateCameraPosition(horizontalDistance, verticalDistance);
        this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
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

    private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {
        float theta = player.getRotY() + angleAroundPlayer;
        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
        position.x = player.getPosition().x - offsetX;
        position.z = player.getPosition().z - offsetZ;
        position.y = player.getPosition().y + verticalDistance;
    }

    private float calculateHorizontalDistance() {
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance() {
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateZoom() {
        long window = WindowManager.getInstance();

        GLFW.glfwSetScrollCallback(window, new GLFWScrollCallback() {
            @Override
            public void invoke(long win, double dx, double dy) {
                if (distanceFromPlayer >= 60) {
                    distanceFromPlayer = 60;
                }

                float zoomLevel = (float) (dy * 0.5f);

                if (distanceFromPlayer <= 60) {
                    distanceFromPlayer -= zoomLevel;
                }
            }
        });
    }

    private void calculatePitch() {
        long window = WindowManager.getInstance();

        // 19th Episode WATCH

//        if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS) {
//            System.out.println("MOUSE DY:" + mouseDY);
//            System.out.println("MOUSE Y:" + mouseY);
//
//            float pitchChange = mouseDY * 0.1f;
//            pitch += pitchChange;
//        }
//        double newX = 400;
//        double newY = 300;
//
//        double prevX = 0;
//        double prevY = 0;
//
//        boolean rotX = false;
//        boolean rotY = false;
//
//        if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS) {
//            glfwSetCursorPos(window, 800 / 2, 600 / 2);
//
//            mouseLocked = true;
//        } else {
//            mouseLocked = false;
//        }
//
//        if (mouseLocked) {
//            DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
//            DoubleBuffer y = BufferUtils.createDoubleBuffer(1);
//
//            glfwGetCursorPos(window, x, y);
//            x.rewind();
//            y.rewind();
//
//            newX = x.get();
//            newY = y.get();
//
//            double deltaX = newX - 400;
//            double deltaY = newY - 300;
//
//            rotX = newX != prevX;
//            rotY = newY != prevY;
//
//            if (rotY) {
//                System.out.println("ROTATE Y AXIS: " + deltaY);
//
//            }
//            if (rotX) {
//                System.out.println("ROTATE X AXIS: " + deltaX);
//            }
//
//            System.out.println("Delta X = " + deltaX + " Delta Y = " + deltaY);
//
//            glfwSetCursorPos(window, 800 / 2, 600 / 2);
//
//            float pitchChange = (float) (deltaY * 0.1f);
//            pitch -= pitchChange;
//        }
    }

    private void calculateAngleAroundPlayer() {

        // 19th Episode WATCH
//        long window = WindowManager.getInstance();
//
//        if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_2) == GLFW_PRESS) {
//            DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
//            DoubleBuffer y = BufferUtils.createDoubleBuffer(1);
//
//            glfwGetCursorPos(window, x, y);
//            x.rewind();
//
//            double newX = x.get();
//
//            GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
//
//            double deltaX = newX - videoMode.width() / 2;
//
//            float angleChange = (float) (deltaX * 0.3f);
//
//            angleAroundPlayer -= angleChange;
//        }
    }
}
