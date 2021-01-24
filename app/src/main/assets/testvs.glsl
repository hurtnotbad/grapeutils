attribute vec4 vPosition;
attribute vec2 inputTextureCoordinate;
varying vec2 textureCoordinate;
uniform mat4 matrix;
void main()
{
    gl_Position = matrix*vec4(vPosition.x, -vPosition.y,0.0,1.0);
    textureCoordinate = inputTextureCoordinate;
}