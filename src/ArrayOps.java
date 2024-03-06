import java.util.ArrayList;

public class ArrayOps
{
    public static float[] concat(float[] arr1, float[] arr2)
    {
        float arr[] = new float[arr1.length + arr2.length];

        for (int i = 0; i < arr1.length; ++i)
        {
            arr[i] = arr1[i];
        }

        for (int i = 0; i < arr2.length; ++i)
        {
            arr[arr1.length + i] = arr2[i];
        }
        return arr;
    }

    public static ArrayList concatList(ArrayList arr1, ArrayList arr2)
    {
        ArrayList arr = new ArrayList();

        for (int i = 0; i < arr1.size(); ++i)
        {
            arr.add(arr1.get(i));
        }

        for (int i = 0; i < arr2.size(); ++i)
        {
            arr.add(arr2.get(i));
        }
        return arr;
    }
}
