public class vec3 extends vec2
{
    float z;
    public vec3(float X, float Y, float Z)
    {
        super(X, Y);
        z = Z;
    }

    public float[] getArr()
    {
        float[] arr = {this.x, this.y, this.z};
        return arr;
    }
}