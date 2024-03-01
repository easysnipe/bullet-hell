public class mat2
{
    vec2 r1, r2;
    public mat2(vec4 row1, vec4 row2)
    {
            r1 = row1;
            r2 = row2;
    }

    // Constructer for direct 2d array to matrix
    public mat2(float[][] matrix)
    {
        r1 = new vec2(matrix[0]);
        r2 = new vec2(matrix[1]);
    }

    public float[] getArr()
    {
        float[] arr = ArrayOps.concat(r1.getArr(), r2.getArr());
        return arr;
    }

    public float[][] getArr2d()
    {
        float[][] arr2d = {r1.getArr(), r2.getArr()};
        return arr2d;
    }
}
