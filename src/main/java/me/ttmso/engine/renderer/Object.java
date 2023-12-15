package me.ttmso.engine.renderer;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Object {

    private float[] vertexArray;
    private int[] elementArray;

    private final List<VertexAttribute> VertAttribs = new ArrayList<>();

    private int vaoID;
    private int vboID;
    private int eboID;

    private Shader shader;


    public Object() { }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public void resetVertexAttribs() {
        this.VertAttribs.clear();
    }

    public void addVertexAttrib(int values) {
        this.VertAttribs.add(new VertexAttribute(values));
    }

    public void create(float[] vertexArray, int[] elementArray) {
        this.vertexArray = vertexArray;
        this.elementArray = elementArray;

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // Code to abstract the vertex attributes
        int floatSize = Float.BYTES;

        int vertexSizeBytes = 0;
        for (int i = 0; i < VertAttribs.size(); i++) {
            VertexAttribute attrib = VertAttribs.get(i);
            vertexSizeBytes += attrib.values * floatSize;
        }

        int pointer = 0;
        for (int i = 0; i < VertAttribs.size(); i++) {
            VertexAttribute attrib = VertAttribs.get(i);

            glVertexAttribPointer(i, attrib.values, GL_FLOAT, false, vertexSizeBytes, pointer);

            pointer += attrib.values * floatSize;
        }
    }

    public void draw() {
        shader.use();

        glBindVertexArray(vaoID);
        for (int i = 0; i < VertAttribs.size(); i++) {
            glEnableVertexAttribArray(i);
        }

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        for (int i = 0; i < VertAttribs.size(); i++) {
            glDisableVertexAttribArray(i);
        }
        glBindVertexArray(0);

        shader.detach();
    }

    public void destroy() {
        glDeleteVertexArrays(vaoID);
        glDeleteBuffers(vboID);
        glDeleteBuffers(eboID);
    }
}
