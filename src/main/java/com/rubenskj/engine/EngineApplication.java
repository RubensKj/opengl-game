package com.rubenskj.engine;

import com.rubenskj.engine.entities.Camera;
import com.rubenskj.engine.entities.Entity;
import com.rubenskj.engine.entities.Light;
import com.rubenskj.engine.io.OBJLoader;
import com.rubenskj.engine.model.RawModel;
import com.rubenskj.engine.model.TexturedModel;
import com.rubenskj.engine.render.Loader;
import com.rubenskj.engine.render.MasterRenderer;
import com.rubenskj.engine.terrain.Terrain;
import com.rubenskj.engine.textures.ModelTexture;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.rubenskj.engine.io.WindowManager.*;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class EngineApplication {

    public static void main(String[] args) {
        long window = getInstance();

        Loader loader = new Loader();

        Entity stall = createStall(loader);
        List<Entity> trees = createTrees(loader);
        List<Entity> grasses = createGrass(loader);

        Light light = new Light(new Vector3f(3000, 2000, 1000), new Vector3f(1, 1, 1));

        ModelTexture texture = new ModelTexture(loader.loadTexture("grass_terrain"));

        texture.setShineDamper(100);
        texture.setReflectivity(10);

        Terrain terrain = new Terrain(0, 0, loader, texture);
        Terrain terrain2 = new Terrain(-1, -1, loader, texture);
        Terrain terrain3 = new Terrain(0, -1, loader, texture);
        Terrain terrain4 = new Terrain(-1, 0, loader, texture);

        Camera camera = new Camera();
        MasterRenderer renderer = new MasterRenderer();

        while (!glfwWindowShouldClose(window)) {
            camera.move();

            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);
            renderer.processTerrain(terrain3);
            renderer.processTerrain(terrain4);
            renderer.processEntity(stall);
            trees.forEach(renderer::processEntity);
            grasses.forEach(renderer::processEntity);

            renderer.render(light, camera);

            updateWindow();
        }

        renderer.cleanUp();
        loader.cleanUp();
        closeWindow();
    }

    private static Entity createStall(Loader loader) {
        RawModel model = OBJLoader.loadObjModel("stall", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("stallTexture"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        texture.setShineDamper(10);
        texture.setReflectivity(1);

        return new Entity(texturedModel, new Vector3f(15, 0, 25), 0, 160, 0, 1);
    }

    private static List<Entity> createTrees(Loader loader) {
        RawModel model = OBJLoader.loadObjModel("tree", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("tree"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        List<Entity> trees = new ArrayList<>();

        for (int i = 0; i < 200; i++) {
            Random random = new Random();

            float x = random.nextFloat() * 100 - 50;
            float y = 0;
            float z = random.nextFloat() * 100 - 50;

            trees.add(new Entity(texturedModel, new Vector3f(x, y, z), 0, 160, 0, 3));
        }

        return trees;
    }

    private static List<Entity> createGrass(Loader loader) {
        RawModel model = OBJLoader.loadObjModel("grassModel", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("grassTexture"));

        RawModel modelFern = OBJLoader.loadObjModel("fern", loader);
        ModelTexture textureFern = new ModelTexture(loader.loadTexture("fern"));

        TexturedModel texturedModel = new TexturedModel(model, texture);
        TexturedModel texturedModelFern = new TexturedModel(modelFern, textureFern);

        texture.setHasTransparency(true);
        texture.setUseFakeLighting(true);
        textureFern.setHasTransparency(true);
        textureFern.setUseFakeLighting(true);

        List<Entity> grass = new ArrayList<>();

        for (int i = 0; i < 200; i++) {
            Random random = new Random();

            float x = random.nextFloat() * 108 - 50;
            float y = 0;
            float z = random.nextFloat() * 105 - 50;

            if (i > 88) {
                grass.add(new Entity(texturedModelFern, new Vector3f(x, y, z), 0, 0, 0, 0.6f));
                continue;
            }

            grass.add(new Entity(texturedModel, new Vector3f(x, y, z), 0, 0, 0, 1));
        }

        return grass;
    }
}
