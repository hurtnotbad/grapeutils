package com.lammy.grapeutils.effet.common

import android.opengl.GLES20
import com.lammy.effect.glutils.BufferUtil.Companion.floatToBuffer
import com.lammy.effect.glutils.BufferUtil.Companion.intToBuffer
import com.lammy.grapeutils.effet.glutils.GLUtil.Companion.checkError
import java.nio.FloatBuffer

class ShaderParameter(var name: String, var type: Int, var valueType: Int) {
    companion object{
        val INVALID_LOCATION = -1
        //type
        val TYPE_UNIFORM = 0
        val TYPE_ATTRIBUTE = 1
        val TYPE_TEXTURE = 2
        val TYPE_NOT_SHADER = 3

        // valueType
        val VALUE_TYPE_INT = 0
        val VALUE_TYPE_INT_ARRAY = 1
        val VALUE_TYPE_FLOAT = 2
        val VALUE_TYPE_FLOAT_ARRAY = 3
        val VALUE_TYPE_FLOAT_MATRIX = 4
        val VALUE_TYPE_IMAGE_TEXTURE = 5
        val VALUE_TYPE_ATTRIBUTE_PARAMETER = 6
    }

    var location = INVALID_LOCATION
    var program = 0
    var value: Any? = null
    private var valueSet: ParameterValueSet? = null

    init {
        initParameterSet(type, valueType)
    }

    fun initLocation(program: Int) {
        if (location == INVALID_LOCATION) {
            this.program = program
            if (type == ShaderParameter2.TYPE_UNIFORM || type == ShaderParameter2.TYPE_TEXTURE) {
                location = GLES20.glGetUniformLocation(program, name)
            } else if (type == ShaderParameter2.TYPE_ATTRIBUTE) {
                location = GLES20.glGetAttribLocation(program, name)
            }
        }
    }

    fun setValueToShader() {
        valueSet?.setValueToShader(value)
    }

    private fun initParameterSet(type: Int, valueType: Int) {
        if (type == ShaderParameter2.TYPE_ATTRIBUTE) {
            if (valueType == VALUE_TYPE_ATTRIBUTE_PARAMETER) {
                valueSet = object : ParameterValueSet {
                    override fun setValueToShader(value: Any?) {
                        val attributeParameter = value as AttributeParameter
                        GLES20.glVertexAttribPointer(
                            location,
                            attributeParameter.size,
                            GLES20.GL_FLOAT,
                            false,
                            attributeParameter.stride,
                            attributeParameter.attributesBuffer
                        )
                    }
                }
            }
            return
        }
        if (type == ShaderParameter2.TYPE_UNIFORM) {
            if (valueType == VALUE_TYPE_FLOAT_ARRAY) {
                valueSet = object : ParameterValueSet {
                    override fun setValueToShader(value: Any?) {
                        val valueArray = value as FloatArray
                        GLES20.glUniform1fv(location, valueArray.size, valueArray, 0)
                    }
                }
            } else if (valueType == VALUE_TYPE_FLOAT) {
                valueSet = object : ParameterValueSet {
                    override fun setValueToShader(value: Any?) {
                        val floatValue = value as Float
                        GLES20.glUniform1f(location, floatValue)
                    }
                }
            } else if (valueType == VALUE_TYPE_FLOAT_MATRIX) {
                valueSet = object : ParameterValueSet {
                    override fun setValueToShader(value: Any?) {
                        val floatMatrix = value as FloatArray?
                        GLES20.glUniformMatrix4fv(location, 1, false, floatMatrix, 0)
                    }
                }
            } else if (valueType == VALUE_TYPE_INT) {
                valueSet = object : ParameterValueSet {
                    override fun setValueToShader(value: Any?) {
                        val intValue = value as Int
                        GLES20.glUniform1i(location, intValue)
                    }
                }
            } else if (valueType == VALUE_TYPE_INT_ARRAY) {
                valueSet = object : ParameterValueSet {
                    override fun setValueToShader(value: Any?) {
                        val intArray = value as IntArray
                        GLES20.glUniform1iv(
                            location,
                            intArray.size,
                            intToBuffer(intArray)
                        )
                    }
                }
            }
            return
        }
        if (type == ShaderParameter2.TYPE_TEXTURE) {
            if (valueType == VALUE_TYPE_IMAGE_TEXTURE) {
                valueSet = object : ParameterValueSet {
                    override fun setValueToShader(value: Any?) {
                        val texture = value as Texture
                        texture.prepareToDraw()
                        checkError("lammy ImageTexture 1")
                        GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + texture.textureIndex)
                        GLES20.glBindTexture(texture.target, texture.textureId)
                        GLES20.glUniform1i(location, texture.textureIndex)
                    }
                }
            }
        }
    }

    private interface ParameterValueSet {
        fun setValueToShader(value: Any?)
    }

    class AttributeParameter {
        // size 是浮点数的位数，如（x,y）则size=2（x,y,z） size = 3
        var size: Int
        var stride: Int
        var attributesBuffer: FloatBuffer?

        constructor(points: FloatArray?, size: Int) {
            attributesBuffer = floatToBuffer(points!!)
            this.size = size
            stride = size * ShaderConstant.FLOAT_SIZE
        }

        constructor(buffer: FloatBuffer?, size: Int) {
            attributesBuffer = buffer
            this.size = size
            stride = size * ShaderConstant.FLOAT_SIZE
        }
    }
}