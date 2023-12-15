package me.ttmso.engine;

import me.ttmso.engine.input.*;
import me.ttmso.engine.utils.Time;
import org.lwjgl.Version;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private int width, height;
    private String title;

    private long glfwWindow;

    private static Window window = null;

    private static Scene currentScene;

    private Window() {
        this.width = 1500;
        this.height = 900;

        this.title = "Window";
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }

        return Window.window;
    }

    public void run(Scene initScene) {
        System.out.println("LwJGL version: " + Version.getVersion());

        init();
        changeScene(initScene);
        loop();

        currentScene.exit();

        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("GLFW failed to init");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);

        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("The GLFW window failed to create");
        }

        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        glfwMakeContextCurrent(glfwWindow);
        glfwSwapInterval(1); // V-Sync

        glfwShowWindow(glfwWindow);

        GL.createCapabilities();
    }

    public void loop() {
        float lastUpdate = Time.getTime();
        float dt = -1.0f;

        while (!glfwWindowShouldClose(glfwWindow)) {
            glfwPollEvents();

            glClearColor(0.1f, 0.15f, 0.2f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            if (dt >= 0) {
                currentScene.update(dt);
            }

            glfwSwapBuffers(glfwWindow);

            dt = Time.getTime() - lastUpdate;
            lastUpdate = Time.getTime();
        }
    }

    public static void changeScene(Scene scene) {
        currentScene = scene;
        currentScene.init();
    }

}
