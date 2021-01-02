package com.rubenskj.engine;

import com.rubenskj.engine.entities.Camera;
import com.rubenskj.engine.entities.Entity;
import com.rubenskj.engine.entities.Light;
import com.rubenskj.engine.io.OBJLoader;
import com.rubenskj.engine.model.RawModel;
import com.rubenskj.engine.model.TexturedModel;
import com.rubenskj.engine.render.Loader;
import com.rubenskj.engine.render.MasterRenderer;
import com.rubenskj.engine.textures.ModelTexture;
import org.joml.Vector3f;

import static com.rubenskj.engine.io.WindowManager.*;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class EngineApplication {

    public static void main(String[] args) {
        long window = getInstance();

        Loader loader = new Loader();

        RawModel model = OBJLoader.loadObjModel("stall", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("stallTexture"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

//        texture.setShineDamper(10);
//        texture.setReflectivity(1);

        Entity entity = new Entity(texturedModel, new Vector3f(0, -2, -25), 0, 160, 0, 1);
        Light light = new Light(new Vector3f(3000, 2000, 2000), new Vector3f(1, 1, 1));

        Camera camera = new Camera();

        MasterRenderer renderer = new MasterRenderer();

        while (!glfwWindowShouldClose(window)) {
            camera.move();

            renderer.processEntity(entity);

            renderer.render(light, camera);

            updateWindow();
        }

        renderer.cleanUp();
        loader.cleanUp();
        closeWindow();
    }
}
