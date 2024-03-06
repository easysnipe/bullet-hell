import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;
import java.util.Random;

public class Game 
{
    float deltaTime;
    float currentFrame;
    float lastFrame = 0.0f;
    GLWindow window;
    ArrayList<Object> enemies;
    ArrayList<Object> bullets;
    ArrayList<Object> objects;
    ArrayList<Object> stars;
    float lastSpawn;
    Object player;

    Random rand = new Random();

    public Game()
    {
        window = new GLWindow(1440, 900, "Triangle vs. Squares");
        enemies = new ArrayList<Object>();
        bullets = new ArrayList<Object>();
        stars = new ArrayList<Object>();
        spawnInitialStars();

        vec4 playerColor = new vec4(0.0f, 1.0f, 1.0f, 1.0f);
        player = new Object(Shapes.triangle(), playerColor);
        player.scale = new vec2(10.0f, 10.0f);
        player.pos = new vec2(2.5f, 50.0f);
        player.rotation = (float)Math.PI * 3 / 2;
        objects = new ArrayList<Object>();
        lastSpawn = 0.0f;

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

    public void spawnEnemies()
    {
        vec4 enemyColor = new vec4(1.0f, 0.0f, 0.0f, 1.0f);
        if (window.getTime() - lastSpawn >= 1)
        {
            for (int i = 0; i < 2; ++i)
            {
                Object enemy = new Object(Shapes.square(), enemyColor);
                enemy.pos = new vec2(rand.nextFloat(100.0f, 200.0f), rand.nextFloat(100.0f));
                
                boolean overlapping = true;
                while (overlapping)
                {
                    overlapping = false;

                    for (Object curr : enemies)
                    {
                       // if (curr.pos > enemy.pos)
                    }
                }


                enemy.scale = new vec2(10.0f, 10.0f);
                enemies.add(enemy);
            }
            lastSpawn = (float)window.getTime();
        }

        despawnEntities(enemies);
    }

    public void spawnInitialStars()
    {
        vec4 starColor = new vec4(1.0f, 1.0f, 1.0f, 1.0f);
        for (int i = 0; i < 500; ++i)
        {
            Object star = new Object(Shapes.square(), starColor);
            star.pos = new vec2(rand.nextFloat(0.0f, 120.0f), rand.nextFloat(0.0f, 100.0f));
            stars.add(star);
        }
    }

    public void spawnStars()
    {
        vec4 starColor = new vec4(1.0f, 1.0f, 1.0f, 1.0f);
        for (int i = 0; i < 2; ++i)
        {
            Object star = new Object(Shapes.square(), starColor);
            star.pos = new vec2(rand.nextFloat(100.0f, 200.0f), rand.nextFloat(100.0f));
            stars.add(star);
        }
        despawnEntities(stars);
    }

    public void despawnEntities(ArrayList<Object> entities)
    {
        for (int i = 0; i < entities.size(); ++i)
        {
            if (entities.get(i).pos.x < 0)
            {
                entities.remove(i);
            }
        }
    }

    public void moveEntities(ArrayList<Object> entities, float speed)
    {
        float offset = speed * deltaTime;
        for (Object entity : entities)
        {
            entity.pos.x -= offset;
        }
    }

    public void combineObjs()
    {
        objects = ArrayOps.concatList(stars, enemies);
        objects = ArrayOps.concatList(objects, bullets);
        objects.add(player);
    }

    public void loop()
    {
        while (!window.windowShouldClose())
        {
            currentFrame = (float)window.getTime();
            deltaTime = currentFrame - lastFrame;
            
            player.pos = movePlayer(window.getGLFW(), player.pos, deltaTime);

            spawnStars();
            spawnEnemies();
            moveEntities(stars, 20.0f);
            moveEntities(enemies, 10.0f);

            combineObjs();
            window.drawFrame(objects);
            
            lastFrame = currentFrame;
        }
    }

}