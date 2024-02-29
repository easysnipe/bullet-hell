import org.lwjgl.glfw.GLFWKeyCallbackI;

import static org.lwjgl.glfw.GLFW.*;

public class Controls
{
    public static final GLFWKeyCallbackI KeyCallback = (window, key, scancode, action, mods) ->
    {
        if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS)
        {
            glfwSetWindowShouldClose(window, true);
        }
    };
}
