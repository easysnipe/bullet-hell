public class Object
{
    float[] data;
    Position pos;
    float scale;

    public Object(float[] vertices, float[] color)
    {
        // Make sure that vertices is divisable by 3
        if (vertices.length % 3 != 0)
        {
            System.out.println("Vertices must be composed of groups of 3 points that make a triangle!");
        }

        // Build data array
        for (int i = 0, dP = 0; i < vertices.length; i += 3, dP += 3)
        {
            // Add vertices to data
            data[dP] = vertices[i];
            data[dP + 1] = vertices[i + 1];
            data[dP + 2] = vertices[i + 2];

            // Add color to data
            data[++dP + 2] = color[0];
            data[++dP + 2] = color[1];
            data[++dP + 2] = color[2];
        }

        pos = new Position(0.0f, 0.0f);
    }
}
