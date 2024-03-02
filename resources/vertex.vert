#version 430 core
layout (location = 0) in vec2 aPos;
layout (location = 1) in vec4 aColor;

out vec4 color;

uniform mat3 model;
uniform mat3 projection;

void main()
{
    color = aColor;
    vec3 Pos = model * projection * vec3(aPos, 0.0);
    if (Pos.z > 0.000010)
           Pos = vec3(0.0, 0.0, 0.0);
    gl_Position = vec4(Pos, 1.0);
}