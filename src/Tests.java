import java.io.File;

public class Tests
{
    public static void main(String[] args)
    {
        File testf = new File("resources\\vertex.vert");
        System.out.println(testf.getAbsolutePath());

        float[] coordsArr = {1.0f, 2.0f, 3.0f};
        vec3 coords = new vec3(coordsArr[0], coordsArr[1], coordsArr[2]);
        float[] results = {coords.x, coords.y, coords.z};

        boolean test = true;
        for (int i = 0; i < 3; ++i)
        {
            if (coordsArr[i] != results[i])
            {
                test = false;
                break;
            }
        }

        if (test)
        {
            System.out.println("Vectors test passed!");
        }
        else
        {
            System.out.println("Vectors test failed!");
        }

        float[][] matrix1 = {{1, 2}, {3, 4}};
        float[][] matrix2 = {{5, 6}, {7, 8}};

        float[][] result = MatrixMath.Mult(matrix1, matrix2);
        float[][] ans = {{19.0f, 22.0f}, {43.0f, 50.0f}};

        test = true;

        for (int i = 0; i < result.length; ++i)
        {
            for (int o = 0; o < result[0].length; ++o)
            {
                if (result[i][o] != ans[i][o])
                {
                    test = false;
                    break;
                }
            }
        }

        if (test)
        {
            System.out.println("Matracies test passed!");
        }
        else
        {
            System.out.println("Matracies test failed!");
        }

    }
}
