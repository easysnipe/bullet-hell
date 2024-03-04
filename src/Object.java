import static org.lwjgl.opengl.GL43.*;
import java.lang.Math;
import java.util.ArrayList;

public class Object
{
    float[] data;
    float[] verts;
    boolean debug;
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
    VBO psVbo;
    ArrayList<Float> points;

    public Object(float[] vertices, vec4 color)
    {
        // Make sure that vertices is divisable by 3
        assert vertices.length % 3 == 0 : "Vertices must be in groups of the 3 points forming a triangle!";

        data = makeData(vertices, color);
        verts = vertices;
        makeVBO();
        makeVAO();
        oVao.UnBind();
        oVbo.UnBind();

        pVao = createCenterPoint();
        col = color;
        pos = new vec2(0.0f, 0.0f);
        scale = new vec2(0.25f, 0.25f);
        velocity = new vec2(0.0f, 0.0f);
        points = new ArrayList<Float>();
        psVbo = new VBO(points);
        debug = false;
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
        //points.add(1.0f);
        for (int i = 0; i < verts.length; i += 2)
        {
            float[][] point2d = new float[3][1];
            point2d[0][0] = verts[i];
            point2d[1][0] = verts[i + 1];
            point2d[2][0] = 0.0f;

            float[][] modelProj = MatrixMath.Mult(model.getArr2d(), projection.getArr2d());
            point2d = MatrixMath.Mult(modelProj, point2d);
            float[] point2 = {point2d[0][0] * -1, point2d[1][0]};

            float[] color = {1.0f, 0.0f, 0.0f, 1.0f};
            float[] point6 = ArrayOps.concat(point2, color);
            for (int o = 0; o < point6.length; ++o)
            {
                points.add(point6[o]);
                System.out.println(points.size());
            }
        }
        if (points.size() > verts.length + verts.length * 4)
        {
            psVbo.Delete();
            psVao.Delete();
        }
        System.out.println(points.size());
        //assert points.size() < 6200 : "Points bigger than 6000";
        psVbo = new VBO(points);
        int[] vaoInst = {2, 4};
        psVao = new VAO(vaoInst, 6);
        psVbo.UnBind();
        psVao.UnBind();
    }

    private VAO createCenterPoint()
    {
        float[] arr = {0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f};
        int[] vaoInst = {2, 4};
        VBO vbo = new VBO(arr);
        VAO vao = new VAO(vaoInst, 6);
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
        shader.EnableProgram();
        shader.SetIntUniform("skip", 0);
        shader.SetMat3Uniform("model", model);

        oVao.Bind();
        glDrawArrays(GL_TRIANGLES, 0, data.length);
        oVao.UnBind();

        if (debug)
        {
            addPoint();
            pVao.Bind();
            glDrawArrays(GL_POINTS, 0, 1);
            pVao.UnBind();
            
            psVao.Bind();
            shader.SetIntUniform("skip", 1);
            glDrawArrays(GL_POINTS, 0, points.size());
            System.out.println("Draw Complete!");
            psVao.UnBind();
        }
    }
}
