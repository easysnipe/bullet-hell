import static org.lwjgl.opengl.GL43.*;

public class VAO
{
    int ID;
    public VAO(int[] details, int totalSize)
    {
        ID = glGenVertexArrays();
        glBindVertexArray(ID);

        int offset = 0;
        for (int i = 0; i < details.length; ++i)
        {
            glVertexAttribPointer(i, details[i], GL_FLOAT, false, totalSize * (Float.SIZE / 8), offset);
            glEnableVertexAttribArray(i);
            offset += details[i];
        }
    }

    public void Bind()
    {
        glBindVertexArray(ID);
    }

    public void UnBind()
    {
        glBindVertexArray(0);
    }

    public void Delete()
    {
        glDeleteVertexArrays(ID);
    }
}
