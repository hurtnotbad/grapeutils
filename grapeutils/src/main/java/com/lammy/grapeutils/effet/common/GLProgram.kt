package com.lammy.grapeutils.effet.common

import android.opengl.GLES20
import com.lammy.effect.glHelper.ShaderHelper
import java.util.*

abstract class GLProgram {
    protected var program = ShaderConstant.INVALID_PROGRAM
    protected var parameters = HashMap<String, ShaderParameter>()

    protected abstract val vertexShader: String?
    protected abstract val fragmentShader: String?

    protected abstract fun addParameters()
    protected abstract fun setDefaultParameterValues()
    protected fun setParametersToShader(){
        for ((_, value) in parameters) {
            value.setValueToShader()
        }
    }
    // must use in glTread
    protected open fun initProgram() {
        if (program == ShaderConstant.INVALID_PROGRAM) {
            program = ShaderHelper.Companion.buildProgram(vertexShader!!, fragmentShader!!)
            for ((_, value) in parameters) {
                value.initLocation(program)
            }
        }
    }

    abstract fun draw()


    fun clearColor() {
        GLES20.glClearColor(0.5f, 1f, 0f, 0f)
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
    }

    init {
        addParameters()
        setDefaultParameterValues()
    }
}