public class vec2 extends vec1
{
    float y;
    public vec2(float X, float Y)
    {
        super(X);
        y = Y;
    }

    // Constructer for direct array into vector
    public vec2(float[] vector)
    {
        super(vector[0]);

        y = vector[1];
    }

    public float[] getArr()
    {
        float[] arr = {this.x, this.y};
        return arr;
    }
}