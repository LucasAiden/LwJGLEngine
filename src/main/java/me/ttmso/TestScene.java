package me.ttmso;

import me.ttmso.engine.Scene;
import me.ttmso.engine.input.KeyListener;
import static org.lwjgl.glfw.GLFW.*;

public class TestScene extends Scene {
    @Override
    public void init() {
        System.out.println("Scene init");
    }

    @Override
    public void update() {
        if (KeyListener.isKeyDown(GLFW_KEY_LEFT_SHIFT)) {
            System.out.println("Shift is down");
        }
    }
}
