package me.ttmso;

import me.ttmso.engine.Scene;
import me.ttmso.engine.input.*;
import me.ttmso.engine.renderer.Shader;
import me.ttmso.engine.renderer.Object;

import static org.lwjgl.glfw.GLFW.*;

public class TestScene extends Scene {
    private Shader shader;
    private Object object;

    @Override
    public void init() {
        System.out.println("Scene init");

        shader = new Shader("assets/shaders/default.glsl");
        shader.compile();

        object = new Object();

        object.setShader(shader);

        object.resetVertexAttribs();
        object.addVertexAttrib(3);
        object.addVertexAttrib(4);

        float[] vertexArray = {
    //          X      Y      Z                 R     G     B     A
                -0.5f, -0.5f, 0.0f,             1.0f, 0.0f, 0.0f, 1.0f,
                 0.5f, -0.5f, 0.0f,             0.0f, 1.0f, 0.0f, 1.0f,
                 0.0f,  0.5f, 0.0f,             0.0f, 0.0f, 1.0f, 1.0f,
        };

        int[] elementArray = {
                0, 1, 2,
        };

        object.create(vertexArray, elementArray);
    }

    @Override
    public void update(float dt) {
        System.out.println("Scene update DT: " + dt + " FPS: " + 1.0f/dt);

        if (KeyListener.isKeyDown(GLFW_KEY_LEFT_SHIFT)) {
            System.out.println("Shift is down");
        }

        object.draw();
    }

    @Override
    public void exit() {
        object.destroy();
        shader.destroy();
    }
}
