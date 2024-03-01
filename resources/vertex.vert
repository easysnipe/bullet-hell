#version 430 core
layout (location = 0) in vec2 aPos;
layout (location = 1) in vec4 aColor;

out vec4 color;

uniform mat3 model;
uniform mat3 view;

void main()
{
    color = aColor;
    vec3 Pos = model * vec3(aPos, 0.0);
    //Pos.z = 0.0;    
    gl_Position = vec4(Pos, 1.0);
}