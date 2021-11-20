package com.lammy.grapeutils.effet.common

import android.graphics.RectF
import android.opengl.GLES20

abstract class Texture {

    var width = 0
    var height = 0
    var textureId = ShaderConstant.INVALID_TEXTURE
    open  var textureIndex = 0
    protected var drawRect:FloatArray = floatArrayOf()
    open var target:Int = GLES20.GL_TEXTURE_2D

    abstract fun freeSource()

    abstract fun prepareToDraw()

}