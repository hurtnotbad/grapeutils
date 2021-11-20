package com.lammy.grapeutils.effet.common

import android.graphics.Bitmap
import com.lammy.grapeutils.effet.glHelper.TextureHelper

class ImageTexture() : Texture() {

    var bitmap: Bitmap? = null
        set(value) {
            field = value
            field?.let {
                width = it.width
                height = it.height
                isReady = false
            }
        }

    constructor(bitmap1: Bitmap) : this() {
        bitmap = bitmap1
        this.width = bitmap1.width
        this.height = bitmap1.height
        isReady = false
    }
    constructor(bitmap1: Bitmap, index:Int) : this() {
        bitmap = bitmap1
        this.width = bitmap1.width
        this.height = bitmap1.height
        textureIndex = index
        isReady = false
    }

    constructor(texture: Int, width: Int, height: Int) : this() {
        textureId = texture
        this.width = width
        this.height = height
        isReady = true
    }

    var isReady: Boolean = false


    // must use in glThread
    override fun prepareToDraw() {
        if (!isReady) {
            bitmap?.let {
                width = it.width
                height = it.height
                val texture = IntArray(1)
                TextureHelper.createTexture(it, texture)
                textureId = texture[0]
                isReady = true
            }
            freeSource()
        }
    }

    override fun freeSource() {
        bitmap?.let {
            if (!it.isRecycled) {
                it.recycle()
            }
        }
    }


}