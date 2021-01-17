package com.lammy.grapeutils.effet.common

import android.opengl.GLES20

class ShaderParameter(name1:String,type1:Int) {
    companion object {
        const val TYPE_UNIFORM = 0
        const val TYPE_ATTRIBUTE = 1
        const val TYPE_TEXTURE = 2
        const val TYPE_NOT_SHADER = 3
    }

    val name = name1
    var location:Int = ShaderConstant.INVALID_PARAMETER_LOCATION
    var value:Any? = null
    val type = type1

    fun initUniformLocation(program:Int) {
        location = GLES20.glGetUniformLocation(program, name)
    }

    fun initAttributeLocation(program:Int) {
        location = GLES20.glGetAttribLocation(program, name)
    }

    fun setUniform1fv(floatArray: FloatArray) {
        GLES20.glUniform1fv(location, floatArray.size, floatArray, 0)
    }

   fun setUniform1f(value: Float) {
        GLES20.glUniform1f(location, value)
    }

   fun setUniformMatrix(shaderParameter: ShaderParameter, matrix: FloatArray) {
        GLES20.glUniformMatrix4fv(shaderParameter.location, matrix.size, false, matrix, 0)
    }

}