package com.rubenskj.engine.shaders;

import static com.rubenskj.engine.util.StaticUtil.FRAGMENT_FILE;
import static com.rubenskj.engine.util.StaticUtil.VERTEX_FILE;

public class StaticShader extends ShaderProgram {

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
    }
}
