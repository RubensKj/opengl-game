package com.rubenskj.engine.entities;

import com.rubenskj.engine.model.TexturedModel;
import org.joml.Vector3f;

public class Entity {

    private TexturedModel model;
    private Vector3f position;
    private int rotX, rotY, rotZ;
    private float scale;

    public Entity(TexturedModel model, Vector3f position, int rotX, int rotY, int rotZ, float scale) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }

    public void increasePosition(float dx, float dy, float dz) {
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }

    public void increaseRotation(float dx, float dy, float dz) {
        this.rotX += dx;
        this.rotY += dy;
        this.rotZ += dz;
    }

    public TexturedModel getModel() {
        return model;
    }

    public void setModel(TexturedModel model) {
        this.model = model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public int getRotX() {
        return rotX;
    }

    public int getRotY() {
        return rotY;
    }

    public int getRotZ() {
        return rotZ;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
