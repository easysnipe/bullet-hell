public class mat3
{
    vec3 r1, r2, r3;
    public mat3(vec3 row1, vec3 row2, vec3 row3)
    {
            r1 = row1;
            r2 = row2;
            r3 = row3;
    }

    // Constructer for direct 2d array to matrix
    public mat3(float[][] matrix)
    {
        r1 = new vec3(matrix[0]);
        r2 = new vec3(matrix[1]);
        r3 = new vec3(matrix[2]);
    }

    public float[] getArr()
    {
        float[] arr = ArrayOps.concat(r1.getArr(), r2.getArr());
        arr = ArrayOps.concat(arr, r3.getArr());
        return arr;
    }

    public float[][] getArr2d()
    {
        float[][] arr2d = {r1.getArr(), r2.getArr(), r3.getArr()};
        return arr2d;
    }

    public void print()
    {
        System.out.print('\n');
        r1.print();
        System.out.print('\n');
        r2.print();
        System.out.print('\n');
        r3.print();
    }
}