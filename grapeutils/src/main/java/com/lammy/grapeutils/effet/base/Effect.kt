package com.lammy.grapeutils.effet.base

import android.graphics.Bitmap
import android.opengl.GLES20
import com.lammy.effect.glHelper.ShaderHelper
import com.lammy.grapeutils.effet.glHelper.TextureHelper
import com.lammy.grapeutils.effet.common.ShaderConstant
import com.lammy.grapeutils.effet.common.ShaderParameter
import com.lammy.grapeutils.log.LogUtil
import java.nio.FloatBuffer
import android.opengl.GLES20.glVertexAttribPointer as glVertexAttribPointer1

abstract class Effect {

    protected var width: Int = 0
    protected var height: Int = 0
    protected var program: Int = ShaderConstant.INVALID_PROGRAM
    protected var parameters = HashMap<String, ShaderParameter>()

//    // shader 以模板load 的时候，需要四个分开
//    protected var uniformParameters = HashMap<String, ShaderParameter>()
//    protected var attributeParameters = HashMap<String, ShaderParameter>()
//    protected var effectParameters = HashMap<String, ShaderParameter>()
//    protected var textureParameters = HashMap<String, ShaderParameter>()

    protected fun onCreate() {
        program = ShaderHelper.buildProgram(getVertexShader(), getFragmentShader())
        initParametersLocationAndTexture()
    }

    protected abstract fun getVertexShader(): String

    protected abstract fun getFragmentShader(): String

    protected abstract fun initParameters()

    /**
     * 初始化 parameter的location，并加载纹理
     */
    private fun initParametersLocationAndTexture() {
        for ((key, value) in parameters) {
            when (value.type) {
                ShaderParameter.TYPE_UNIFORM  -> value.initUniformLocation(this.program)
                ShaderParameter.TYPE_TEXTURE  -> {
                    value.initUniformLocation(this.program)
                    if(value.value is Bitmap) {
                        setTextureParameter(key, value.value as Bitmap)
                    }
                }
                ShaderParameter.TYPE_ATTRIBUTE -> value.initAttributeLocation(this.program)
            }
        }
    }

    protected fun addParameter(name: String, value:Any?, type:Int) {
        parameters[name] = ShaderParameter(name, type)
        parameters[name]!!.value = value
    }

    /**
     * 绘制的时候，设置参数，此方法会去获取parameters内的value，然后给shader
     */
    protected abstract fun setParameters()

    /**
     * 设置effect 的参数，不需要program 创建
     */
    fun setParameter(name: String, value: Any) {
        parameters[name]?.value = value
    }
    private fun setTextureParameter(name: String, value: Bitmap) {
        val texture = IntArray(1)
        TextureHelper.createTexture(value,texture)
        LogUtil.e("texture = $texture[0")
        parameters[name]?.value = texture[0]
    }

    abstract fun draw()

    protected abstract fun getTarget(): Int;

    protected fun setSize(width: Int, height: Int) {
        this.width = width
        this.height = height
    }

    protected fun onClear() {
        GLES20.glClearColor(0f, 0f, 0f, 0f)
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
    }


    fun getLocation(name: String): Int {
        return parameters[name]?.location?.toInt()
            ?: ShaderConstant.INVALID_PARAMETER_LOCATION
    }

    fun getValue(name: String): Any? {
//        LogUtil.e("lammy getValue name = $name value =  "+ parameters[name]?.value)
        return parameters[name]?.value
    }

    fun getType(name: String) :Int?{
        return parameters[name]?.type
    }

    /******************************** setParameters()方法内使用 start******************************************/
    /***
     * size 是浮点数的位数，如（x,y）则size=2（x,y,z） size = 3
     */
    fun setAttribute(name: String, size: Int) {
        glVertexAttribPointer1(
            getLocation(name),
            size,
            GLES20.GL_FLOAT,
            false,
            size * ShaderConstant.FLOAT_SIZE,
            getValue(name) as FloatBuffer
        )
    }

     protected fun setUniform1fv(name: String) {
        var floatArray: FloatArray = getValue(name) as FloatArray
        GLES20.glUniform1fv(getLocation(name), floatArray.size, floatArray, 0)
    }

    protected fun setUniform1f(name: String) {
        GLES20.glUniform1f(getLocation(name), getValue(name) as Float)
    }

    protected fun setUniformMatrix(name: String) {
        val floatArray:FloatArray = getValue(name) as FloatArray
        GLES20.glUniformMatrix4fv(getLocation(name), floatArray.size, false, floatArray, 0)
     }

    protected fun setUniformTexture(name: String,  textureTarget:Int) {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + textureTarget)
        GLES20.glBindTexture(getTarget(), getValue(name) as Int)
        GLES20.glUniform1i(getLocation(name), textureTarget)
    }
    /******************************** setParameters()方法内使用 end******************************************/

}