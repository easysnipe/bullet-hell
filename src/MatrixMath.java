public class MatrixMath
{
    public static float[][]Mult(float[][] mat1, float[][] mat2)
    {
        float[][] mat = new float[mat1.length][mat1[0].length];

        for (int i = 0; i < mat1.length; ++i)
        {
            for (int o = 0; o < mat1[0].length; ++o)
            {
                float sum = 0;
                for (int p = 0; p < mat1.length; ++p)
                {
                    sum += mat1[i][p] * mat2[p][o];
                }
                mat[i][o] = sum;
            }
        }

        return mat;
    }
}
