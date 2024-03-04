import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import org.lwjgl.system.*;
import java.nio.*;

import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.system.MemoryStack.*;

public class ShaderProgram
{
    int ID;
    File vertPath;
    File fragPath;

    public ShaderProgram(File vertexPath, File fragmentPath)
    {
        vertPath = vertexPath;
        fragPath = fragmentPath;
    }

    public void CompileShader()
    {
        // Vertex Shader
        System.out.println("Compiling Vertex Shader ...");
        int vertexShader = CompileShader(GL_VERTEX_SHADER, vertPath);
        System.out.println("Compiling Fragment Shader ...");
        int fragmentShader = CompileShader(GL_FRAGMENT_SHADER, fragPath);

        // Shader Linking
        ID = glCreateProgram();
        glAttachShader(ID, vertexShader);
        glAttachShader(ID, fragmentShader);
        glLinkProgram(ID);

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }

    public int CompileShader(int type, File ShaderPath)
    {
        int shader = glCreateShader(type);
        try
        {
            // Read shader file into string
            Scanner reader = new Scanner(ShaderPath);
            String shaderCode = "";

            while (reader.hasNextLine())
            {
                shaderCode = shaderCode + '\n' + reader.nextLine();
            }
            glShaderSource(shader, shaderCode);
            glCompileShader(shader);

            if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0)
            {
                if (type == GL_VERTEX_SHADER)
                {
                    System.out.println("VERTEX SHADER ERROR::" + '\n' + glGetShaderInfoLog(shader, 2048));
                }
                else if (type == GL_FRAGMENT_SHADER)
                {
                    System.out.println("FRAGMENT SHADER ERROR::" + '\n' + glGetShaderInfoLog(shader, 2048));
                }
                else 
                {
                    System.out.println("UNKOWN SHADER ERROR::" + '\n' + glGetShaderInfoLog(shader, 2048));
                }
                reader.close();
                return -1;
            }
            reader.close();
        }
        catch (FileNotFoundException e)
        {
            if (type == GL_VERTEX_SHADER)
            {
                System.out.println("Vertex Shader File Not Found!");
            }
            else if (type == GL_FRAGMENT_SHADER)
            {
                System.out.println("Fragment Shader File Not Found!");
            }
            else
            {
                System.out.println("Unkown Shader File Not Found!");
            }
            return -1;
        }
        return shader;
    }

    public void EnableProgram()
    {
        glUseProgram(ID);
    }

    public void DisableProgram()
    {
        glUseProgram(0);
    }

    public void DeleteProgram()
    {
        glDeleteProgram(ID);
    }

    public void SetIntUniform(String name, int num)
    {
        int loc = glGetUniformLocation(ID, name);
        int[] nums = {num};
        if (loc < 0)
        {
            System.out.println("Uniform not found!");
            return;
        }
        else
        {
            try (MemoryStack stack = stackPush())
            {
                IntBuffer numIn = stack.mallocInt(1);
                numIn.put(num);
                glUniform1i(loc, num);
            }
        }
    }

    public void SetFloatUniform(String name, float num)
    {
        int loc = glGetUniformLocation(ID, name);
        if (loc < 0)
        {
            System.out.println("Uniform not found!");
            return;
        }
        else
        {
            try (MemoryStack stack = stackPush())
            {
                FloatBuffer numIn = stack.mallocFloat(1);
                numIn.put(num);
                glUniform1fv(loc, numIn);
            }
        }
    }

    public void SetVec2Uniform(String name, vec2 vec)
    {
        int loc = glGetUniformLocation(ID, name);
        if (loc < 0)
        {
            System.out.println("Uniform not found!");
            return;
        }
        else
        {
            try (MemoryStack stack = stackPush())
            {
                FloatBuffer numIn = stack.mallocFloat(2);
                numIn.put(vec.getArr());
                glUniform1fv(loc, numIn);
            }
        }
    }

    public void SetVec3Uniform(String name, vec3 vec)
    {
        int loc = glGetUniformLocation(ID, name);
        if (loc < 0)
        {
            System.out.println("Uniform not found!");
            return;
        }
        else
        {
            try (MemoryStack stack = stackPush())
            {
                FloatBuffer numIn = stack.mallocFloat(3);
                numIn.put(vec.getArr());
                glUniform1fv(loc, numIn);
            }
        }
    }

    public void SetVec4Uniform(String name, vec4 vec)
    {
        int loc = glGetUniformLocation(ID, name);
        if (loc < 0)
        {
            System.out.println("Uniform not found!");
            return;
        }
        else
        {
            try (MemoryStack stack = stackPush())
            {
                FloatBuffer numIn = stack.mallocFloat(4);
                numIn.put(vec.getArr());
                glUniform1fv(loc, numIn);
            }
        }
    }

    public void SetMat4Uniform(String name, mat4 mat)
    {
        int loc = glGetUniformLocation(ID, name);
        if (loc < 0)
        {
            System.out.println("Uniform not found!");
            return;
        }
        else
        {
            try (MemoryStack stack = stackPush())
            {
                FloatBuffer numIn = stack.mallocFloat(16);
                numIn.put(mat.getArr());
                glUniform1fv(loc, numIn);
            }
        }
    }

    public void SetMat3Uniform(String name, mat3 mat)
    {
        int loc = glGetUniformLocation(ID, name);
        if (loc < 0)
        {
            System.out.println("Uniform not found!");
            return;
        }
        else
        {
            try (MemoryStack stack = stackPush())
            {
                FloatBuffer numIn = stack.mallocFloat(9);
                numIn.put(mat.getArr());
                glUniformMatrix3fv(loc, false, mat.getArr());
            }
        }
    }

    public void SetBoolUniform(String name, boolean bool)
    {
        int loc = glGetUniformLocation(ID, name);

        int num = (bool) ? 1 : 0;

        if (loc < 0)
        {
            System.out.println("Uniform not found!");
            return;
        }
        else
        {
            try (MemoryStack stack = stackPush())
            {
                IntBuffer numIn = stack.mallocInt(1);
                numIn.put(num);
                glUniform1iv(loc, numIn);
            }
        }
    }

}