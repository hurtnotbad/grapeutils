package com.lammy.grapeutils.effet.common

import android.opengl.GLES11Ext

class OesTexture : Texture() {
    override var textureIndex = 0
    override var target: Int = GLES11Ext.GL_TEXTURE_EXTERNAL_OES

    override fun freeSource() {

    }

    override fun prepareToDraw() {

    }
}