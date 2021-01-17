package com.lammy.grapeutils.effet.filter

import android.opengl.GLES20
import com.lammy.grapeutils.effet.base.Effect
import com.lammy.grapeutils.effet.common.ShaderConstant
import com.lammy.grapeutils.effet.common.ShaderParameter

/***
 * 构建基本的filter类，后续继承，只需要设置参数即可，可参考 showFilter 和 LutTableFilter
 */
abstract class Filter : Effect() {

    companion object{
         var inTextureString = ShaderConstant.INPUT_TEXTURE_UNIFORM_STRING
         var positionsString = ShaderConstant.POINTS_ATTRS_STRING
         var textureCoorString = ShaderConstant.INPUT_TEXTURE_CORD_ATTRS_STRING
         val matrixString = ShaderConstant.INPUT_MATRIX_UNIFORM_STRING
    }


    /*在init方法中调用*/
    override fun initParameters() {
        addParameter(positionsString, ShaderConstant.POSITIONS!!, ShaderParameter.TYPE_ATTRIBUTE)
        addParameter(textureCoorString, ShaderConstant.COORDINATE!!, ShaderParameter.TYPE_ATTRIBUTE)
        addParameter(inTextureString, ShaderConstant.INVALID_TEXTURE, ShaderParameter.TYPE_TEXTURE)
    }

    override fun setParameters() {
        setAttribute(positionsString, 2)
        setAttribute(textureCoorString, 2)
        setUniformTexture(inTextureString, 0)
    }

    override fun getTarget(): Int {
        return GLES20.GL_TEXTURE_2D
    }

    override fun draw() {
        if (program == ShaderConstant.INVALID_PROGRAM) {
            onCreate()
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