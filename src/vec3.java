public class vec3 extends vec2
{
    float z;
    public vec3(float X, float Y, float Z)
    {
        super(X, Y);
        z = Z;
    }

    // Constructer for direct array into vector
    public vec3(float[] vector)
    {
        super(vector[0], vector[1]);

        z = vector[2];
    }

    public float[] getArr()
    {
        float[] arr = {this.x, this.y, this.z};
        return arr;
    }

    public void print()
    {
        System.out.print("{");
        System.out.print(x + ", ");
        System.out.print(y + ", ");
        System.out.print(z);
        System.out.print("}");

    }
}