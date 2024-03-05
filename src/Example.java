import java.lang.Math;
import static org.lwjgl.glfw.GLFW.*;

public class Example
{
    public static void main(String[] args)
    {
        GLWindow window = new GLWindow(1920, 1080, "Example Window");

        float[] triangleVerts = {
        0.0f, (2 * (float)Math.sqrt(3)/3),
        1.0f, (-1.0f * (float)Math.sqrt(3) / 3),
        -1.0f, (-1.0f * (float)Math.sqrt(3) / 3)
        };
        
        Object triangle = new Object(triangleVerts, new vec4(1.0f, 1.0f, 1.0f, 1.0f));
        triangle.debug = false;

        Object[] objects = {triangle};

        while (!window.windowShouldClose())
        {
            window.drawFrame(objects);
            triangle.rotation = (float)window.getTime();
            triangle.pos = updPos(window.getGLFW(), triangle.pos);
        }
    }

    public static vec2 updPos(long glfwWindow, vec2 pos)
    {
        if (glfwGetKey(glfwWindow, GLFW_KEY_W) == GLFW_PRESS)
        {
            pos.y += 0.1f;
        }

        return pos;
    }
}