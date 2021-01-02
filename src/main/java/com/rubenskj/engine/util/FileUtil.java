package com.rubenskj.engine.util;

import org.lwjgl.BufferUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static com.rubenskj.engine.util.StaticUtil.RES_FOLDER;
import static com.rubenskj.engine.util.StaticUtil.TEXTURE_ERROR_IMAGE;

public class FileUtil {

    private FileUtil() {
    }

    public static ByteBuffer loadResource(String file) {
        return loadResourceFromFile(file);
    }

    private static ByteBuffer loadResourceFromFile(String file) {
        try (FileInputStream fileInputStream = new FileInputStream(RES_FOLDER + file + ".png")) {
            return createByteBufferFromFileInputStream(fileInputStream);
        } catch (IOException e) {
            try (FileInputStream fileInputStream = new FileInputStream(TEXTURE_ERROR_IMAGE)) {
                return createByteBufferFromFileInputStream(fileInputStream);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            return null;
        }
    }

    private static ByteBuffer createByteBufferFromFileInputStream(FileInputStream fileInputStream) throws IOException {
        ByteBuffer buffer = BufferUtils.createByteBuffer(1024);

        int data;
        while ((data = fileInputStream.read()) != -1) {
            buffer.put((byte) data);

            if (buffer.remaining() == 0) {
                ByteBuffer newBuffer = BufferUtils.createByteBuffer(buffer.capacity() + 1024);
                buffer.flip();
                newBuffer.put(buffer);
                buffer = newBuffer;
            }
        }

        buffer.flip();
        return buffer;
    }
}
