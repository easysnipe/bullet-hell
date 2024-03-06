import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;
import java.util.Random;

public class Game 
{
    float deltaTime;
    float currentFrame;
    float lastFrame = 0.0f;
    float lastBullet = 0.0f;
    GLWindow window;
    ArrayList<Object> enemies;
    ArrayList<Object> bullets;
    ArrayList<Object> objects;
    ArrayList<Object> stars;
    float lastSpawn;
    Object player;
    Object border;
    Object cover;

    Random rand = new Random();

    public Game()
    {
        window = new GLWindow(1440, 900, "Triangle vs. Squares");
        enemies = new ArrayList<Object>();
        bullets = new ArrayList<Object>();
        stars = new ArrayList<Object>();
        spawnInitialStars();

        vec4 playerColor = new vec4(0.0f, 1.0f, 1.0f, 1.0f);
        vec4 borderColor = new vec4(1.0f, 0.0f, 0.0f, 0.0f);
        player = new Object(Shapes.triangle(), playerColor);
        player.scale = new vec2(10.0f, 10.0f);
        player.pos = new vec2(2.5f, 50.0f);
        player.rotation = (float)Math.PI * 3 / 2;

        border = new Object(Shapes.square(), borderColor);
        border.pos = new vec2(5.0f, 50.0f);
        border.scale = new vec2(20.0f, 100.0f);

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
        if (glfwGetKey(glfwWindow, GLFW_KEY_SPACE) == GLFW_PRESS)
        {
            if (window.getTime() - lastBullet > 0.25f)
            {
                createBullet();
                lastBullet = (float)window.getTime();
            }
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
                enemy.scale = new vec2(10.0f, 10.0f);
                
                boolean overlapping = true;
                while (overlapping)
                {
                    overlapping = false;

                    for (Object curr : enemies)
                    {
                       if (checkCollision(enemy, curr))
                       {
                            overlapping = true;
                            break;
                       }
                    }

                    if (overlapping)
                    {
                        enemy.pos = new vec2(rand.nextFloat(100.0f, 200.0f), rand.nextFloat(100.0f));
                    }
                }

                enemies.add(enemy);
            }
            lastSpawn = (float)window.getTime();
        }

        despawnEntities(enemies, new vec2(-10.0f, 1000.0f));
    }

    public boolean checkCollision(Object obj1, Object obj2)
    {
        float obj1Rad = (obj1.scale.x);
        float obj2Rad = (obj2.scale.x);
        float contactPoint = obj1Rad + obj2Rad;
        boolean xMatch = false;
        boolean yMatch = false;

        if (obj1.pos.x > obj2.pos.x - contactPoint)
        {
            if (obj1.pos.x < obj2.pos.x + contactPoint)
            {
                xMatch = true;
            }
        }

        if (obj1.pos.y > obj2.pos.y - contactPoint)
        {
            if (obj1.pos.y < obj2.pos.y + contactPoint)
            {
                yMatch = true;
            }
        }

        return (xMatch && yMatch);
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
        despawnEntities(stars, new vec2(-10.0f, 1000.0f));
    }

    public void createBullet()
    {
        vec4 color = new vec4(1.0f, 1.0f, 1.0f, 1.0f);
        Object bullet = new Object(Shapes.triangle(), color);
        bullet.pos = new vec2(player.pos.x + player.scale.x / 2, player.pos.y);
        bullet.scale = new vec2(2.0f, 2.0f);
        bullets.add(bullet);
    }

    public void rotateEntities(ArrayList<Object> entities, float speed)
    {
        for (Object entity : entities)
        {
            entity.rotation += deltaTime * speed;
        }
    }

    public void despawnEntities(ArrayList<Object> entities, vec2 limit)
    {
        for (int i = 0; i < entities.size(); ++i)
        {
            if (entities.get(i).pos.x < limit.x)
            {
                entities.remove(i);
            }
            else if (entities.get(i).pos.x > limit.y)
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
            entity.pos.x += offset;
        }
    }

    public void checkHit()
    {
        for (int i = 0; i < bullets.size(); ++i)
        {
            for (int o = 0; o < enemies.size(); ++o)
            {
                if (checkCollision(bullets.get(i), enemies.get(o)))
                {
                    bullets.remove(i);
                    enemies.remove(o);
                    return;
                }
            }
        }
    }

    public void combineObjs()
    {
        objects = ArrayOps.concatList(stars, enemies);
        objects = ArrayOps.concatList(objects, bullets);
        objects.add(player);
        objects.add(border);
    }

    public void checkDeath()
    {
        for (Object enemy : enemies)
        {
            if (checkCollision(enemy, border))
            {
                Object cover = new Object(Shapes.square(), new vec4(1.0f, 0.0f, 0.0f, 1.0f));
                cover.scale = new vec2(1000.0f, 1000.0f);
                border = cover;
            }
        }
    }

    public void loop()
    {
        while (!window.windowShouldClose())
        {
            currentFrame = (float)window.getTime();
            deltaTime = currentFrame - lastFrame;
            
            // Player
            player.pos = movePlayer(window.getGLFW(), player.pos, deltaTime);

            // Stars
            spawnStars();
            moveEntities(stars, -20.0f);
            
            // Enemies
            spawnEnemies();
            moveEntities(enemies, -10.0f);

            // Bullets
            moveEntities(bullets, 80.0f);
            rotateEntities(bullets, 50.0f);
            despawnEntities(bullets, new vec2(0.0f, 102.0f));

            checkHit();
            checkDeath();
            combineObjs();
            window.drawFrame(objects);
            
            lastFrame = currentFrame;
        }
    }

}