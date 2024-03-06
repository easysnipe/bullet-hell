public class Shapes
{
    public static float[] triangle()
    {
        float[] triangleVerts = {
        0.0f, (2 * (float)Math.sqrt(3)/3),
        1.0f, (-1.0f * (float)Math.sqrt(3) / 3),
        -1.0f, (-1.0f * (float)Math.sqrt(3) / 3)
        };

        return triangleVerts;
    }

    public static float[] square()
    {
        float[] squareVerts = {
        1.0f, 1.0f,
        -1.0f, 1.0f,
        1.0f, -1.0f,
        -1.0f, -1.0f,
        1.0f, -1.0f,
        -1.0f, 1.0f
        };

        return squareVerts;
    }
}
