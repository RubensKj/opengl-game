package com.rubenskj.engine.util;

import java.io.FileInputStream;
import java.io.IOException;

public class StaticUtil {

    private static boolean IS_PRODUCTION = false;
    private static boolean ALREADY_VALIDATE_PROD = false;

    private static final String BASIC_PATH = "src/main/resources/";
    private static final String PRODUCTION_BASIC_PATH = "lib/";

    // Files
    public static final String TEXTURE_ERROR_IMAGE = getBasicPath() + "res/checker.png";

    // Shaders
    public static final String VERTEX_FILE = getBasicPath() + "shaders/VertexShader.glsl";
    public static final String FRAGMENT_FILE = getBasicPath() + "shaders/FragmentShader.glsl";

    // Folders
    public static final String RES_FOLDER = getBasicPath() + "res/";

    private StaticUtil() {
    }

    private static String getBasicPath() {
        if (IS_PRODUCTION) {
            return PRODUCTION_BASIC_PATH;
        } else {
            if (!ALREADY_VALIDATE_PROD) {
                validateProd();
            }
            return BASIC_PATH;
        }
    }

    private static void validateProd() {
        try {
            FileInputStream fileInputStream = new FileInputStream(PRODUCTION_BASIC_PATH + "shaders/VertexShader.glsl");

            fileInputStream.read();

            IS_PRODUCTION = true;
        } catch (IOException e) {
            IS_PRODUCTION = false;
        }
        ALREADY_VALIDATE_PROD = true;
    }
}
