package com.lammy.grapeutils.effet.common

import android.graphics.RectF
import android.opengl.GLES20

abstract class Texture {

    open var width = 0
    var height = 0
    var textureId = ShaderConstant.INVALID_TEXTURE
    protected var drawRect:FloatArray = floatArrayOf()
    protected var target:Int = GLES20.GL_TEXTURE_2D

}