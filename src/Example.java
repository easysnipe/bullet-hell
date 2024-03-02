public class Example
{
    public static void main(String[] args)
    {
        GLWindow window = new GLWindow(600, 600, "Example Window");

        float[] triangleVerts = {
        0.0f, 1.0f,
        1.0f, -1.0f,
        -1.0f, -1.0f
        };
        
        Object triangle = new Object(triangleVerts, new vec4(1.0f, 1.0f, 1.0f, 1.0f));

        Object[] objects = {triangle};

        while (!window.windowShouldClose())
        {
            window.drawFrame(objects);
            triangle.rotation = (float)window.getTime();
            triangle.scale = new vec2(100.0f, 100.0f);
        }
    }
}
