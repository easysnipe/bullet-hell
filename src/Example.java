import java.lang.Math;

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

        System.out.println(triangleVerts[3]);
        
        Object triangle = new Object(triangleVerts, new vec4(1.0f, 1.0f, 1.0f, 1.0f));
        triangle.debug = true;

        Object[] objects = {triangle};

        while (!window.windowShouldClose())
        {
            window.drawFrame(objects);
            triangle.rotation = (float)window.getTime();
        }
    }
}