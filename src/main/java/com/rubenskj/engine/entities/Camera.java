package com.rubenskj.engine.entities;

import com.rubenskj.engine.io.WindowManager;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWScrollCallback;

import static com.rubenskj.engine.entities.Mouse.updateDeltaValues;
import static org.lwjgl.glfw.GLFW.*;

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
        updateDeltaValues();
        checkInputs();
        calculateZoom();
        calculateAngles();
        calculatePitch();
        calculateAngleAroundPlayer();

        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();

        calculateCameraPosition(horizontalDistance, verticalDistance);
        this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
    }

    private void checkInputs() {
        long window = WindowManager.getInstance();

        if (glfwGetKey(window, GLFW_KEY_T) == GLFW_TRUE) {
            this.angleAroundPlayer = 0;
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
                float zoomLevel = (float) (dy * 0.5f);

                alterDistanceFromPlayer(zoomLevel);
            }
        });
    }

    private void calculateAngles() {
        long window = WindowManager.getInstance();

        if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS) {
            glfwSetCursorPos(window, Mouse.getActualPositionX(), Mouse.getActualPositionY());

            mouseLocked = true;
        } else {
            mouseLocked = false;
        }

        if (mouseLocked) {

            float pitchChange = Mouse.getDeltaY() * 0.1f;

            alterPitch(pitchChange);

            float angleChange = Mouse.getDeltaX() * 0.1f;

            alterAngleAroundPlayer(angleChange);
        }
    }

    private void calculatePitch() {
        long window = WindowManager.getInstance();

        if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_2) == GLFW_PRESS) {
            glfwSetCursorPos(window, Mouse.getActualPositionX(), Mouse.getActualPositionY());

            mouseLocked = true;
        } else {
            mouseLocked = false;
        }

        if (mouseLocked) {

            float pitchChange = Mouse.getDeltaY() * 0.1f;

            alterPitch(pitchChange);
        }
    }

    private void calculateAngleAroundPlayer() {
        // 19th Episode WATCH
        long window = WindowManager.getInstance();

        if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_3) == GLFW_PRESS) {
            glfwSetCursorPos(window, Mouse.getActualPositionX(), Mouse.getActualPositionY());

            mouseLocked = true;
        } else {
            mouseLocked = false;
        }

        if (mouseLocked) {
            float angleChange = Mouse.getDeltaX() * 0.1f;

            alterAngleAroundPlayer(angleChange);
        }
    }

    private void alterDistanceFromPlayer(float zoomLevel) {
        float distanceFromPlayerUpdate = distanceFromPlayer - zoomLevel;

        if (distanceFromPlayerUpdate < 15 || distanceFromPlayerUpdate > 60) {
            return;
        }

        distanceFromPlayer -= zoomLevel;
    }

    private void alterPitch(float pitchChange) {
        float pitchUpdated = pitch + pitchChange;

        if (pitchUpdated <= 3f || pitchUpdated >= 85) {
            return;
        }

        pitch += pitchChange;
    }

    private void alterAngleAroundPlayer(float angleChange) {
        angleAroundPlayer -= angleChange;
    }
}
