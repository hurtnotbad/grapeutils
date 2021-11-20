package com.lammy.grapeutils.effet.animation

import android.opengl.GLES20
import com.lammy.effect.glutils.Matrix2DUtils.getIdentityMatrix
import com.lammy.grapeutils.effet.common.GLProgram
import com.lammy.grapeutils.effet.common.ShaderConstant
import com.lammy.grapeutils.effet.common.ShaderParameter
import com.lammy.grapeutils.utils.AssetsUtil

open class LYAnimation: GLProgram() {

    var duration = 500

    override var vertexShader =// ShaderConstant.SHOW_FILTER_VERTEX_SHADER
    """
            attribute vec4 vPosition;
            attribute vec2 inputTextureCoordinate;
            varying vec2 textureCoordinate;
            uniform mat4 matrix;
            void main()
            {
                gl_Position = matrix*vec4(vPosition.x, vPosition.y,0.0,1.0);
                textureCoordinate = vec2(inputTextureCoordinate.x, 1.0 - inputTextureCoordinate.y);
            }
        """.trimIndent()
    override var fragmentShader = ShaderConstant.NO_FILTER_FRAGMENT_SHADER

    override fun addParameters() {
        parameters["vPosition"] = ShaderParameter(
            "vPosition",
            ShaderParameter.TYPE_ATTRIBUTE,
            ShaderParameter.VALUE_TYPE_ATTRIBUTE_PARAMETER
        )
        parameters["inputTextureCoordinate"] = ShaderParameter(
            "inputTextureCoordinate",
            ShaderParameter.TYPE_ATTRIBUTE,
            ShaderParameter.VALUE_TYPE_ATTRIBUTE_PARAMETER
        )
        parameters["inputTexture"] = ShaderParameter(
            "inputTexture",
            ShaderParameter.TYPE_TEXTURE,
            ShaderParameter.VALUE_TYPE_IMAGE_TEXTURE
        )
        parameters["matrix"] = ShaderParameter(
            "matrix",
            ShaderParameter.TYPE_UNIFORM,
            ShaderParameter.VALUE_TYPE_FLOAT_MATRIX
        )
    }

    protected fun setMatrix(matrix: FloatArray) {
        parameters["matrix"]?.value = matrix
    }

    override fun setDefaultParameterValues() {
        parameters["vPosition"]?.value = ShaderParameter.AttributeParameter(ShaderConstant.POSITIONS, 2)
        parameters["inputTextureCoordinate"]?.value = ShaderParameter.AttributeParameter(
            ShaderConstant.COORDINATE, 2)
        parameters["inputTexture"]?.value = AssetsUtil.testInTexture
        parameters["matrix"]?.value = getIdentityMatrix()
    }

    open fun updateParameters(){

    }
    // must use in glThread
    override fun draw() {
        clearColor()
        initProgram()
        GLES20.glUseProgram(program)
        updateParameters()
        setParametersToShader()
        GLES20.glEnableVertexAttribArray(parameters["vPosition"]!!.location)
        GLES20.glEnableVertexAttribArray(parameters["inputTextureCoordinate"]!!.location)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)
        GLES20.glDisableVertexAttribArray(parameters["vPosition"]!!.location)
        GLES20.glDisableVertexAttribArray(parameters["inputTextureCoordinate"]!!.location)
    }

}