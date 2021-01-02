package com.rubenskj.engine.shaders;

public class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/main/resources/shaders/VertexShader.glsl";
    private static final String FRAGMENT_FILE = "src/main/resources/shaders/FragmentShader.glsl";

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
}
