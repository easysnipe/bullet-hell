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
    final File VERTEXPATH = new File("\\resources\\vertex.vert");
    final File FRAGMENTPATH  = new File("\\resources\\fragment.frag");
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

        CompShaders();
    }

    private void CompShaders()
    {
        shader = new ShaderProgram(VERTEXPATH, FRAGMENTPATH);
        shader.CompileShader();
    }
}
