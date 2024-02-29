public class vec4 extends vec3
{
    float a;
    public vec4(float X, float Y, float Z, float A)
    {
        super(X, Y, Z);
        a = A;
    }

    public float[] getArr()
    {
        float[] arr = {this.x, this.y, this.z, this.a};
        return arr;
    }
}
