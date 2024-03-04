import java.util.ArrayList;
import org.lwjgl.BufferUtils;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL43.*;

public class VBO
{
    int ID;
    public VBO(float[] vertices)
    {
        ID = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, ID);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
    }

    public VBO(ArrayList<Float> vertices)
    {
        ID = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, ID);

        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.size());
        float[] arr = new float[vertices.size()];
        int cnt = 0;
        for (float curr : vertices)
        {
            arr[cnt++] = curr;
        }
        
       glBufferData(GL_ARRAY_BUFFER, arr, GL_STATIC_DRAW);
    }

    public void Bind()
    {
        glBindBuffer(GL_ARRAY_BUFFER, ID);
    }

    public void UnBind()
    {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void Delete()
    {
        glDeleteBuffers(ID);
    }
}
