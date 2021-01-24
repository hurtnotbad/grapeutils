package com.lammy.grapeutils.effet.animation

import android.opengl.GLES20
import com.lammy.effect.glutils.Matrix2DUtils
import com.lammy.grapeutils.effet.base.Effect
import com.lammy.grapeutils.effet.common.ShaderConstant
import com.lammy.grapeutils.effet.common.ShaderParameter

/***
 * 构建基本的Animation类，后续继承，只需要设置参数即可，可参考 showFilter 和 LutTableFilter
 */
abstract class Animation : Effect() {

    companion object {
        const val inTexture = ShaderConstant.INPUT_TEXTURE_UNIFORM_STRING
        const val duration = "duration"
        const val defaultDuration = 3000
    }
    protected  var positionsString = ShaderConstant.POINTS_ATTRS_STRING
    protected var textureCoorString = ShaderConstant.INPUT_TEXTURE_CORD_ATTRS_STRING
    protected val matrixString = ShaderConstant.INPUT_MATRIX_UNIFORM_STRING
    protected var startTime = 0L


    /*在init方法中调用*/
    override fun initParameters() {
        addParameter(positionsString, ShaderConstant.POSITIONS!!, ShaderParameter.TYPE_ATTRIBUTE)
        addParameter(textureCoorString, ShaderConstant.COORDINATE!!, ShaderParameter.TYPE_ATTRIBUTE)
        addParameter(inTexture, ShaderConstant.INVALID_TEXTURE, ShaderParameter.TYPE_TEXTURE)
        addParameter(matrixString, Matrix2DUtils.getIdentityMatrix(), ShaderParameter.TYPE_TEXTURE)
        addParameter(duration, defaultDuration, ShaderParameter.TYPE_NOT_SHADER)
    }

    override fun setParameters() {
        updateParameters()
        setAttribute(positionsString, 2)
        setAttribute(textureCoorString, 2)
        setUniformTexture(inTexture, 0)
        setUniformMatrix(matrixString)
    }

    protected fun getProgress(): Float {
        val curTime = System.currentTimeMillis()
        val progress = (curTime - startTime).toFloat() / (getValue(duration) as Int)
        return if (progress > 1f) {
            1f
        } else {
            progress
        }
    }

    protected open fun updateParameters() {}

    override fun getTarget(): Int {
        return GLES20.GL_TEXTURE_2D
    }

    override fun onCreate() {
        super.onCreate()
        startTime = System.currentTimeMillis()
    }

    fun reSet() {
        startTime = System.currentTimeMillis()
    }

    override fun draw() {
        if (program == ShaderConstant.INVALID_PROGRAM) {
            onCreate()
            printParameter(javaClass.simpleName)
        }
        GLES20.glUseProgram(program)
        setParameters()
        GLES20.glEnableVertexAttribArray(getLocation(positionsString))
        GLES20.glEnableVertexAttribArray(getLocation(textureCoorString))
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)
        GLES20.glDisableVertexAttribArray(getLocation(positionsString))
        GLES20.glDisableVertexAttribArray(getLocation(textureCoorString))
    }
}