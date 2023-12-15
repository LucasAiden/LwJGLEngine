package me.ttmso.engine.input;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyListener {
    private static KeyListener instance;
    private boolean keyDown[] = new boolean[350];

    private KeyListener() {

    }

    public static KeyListener get() {
        if (KeyListener.instance == null) {
            KeyListener.instance = new KeyListener();
        }

        return KeyListener.instance;
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods) {
        if (key >= 0 && key < get().keyDown.length)
            if (action == GLFW_PRESS) {
                get().keyDown[key] = true;
            } else if (action == GLFW_RELEASE) {
                get().keyDown[key] = false;
            }
    }

    public static boolean isKeyDown(int keyCode) {
        return get().keyDown[keyCode];
    }
}
