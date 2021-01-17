package com.lammy.grapeutils.effet.common

import com.lammy.effect.glutils.BufferUtil

class ShaderConstant {

    companion object{
        val POSITIONS = BufferUtil.floatToBuffer(floatArrayOf(
            -1.0f, 1.0f,
            -1.0f, -1.0f,
            1.0f, 1.0f,
            1.0f, -1f
        ))
        // texture coordinate
        var COORDINATE = BufferUtil.floatToBuffer(floatArrayOf(
            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
        ))
        const val INVALID_PROGRAM = -1
        const val INVALID_TEXTURE = -1
        const val INVALID_PARAMETER_LOCATION = -1

        const val FLOAT_SIZE = 4

        const val INPUT_TEXTURE_UNIFORM_STRING = "inputTexture"
        const val INPUT_MATRIX_UNIFORM_STRING = "matrix"
        const val POINTS_ATTRS_STRING = "vPosition"
        const val INPUT_TEXTURE_CORD_ATTRS_STRING = "inputTextureCoordinate"

        const val NO_FILTER_VERTEX_SHADER = "attribute vec4 vPosition;\n" +
                "attribute vec2 inputTextureCoordinate;\n" +
                "varying vec2 textureCoordinate;\n" +
                "void main()\n" +
                "           {\n" +
                "             gl_Position = vPosition;\n" +
                "             textureCoordinate = inputTextureCoordinate;\n" +
                "           }"

        const val SHOW_FILTER_VERTEX_SHADER = "attribute vec4 vPosition;\n" +
                "attribute vec2 inputTextureCoordinate;\n" +
                "varying vec2 textureCoordinate;\n" +
                "void main()\n" +
                "           {\n" +
                "             gl_Position = vPosition;\n" +
                "             textureCoordinate = vec2(inputTextureCoordinate.x , 1.0 - inputTextureCoordinate.y);\n" +
                "           }"

        const val NO_FILTER_FRAGMENT_SHADER = "precision mediump float;\n" +
                "varying vec2 textureCoordinate;\n" +
                "uniform sampler2D inputTexture;\n" +
                "\n" +
                "void main()\n" +
                " {\n" +
                "   gl_FragColor = texture2D( inputTexture, textureCoordinate);\n" +
                " }"
    }

}