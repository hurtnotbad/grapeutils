package com.lammy.grapeutils.effet.filter

import android.opengl.GLES20
import com.lammy.grapeutils.effet.common.*
import com.lammy.grapeutils.effet.common.ShaderConstant.Companion.COORDINATE
import com.lammy.grapeutils.effet.common.ShaderConstant.Companion.POSITIONS
import com.lammy.grapeutils.utils.AssetsUtil

open class LYFilter : GLProgram() {
    override var vertexShader = ShaderConstant.SHOW_FILTER_VERTEX_SHADER
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
    }

    override fun setDefaultParameterValues() {
        parameters["vPosition"]?.value = ShaderParameter.AttributeParameter(POSITIONS, 2)
        parameters["inputTextureCoordinate"]?.value = ShaderParameter.AttributeParameter(COORDINATE, 2)
        parameters["inputTexture"]?.value = AssetsUtil.testInTexture

    }

    open fun setInTexture(texture: Texture){
        parameters["inputTexture"]?.value = texture
    }

    open fun getInTexture(): Texture{
       return parameters["inputTexture"]?.value as Texture
    }

    // must use in glThread
    override fun draw() {
        clearColor()
        initProgram()
        GLES20.glUseProgram(program)
        setParametersToShader()
        GLES20.glEnableVertexAttribArray(parameters["vPosition"]!!.location)
        GLES20.glEnableVertexAttribArray(parameters["inputTextureCoordinate"]!!.location)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)
        GLES20.glDisableVertexAttribArray(parameters["vPosition"]!!.location)
        GLES20.glDisableVertexAttribArray(parameters["inputTextureCoordinate"]!!.location)
    }
}