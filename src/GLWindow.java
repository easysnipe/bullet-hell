import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import java.nio.*;
import java.io.File;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.system.MemoryUtil.*;

public class GLWindow
{
    private long window;
    
    ShaderProgram shader;
    
    VAO vao2d; 
    VBO vbo2d;

    mat3 view;

    final File VERTEXPATH = new File("resources\\vertex.vert");
    final File FRAGMENTPATH  = new File("resources\\fragment.frag");
    public GLWindow(int Width, int Height, String WindowName)
    {
        // Initalize the window
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit())
        {
            throw new IllegalStateException("GLFW has failed to Initialize");
        }
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        window = glfwCreateWindow(Width, Height, WindowName, NULL, NULL);
        if (window == NULL)
        {
            throw new IllegalStateException("Window has failed to initalize");
        }

        glfwSetKeyCallback(window, Controls.KeyCallback);
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);

        GL.createCapabilities();
        CompShaders();

        float top = Height / 2.0f;
        float bottom = -1 * Height / 2.0f;
        float right = Width / 2.0f;
        float left = -1 * Width / 2.0f;

        view = createOrthoView(top, bottom, right, left);
        setConstUniforms(shader);
    }

    private void setConstUniforms(ShaderProgram shade)
    {
        shade.EnableProgram();
        shade.SetMat3Uniform("view", view);
    }

    private void CompShaders()
    {
        shader = new ShaderProgram(VERTEXPATH.getAbsoluteFile(), FRAGMENTPATH.getAbsoluteFile());
        shader.CompileShader();
    }

    public mat3 createOrthoView(float top, float bottom, float right, float left)
    {
        vec3 r1 = new vec3(2.0f / (right - left), 0.0f, -1.0f *(right + left) / (right - left));
        vec3 r2 = new vec3(0.0f, 2.0f / (top - bottom), -1.0f *(top + bottom) / (top - bottom));
        vec3 r3 = new vec3(0.0f, 0.0f, 1.0f);
        mat3 viewMatrix = new mat3(r1, r2, r3);
        return viewMatrix;
    }

    public boolean windowShouldClose()
    {
        return glfwWindowShouldClose(window);
    }

    public double getTime()
    {
        return glfwGetTime();
    }

    public void drawFrame(Object[] objects)
    {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        for (int i = 0; i < objects.length; ++i)
        {
            objects[i].Draw(shader);
        }

        glfwSwapBuffers(window);
        glfwPollEvents();
    }
}
