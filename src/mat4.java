public class mat4
{
    vec4 r1, r2, r3, r4;
    public mat4(vec4 row1, vec4 row2, vec4 row3, vec4 row4)
    {
            r1 = row1;
            r2 = row2;
            r3 = row3;
            r4 = row4;
    }

    public float[] getArr()
    {
        float[] arr = ArrayOps.concat(r1.getArr(), r2.getArr());
        arr = ArrayOps.concat(arr, r3.getArr());
        arr = ArrayOps.concat(arr, r4.getArr());
        return arr;
    }

    public float[][] getArr2d()
    {
        float[][] arr2d = {r1.getArr(), r2.getArr(), r3.getArr(), r4.getArr()};
        return arr2d;
    }
}
