package com.rubenskj.engine.shaders;

import com.rubenskj.engine.entities.Camera;
import com.rubenskj.engine.entities.Light;
import com.rubenskj.engine.toolbox.Maths;
import org.joml.Matrix4f;

import static com.rubenskj.engine.util.StaticUtil.FRAGMENT_FILE;
import static com.rubenskj.engine.util.StaticUtil.VERTEX_FILE;

public class StaticShader extends ShaderProgram {

    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;
    private int locationLightPosition;
    private int locationLightColour;
    private int locationShineDamper;
    private int locationReflectivity;

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    @Override
    protected void getAllUniformLocations() {
        locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
        locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
        locationViewMatrix = super.getUniformLocation("viewMatrix");
        locationLightPosition = super.getUniformLocation("lightPosition");
        locationLightColour = super.getUniformLocation("lightColour");
        locationShineDamper = super.getUniformLocation("shineDamper");
        locationReflectivity = super.getUniformLocation("reflectivity");
    }

    public void loadShineVariables(float damper, float reflectivity) {
        super.loadFloat(locationShineDamper, damper);
        super.loadFloat(locationReflectivity, reflectivity);
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(locationTransformationMatrix, matrix);
    }

    public void loadLight(Light light) {
        super.loadVector(locationLightPosition, light.getPosition());
        super.loadVector(locationLightColour, light.getColour());
    }

    public void loadViewMatrix(Camera camera) {
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);

        super.loadMatrix(locationViewMatrix, viewMatrix);
    }

    public void loadProjectionMatrix(Matrix4f projection) {
        super.loadMatrix(locationProjectionMatrix, projection);
    }
}
