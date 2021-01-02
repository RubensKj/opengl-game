package com.rubenskj.engine.toolbox;

import com.rubenskj.engine.entities.Camera;
import org.joml.AxisAngle4f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Maths {

    public static Matrix4f createTransformationMatrix(Vector3f position, int rotX, int rotY, int rotZ, float scale) {
        Matrix4f matrix = new Matrix4f();

        matrix.identity();

        matrix.translate(position);
        matrix.rotate(new Quaternionf(new AxisAngle4f((float) Math.toRadians(rotX), new Vector3f(1, 0, 0))), matrix);
        matrix.rotate(new Quaternionf(new AxisAngle4f((float) Math.toRadians(rotY), new Vector3f(0, 1, 0))), matrix);
        matrix.rotate(new Quaternionf(new AxisAngle4f((float) Math.toRadians(rotZ), new Vector3f(0, 0, 1))), matrix);
        matrix.scale(new Vector3f(scale));

        return matrix;
    }

    public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f viewMatrix = new Matrix4f();

        viewMatrix.identity();

        viewMatrix.rotate(new Quaternionf(new AxisAngle4f((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0))), viewMatrix);
        viewMatrix.rotate(new Quaternionf(new AxisAngle4f((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0))), viewMatrix);

        Vector3f position = camera.getPosition();

        Vector3f negativeCameraPos = new Vector3f(-position.x, -position.y, -position.z);

        viewMatrix.translate(negativeCameraPos, viewMatrix);

        return viewMatrix;
    }
}
