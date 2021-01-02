package com.rubenskj.engine.entities;

import com.rubenskj.engine.io.WindowManager;
import com.rubenskj.engine.model.TexturedModel;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

public class Player extends Entity {

    private static final float RUN_SPEED = 20;
    private static final float TURN_SPEED = 160;
    private static final float GRAVITY = -50;
    private static final float JUMP_POWER = 30;

    private static final float TERRAIN_HEIGHT = 0;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;

    private boolean isInAir = false;

    public Player(TexturedModel model, Vector3f position, int rotX, int rotY, int rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void move() {
        checkInputs();
        super.increaseRotation(0, currentTurnSpeed * WindowManager.getFrameTimeSeconds(), 0);
        float distance = currentSpeed * WindowManager.getFrameTimeSeconds();

        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
        super.increasePosition(dx, 0, dz);
        upwardsSpeed += GRAVITY * WindowManager.getFrameTimeSeconds();
        super.increasePosition(0, upwardsSpeed * WindowManager.getFrameTimeSeconds(), 0);

        if (super.getPosition().y < TERRAIN_HEIGHT) {
            upwardsSpeed = 0;
            isInAir = false;
            super.getPosition().y = TERRAIN_HEIGHT;
        }
    }

    private void jump() {
        if (!isInAir) {
            this.upwardsSpeed = JUMP_POWER;
            isInAir = true;
        }
    }

    private void checkInputs() {
        long window = WindowManager.getInstance();

        if (glfwGetKey(window, GLFW_KEY_W) == GL_TRUE) {
            this.currentSpeed = RUN_SPEED;
        } else if (glfwGetKey(window, GLFW_KEY_S) == GL_TRUE) {
            this.currentSpeed = -RUN_SPEED;
        } else {
            this.currentSpeed = 0;
        }

        if (glfwGetKey(window, GLFW_KEY_D) == GL_TRUE) {
            this.currentTurnSpeed = -TURN_SPEED;
        } else if (glfwGetKey(window, GLFW_KEY_A) == GL_TRUE) {
            this.currentTurnSpeed = TURN_SPEED;
        } else {
            this.currentTurnSpeed = 0;
        }

        if (glfwGetKey(window, GLFW_KEY_SPACE) == GL_TRUE) {
            jump();
        }
    }
}
