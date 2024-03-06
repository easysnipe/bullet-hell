import java.lang.Math;
import static org.lwjgl.glfw.GLFW.*;

public class Example
{
    public static void main(String[] args)
    {
        Game game = new Game();
    }

    public static vec2 updPos(long glfwWindow, vec2 pos, float deltaTime)
    {
        float speed = 2.0f * deltaTime;

        if (glfwGetKey(glfwWindow, GLFW_KEY_W) == GLFW_PRESS)
        {
            pos.y += speed;
        }
        if (glfwGetKey(glfwWindow, GLFW_KEY_S) == GLFW_PRESS)
        {
            pos.y -= speed;
        }
        if (glfwGetKey(glfwWindow, GLFW_KEY_A) == GLFW_PRESS)
        {
            pos.x -= speed * (1080/1920.0f);
        }
        if (glfwGetKey(glfwWindow, GLFW_KEY_D) == GLFW_PRESS)
        {
            pos.x += speed * (1080/1920.0f);
        }

        return pos;
    }
}