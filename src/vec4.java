public class vec4 extends vec3
{
    float a;
    public vec4(float X, float Y, float Z, float A)
    {
        super(X, Y, Z);
        a = A;
    }

    // Constructer for direct array into vector
    public vec4(float[] vector)
    {
        super(vector[0], vector[1], vector[2]);

        a = vector[3];
    }

    public float[] getArr()
    {
        float[] arr = {this.x, this.y, this.z, this.a};
        return arr;
    }
}
