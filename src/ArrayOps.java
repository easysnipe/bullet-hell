public class ArrayOps
{
    public static float[] concat(float[] arr1, float[] arr2)
    {
        float arr[] = arr1;

        for (int i = 0; i < arr2.length; ++i)
        {
            arr[arr1.length + i] = arr2[i];
        }
        return arr;
    }
}
