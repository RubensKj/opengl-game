package com.rubenskj.engine;

import com.rubenskj.engine.entities.*;
import com.rubenskj.engine.io.OBJLoader;
import com.rubenskj.engine.model.RawModel;
import com.rubenskj.engine.model.TexturedModel;
import com.rubenskj.engine.render.Loader;
import com.rubenskj.engine.render.MasterRenderer;
import com.rubenskj.engine.terrain.Terrain;
import com.rubenskj.engine.textures.ModelTexture;
import com.rubenskj.engine.textures.TerrainTexture;
import com.rubenskj.engine.textures.TerrainTexturePack;
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

        // TERRAIN PACK

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);

        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

        // ENTITIES

        Entity tree = createEntity("tree", "tree", new Vector3f(0, 0, 0), 1, loader);
        Entity grass = createEntity("grassModel", "grassTexture", new Vector3f(0, 0, 0), 1, loader);
        Entity flower = createEntity("grassModel", "flower", new Vector3f(0, 0, 0), 1, loader);
        Entity fern = createEntity("fern", "fern", new Vector3f(0, 0, 0), 1, loader);

        grass.getModel().getTexture().setHasTransparency(true);
        grass.getModel().getTexture().setUseFakeLighting(true);
        flower.getModel().getTexture().setHasTransparency(true);
        flower.getModel().getTexture().setUseFakeLighting(true);
        fern.getModel().getTexture().setHasTransparency(true);

        List<Entity> entities = new ArrayList<>();

        Random random = new Random(676452);

        entities.addAll(createGrass(loader));
        entities.addAll(createTrees(loader));
        entities.add(createEntity("dragon", "white", new Vector3f(100, 0, -25), 1, loader));
        entities.add(createEntity("spaceship", "white", new Vector3f(110, 8, -35), 1, loader));

        // LIGHT

        Light light = new Light(new Vector3f(20000, 40000, 20000), new Vector3f(1, 1, 1));

        // TERRAIN

        Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap);
        Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap);

        // CAMERA AND RENDER

        MasterRenderer renderer = new MasterRenderer();


        TexturedModel texturedPlayer = createTexturedModel("person", "playerTexture", loader);

        Player player = new Player(texturedPlayer, new Vector3f(100, 0, -50), 0, 0, 0, 0.6f);
        Camera camera = new Camera(player);

        while (!glfwWindowShouldClose(window)) {
            camera.move();
            player.move();

            renderer.processEntity(player);
            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);
            entities.forEach(renderer::processEntity);

            renderer.render(light, camera);

            updateWindow();
        }

        renderer.cleanUp();
        loader.cleanUp();
        closeWindow();
    }

    private static Entity createEntity(String fileObjName, String fileTextureName, Vector3f position, float scale, Loader loader) {
        ModelData data = OBJLoader.loadOBJ(fileObjName);
        RawModel model = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
        ModelTexture texture = new ModelTexture(loader.loadTexture(fileTextureName));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        return new Entity(texturedModel, position, 0, 0, 0, scale);
    }

    private static TexturedModel createTexturedModel(String fileObjName, String fileTextureName, Loader loader) {
        ModelData data = OBJLoader.loadOBJ(fileObjName);
        RawModel model = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
        ModelTexture texture = new ModelTexture(loader.loadTexture(fileTextureName));
        return new TexturedModel(model, texture);
    }

    private static Entity createStall(Loader loader) {
        ModelData data = OBJLoader.loadOBJ("stall");
        RawModel model = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
        ModelTexture texture = new ModelTexture(loader.loadTexture("stallTexture"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        texture.setShineDamper(10);
        texture.setReflectivity(1);

        return new Entity(texturedModel, new Vector3f(15, 0, 25), 0, 160, 0, 1);
    }

    private static List<Entity> createTrees(Loader loader) {
        ModelData data = OBJLoader.loadOBJ("tree");
        RawModel model = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
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

        Entity grass = createEntity("grassModel", "grassTexture", new Vector3f(0, 0, 0), 0.4f, loader);
        Entity fern = createEntity("fern", "fern", new Vector3f(0, 0, 0), 0.6f, loader);
        Entity flower = createEntity("grassModel", "flower", new Vector3f(0, 0, 0), 0.4f, loader);

        grass.getModel().getTexture().setHasTransparency(true);
        grass.getModel().getTexture().setUseFakeLighting(true);
        fern.getModel().getTexture().setHasTransparency(true);
        fern.getModel().getTexture().setUseFakeLighting(true);
        flower.getModel().getTexture().setHasTransparency(true);
        flower.getModel().getTexture().setUseFakeLighting(true);

        List<Entity> grasses = new ArrayList<>();

        for (int i = 0; i < 200; i++) {
            Random random = new Random();

            float x = random.nextFloat() * 108 - 50;
            float y = 0;
            float z = random.nextFloat() * 105 - 50;

            if (i > 88) {
                fern.setPosition(new Vector3f(x, y, z));
                grasses.add(fern);
                continue;
            }

            if (i > 150) {
                flower.setPosition(new Vector3f(x, y, z));
                grasses.add(flower);
                continue;
            }

            grass.setPosition(new Vector3f(x, y, z));
            grasses.add(grass);
        }

        return grasses;
    }
}
