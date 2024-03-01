import static org.lwjgl.opengl.GL43.*;
import java.lang.Math;

public class Object
{
    float[] data;
    float[] verts;
    float rotation;
    mat3 model;
    vec2 pos;
    vec2 scale;
    vec2 velocity;
    VBO oVbo;
    VAO oVao;

    public Object(float[] vertices, vec4 color)
    {
        // Make sure that vertices is divisable by 3
        assert vertices.length % 3 == 0 : "Vertices must be in groups of the 3 points forming a triangle!";

        data = makeData(vertices, color);
        makeVBO();
        makeVAO();
        oVao.UnBind();
        oVbo.UnBind();

        pos = new vec2(0.0f, 0.0f);
        scale = new vec2(0.25f, 0.25f);
        velocity = new vec2(0.0f, 0.0f);
        rotation = 0.0f;
    }

    private float[] makeData(float[] vertices, vec4 color)
    {
        // Build data array
        verts = vertices;
        float[] output = new float[vertices.length + (vertices.length / 2) * 4];
        for (int i = 0, dP = 0; i < vertices.length; i += 2, dP += 2)
        {
           // Add vertices to data
           output[dP] = vertices[i];
           output[dP + 1] = vertices[i + 1];
 
           // Add color to data
           output[++dP + 1] = color.x;
           output[++dP + 1] = color.y;
           output[++dP + 1] = color.z;
           output[++dP + 1] = color.a;
        }
        return (output);
    }

    private void makeVBO()
    {
        oVbo = new VBO(data);
    }

    private void makeVAO()
    {
        int[] vaoInst = {2, 4};
        oVao = new VAO(vaoInst, 6);
    }

    private void makeModelMatrix()
    {
        // Translation Matrix
        vec3 r1 = new vec3(1.0f, 0.0f, pos.x);
        vec3 r2 = new vec3(0.0f, 1.0f, pos.y);
        vec3 r3 = new vec3(0.0f, 0.0f, 1.0f);
        mat3 translationMat = new mat3(r1, r2, r3);

        // Rotatiton Matrix
        r1 = new vec3((float)Math.cos(rotation), -1 * (float)Math.sin(rotation), 0.0f);
        r2 = new vec3((float)Math.sin(rotation), (float)Math.cos(rotation),  0.0f);
        r3 = new vec3(0.0f, 0.0f, 1.0f);
        mat3 rotationMat = new mat3(r1, r2, r3);

        // Scale matrix
        r1 = new vec3(scale.x, 0.0f, 0.0f);
        r2 = new vec3(0.0f, scale.y, 0.0f);
        r3 = new vec3(0.0f, 0.0f, 1.0f);
        mat3 scaleMat = new mat3(r1, r2, r3);
        
        model = new mat3(MatrixMath.Mult(translationMat.getArr2d(), rotationMat.getArr2d()));
        model = new mat3(MatrixMath.Mult(model.getArr2d(), scaleMat.getArr2d()));
        //model = translationMat;
    }
    
    public void rotate(float radians)
    {
        rotation += radians;
    }

    public void Draw(ShaderProgram shader)
    {
        makeModelMatrix();
        shader.SetMat3Uniform("model", model);
        shader.EnableProgram();
        oVao.Bind();

        glDrawArrays(GL_TRIANGLES, 0, data.length / 2);
    }
}
