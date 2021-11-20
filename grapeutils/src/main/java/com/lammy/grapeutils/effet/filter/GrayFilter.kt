package com.lammy.grapeutils.effet.filter

import com.lammy.grapeutils.effet.common.ShaderConstant

class GrayFilter:LYFilter() {
    override var vertexShader = ShaderConstant.NO_FILTER_VERTEX_SHADER
    override var fragmentShader =  "precision mediump float;\n" +
            "varying vec2 textureCoordinate;\n" +
            "uniform sampler2D inputTexture;\n" +
            "\n" +
            "void main()\n" +
            " {\n" +
            "   gl_FragColor = texture2D( inputTexture, textureCoordinate);\n" +
            "   float color = (gl_FragColor.r + gl_FragColor.g + gl_FragColor.b)/3.0;\n" +
            "   gl_FragColor = vec4(color, color,color, 1.0);\n" +
            " }"
}