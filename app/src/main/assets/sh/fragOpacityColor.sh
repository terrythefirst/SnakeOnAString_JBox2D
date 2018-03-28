#version 300 es
precision mediump float;
uniform sampler2D sTexture;
uniform float uDownFactor;
uniform float uOpacityFactor;
uniform vec3 uColor;
in vec2 vTextureCoord;
out vec4 fragColor;

void main()
{
    vec4 tempColor = texture(sTexture, vTextureCoord);
    //基础方案 颜色叠加 易泛白
    //fragColor = vec4((tempColor.r+uColor.r)*tempColor.a*(uDownFactor),(tempColor.g+uColor.g)*tempColor.a*(uDownFactor),(tempColor.b+uColor.b)*tempColor.a*(uDownFactor),tempColor.a);
    //用颜色代替方案 过于深
    //fragColor = vec4((uColor.r)*tempColor.a*(uDownFactor),(uColor.g)*tempColor.a*(uDownFactor),(uColor.b)*tempColor.a*(uDownFactor),tempColor.a);

    fragColor = vec4(
                tempColor.a*uColor.r*(uDownFactor),
                tempColor.a*uColor.g*(uDownFactor),
                tempColor.a*uColor.b*(uDownFactor),
                tempColor.a*uOpacityFactor
            );

    //fragColor = vec4((tempColor.r*(uDownFactor)+uColor.r)*tempColor.a,(tempColor.g*(uDownFactor)+uColor.g)*tempColor.a,(tempColor.b*(uDownFactor)+uColor.b)*tempColor.a,tempColor.a);
    //fragColor = vec4(tempColor.r*(uDownFactor)*tempColor.a,tempColor.g*(uDownFactor)*tempColor.a,tempColor.b*(uDownFactor)*tempColor.a,tempColor.a);

    //fragColor = texture(sTexture, vTextureCoord);
}