#version 300 es
precision mediump float;
uniform sampler2D sTexture;
uniform vec3 uColor;
in vec2 vTextureCoord;
out vec4 fragColor;

void main()
{
    //fragColor = texture(sTexture, vTextureCoord);
    vec4 tempColor = texture(sTexture, vTextureCoord);
    //fragColor = vec4((tempColor.r-uColor.r/2.0)*tempColor.a,(tempColor.g-uColor.g/2.0)*tempColor.a,(tempColor.b-uColor.b/2.0)*tempColor.a,tempColor.a);

    //float color = (tempColor.r+tempColor.g+tempColor.b)/3.0-1.0;
    fragColor = vec4(
            uColor.r,
            uColor.g,
            uColor.b,
            tempColor.a
        );
    //fragColor = vec4((tempColor.r+uColor.r/4.0)*tempColor.a,(tempColor.g+uColor.g/4.0)*tempColor.a,(tempColor.b+uColor.b/4.0)*tempColor.a,tempColor.a);
    //float avg = (tempColor.r+tempColor.g+tempColor.b)/3.0;
    //fragColor = vec4(uColor.r*tempColor.a,uColor.g*tempColor.a,uColor.b*tempColor.a,tempColor.a);
}