import static org.lwjgl.opengl.GL43.*;

public class Object
{
    float[] data;
    float rotation;
    vec2 pos;
    vec2 scale;
    vec2 velocity;
    VBO oVbo;
    VAO oVao;

    public Object(float[] vertices, vec4 color)
    {
        // Make sure that vertices is divisable by 3
        assert vertices.length % 3 == 0 : "Vertices must be in groups of the 3 points forming a triangle!";

        makeData(vertices, color);
        makeVBO();
        makeVAO();
        oVao.UnBind();
        oVbo.UnBind();
    }

    private void makeData(float[] vertices, vec4 color)
    {
         // Build data array
         for (int i = 0, dP = 0; i < vertices.length; i += 3, dP += 3)
         {
             // Add vertices to data
             data[dP] = vertices[i];
             data[dP + 1] = vertices[i + 1];
             data[dP + 2] = vertices[i + 2];
 
             // Add color to data
             data[++dP + 2] = color.x;
             data[++dP + 2] = color.y;
             data[++dP + 2] = color.z;
         }
 
         pos = new vec2(0.0f, 0.0f);
         scale = new vec2(0.25f, 0.25f);
         velocity = new vec2(0.0f, 0.0f);
    }

    private void makeVBO()
    {
        oVbo = new VBO(data);
    }

    private void makeVAO()
    {
        int[] vaoInst = {2, 3};
        oVao = new VAO(vaoInst, 5);
    }

    private void makeModelMatrix()
    {
        // Translation Matrix
        vec3 r1 = new vec3(1.0f, 0.0f, pos.x);
        vec3 r2 = new vec3(0.0f, 1.0f, pos.y);
        vec3 r3 = new vec3(0.0f, 0.0f, 1.0f);
        mat4 translation = new mat4(r1, r2, r3);
    }

    public void Draw(ShaderProgram shader)
    {
        shader.EnableProgram();
        oVao.Bind();

        glDrawArrays(GL_TRIANGLES, 0, data.length / 2);
    }
}
