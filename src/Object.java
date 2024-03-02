import static org.lwjgl.opengl.GL43.*;
import java.lang.Math;

public class Object
{
    float[] data;
    float[] verts;
    float[] points;
    vec4 col;
    float rotation;
    mat3 model;
    mat3 projection;
    vec2 pos;
    vec2 scale;
    vec2 velocity;
    VBO oVbo;
    VAO oVao;
    VAO pVao;
    VAO psVao;

    public Object(float[] vertices, vec4 color)
    {
        // Make sure that vertices is divisable by 3
        assert vertices.length % 3 == 0 : "Vertices must be in groups of the 3 points forming a triangle!";

        data = makeData(vertices, color);
        makeVBO();
        makeVAO();
        oVao.UnBind();
        oVbo.UnBind();

        pVao = createCenterPoint();
        col = color;
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

    private void addPoint()
    {
        float[] point3 = new float[3];

        for (int i = 0; i < 2; ++i)
        {
            point3[i] = data[i];
        }
        point3[2] = 0.0f;

        float[][] point2d = new float[3][1];
        point2d[0][0] = point3[0];
        point2d[1][0] = point3[1];
        point2d[2][0] = point3[2];

        float[][] modelProj = MatrixMath.Mult(model.getArr2d(), projection.getArr2d());
        point2d = MatrixMath.Mult(modelProj, point2d);
        float[] point2 = {point2d[0][0], point2d[0][1]};

        float[] point6 = ArrayOps.concat(point2, col.getArr());
        points = ArrayOps.concat(points, point6);
        VBO psVbo = new VBO(points);
        int[] vaoInst = {2, 4};
        psVao = new VAO(vaoInst, 6);
    }

    private VAO createCenterPoint()
    {
        float[] arr = {0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f};
        int[] vaoInst = {2, 4};
        VBO vbo = new VBO(arr);
        VAO vao = new VAO(vaoInst, 4);
        vbo.UnBind();
        vao.UnBind();
        return vao;
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
        
        model = new mat3(MatrixMath.Mult(scaleMat.getArr2d(), rotationMat.getArr2d()));
        model = new mat3(MatrixMath.Mult(model.getArr2d(), translationMat.getArr2d()));
        //model = translationMat;
    }
    
    public void rotate(float radians)
    {
        rotation += radians;
    }

    public void Draw(ShaderProgram shader)
    {
        makeModelMatrix();
        addPoint();
        shader.SetMat3Uniform("model", model);
        shader.EnableProgram();
        oVao.Bind();

        glDrawArrays(GL_TRIANGLES, 0, data.length / 2);
        oVao.UnBind();

        pVao.Bind();
        glDrawArrays(GL_POINTS, 0, 1);
        pVao.UnBind();

        psVao.Bind();
        glDrawArrays(GL_POINTS, 0, points.length / 3);
        psVao.UnBind();
    }
}
