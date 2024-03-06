import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;

public class Game 
{
    float deltaTime;
    float currentFrame;
    float lastFrame = 0.0f;
    GLWindow window;
    ArrayList<Object> enemys;
    ArrayList<Object> bullets;
    ArrayList<Object> objects;
    float lastSpawn;
    Object player;

    public Game()
    {
        window = new GLWindow(1440, 900, "Triangle vs. Squares");
        enemys = new ArrayList<Object>();
        bullets = new ArrayList<Object>();

        vec4 playerColor = new vec4(0.0f, 1.0f, 1.0f, 1.0f);
        player = new Object(Shapes.triangle(), playerColor);
        player.scale = new vec2(10.0f, 10.0f);
        player.pos = new vec2(2.5f, 50.0f);
        player.rotation = (float)Math.PI * 3 / 2;
        objects = new ArrayList<Object>();
        float lastSpawn = 0.0f;

        loop();
    }

    public vec2 movePlayer(long glfwWindow, vec2 pos, float deltaTime)
    { 
        float speed = 50.0f * deltaTime;

        if (glfwGetKey(glfwWindow, GLFW_KEY_W) == GLFW_PRESS)
        {
            pos.y += speed;
        }
        if (glfwGetKey(glfwWindow, GLFW_KEY_S) == GLFW_PRESS)
        {
            pos.y -= speed;
        }

        if (pos.y < 5.0f)
        {
            pos.y = 5.0f;
        }
        else if (pos.y > 95.0f)
        {
            pos.y = 95.0f;
        }
        return pos;
    }

    public void spawnEnemys()
    {
        if (window.getTime() - lastSpawn >= 1)
        {
            
        }
    }

    public void combineObjs()
    {
        objects = ArrayOps.concatList(enemys, bullets);
        objects.add(player);
    }

    public void loop()
    {
        while (!window.windowShouldClose())
        {
            currentFrame = (float)window.getTime();
            deltaTime = currentFrame - lastFrame;
            player.pos = movePlayer(window.getGLFW(), player.pos, deltaTime);
            combineObjs();
            window.drawFrame(objects);
            
            lastFrame = currentFrame;
        }
    }

}