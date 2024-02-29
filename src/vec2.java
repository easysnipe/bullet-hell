public class vec2 extends vec1
{
    float y;
    public vec2(float X, float Y)
    {
        super(X);
        y = Y;
    }

    public float[] getArr()
    {
        float[] arr = {this.x, this.y};
        return arr;
    }
}