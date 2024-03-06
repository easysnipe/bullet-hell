#version 430 core
layout (location = 0) in vec2 aPos;
layout (location = 1) in vec4 aColor;

out vec4 color;

uniform mat3 model;
uniform int skip;
uniform mat3 projection;
uniform float x;
uniform float y;

void main()
{
    color = aColor;
    if (skip == 0)
    {
        vec3 Pos = projection * model * vec3(aPos, 1.0);  // Remember PVM
        Pos.x += x;
        Pos.y += y;
        gl_Position = vec4(Pos, 1.0);
    }
    else
    {
        gl_Position = vec4(aPos, 0.0, 1.0);
    }

}