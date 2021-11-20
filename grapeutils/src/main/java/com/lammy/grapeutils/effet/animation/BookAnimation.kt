package com.lammy.grapeutils.effet.animation

import com.lammy.grapeutils.effet.common.ShaderConstant

class BookAnimation : LYAnimation() {

    override var vertexShader ="""
            attribute vec4 vPosition;
            attribute vec2 inputTextureCoordinate;
            varying vec2 textureCoordinate;
            uniform mat4 matrix;
            void main()
            {
                gl_Position = matrix*vec4(vPosition.x , -vPosition.y ,0.0,1.0);
                if(gl_Position.x > 0.0) {
                  gl_Position = vec4(vPosition.x * cos(-3.14/4.0), -vPosition.y * sin(-3.14/4.0),0.0,1.0);
                }
                textureCoordinate = inputTextureCoordinate;
            }
        """.trimIndent()

    override var fragmentShader = ShaderConstant.NO_FILTER_FRAGMENT_SHADER


}