package com.lammy.grapeutils.effet.common

import android.graphics.Bitmap
import com.lammy.grapeutils.effet.glHelper.TextureHelper

class ImageTexture(bitmap1: Bitmap) : Texture() {

    var isReady: Boolean = false
    var bitmap: Bitmap = bitmap1
        set(value) {
            field = value
            width = field.width
            height = field.height
            isReady = false
        }

    fun prepareToDraw() {
        if(!isReady) {
            val texture = IntArray(1)
            TextureHelper.createTexture(bitmap, texture)
            textureId = texture[0]
            isReady = true
        }
    }

}