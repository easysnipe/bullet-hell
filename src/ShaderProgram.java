import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import static org.lwjgl.opengl.GL43.*;

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
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        try
        {
            // Read shader file into string
            Scanner reader = new Scanner(vertPath);
            String vertex = "";

            while (reader.hasNextLine())
            {
                vertex = vertex + '\n' + reader.nextLine();
            }
            glShaderSource(vertexShader, vertex);
            glCompileShader(vertexShader);

            if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) == 0)
            {
                System.out.println("VERTEX SHADER ERROR::" + '\n' + glGetShaderInfoLog(vertexShader, 2048));
                reader.close();
                return;
            }
            reader.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Vertex Shader File Not Found!");
            return;
        }

        // Fragment Shader
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        try
        {
            // Read shader file into string
            Scanner reader = new Scanner(fragPath);
            String fragment = "";

            while (reader.hasNextLine())
            {
                fragment = fragment + '\n' + reader.nextLine();
            }
            glShaderSource(fragmentShader, fragment);
            glCompileShader(fragmentShader);

            if (glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == 0)
            {
                System.out.println("FRAGMENT SHADER ERROR::" + '\n' + glGetShaderInfoLog(fragmentShader, 2048));
                reader.close();
                return;
            }
            reader.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Fragment Shader File Not Found!");
            return;
        }

        // Shader Linking
        ID = glCreateProgram();
        glAttachShader(ID, vertexShader);
        glAttachShader(ID, fragmentShader);
        glLinkProgram(ID);

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
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

}