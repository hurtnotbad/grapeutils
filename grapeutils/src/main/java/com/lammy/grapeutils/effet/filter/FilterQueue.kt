package com.lammy.grapeutils.effet.filter

import android.graphics.Bitmap
import android.opengl.GLES20
import com.lammy.grapeutils.effet.glHelper.FboHelper
import com.lammy.grapeutils.effet.glHelper.TextureHelper
import com.lammy.grapeutils.log.LogUtil
import java.util.*

class FilterQueue(var width: Int, var height: Int) {
    private val mFilters = ArrayList<Filter>()
    private val textureSize = 2
    private val frame = IntArray(1)
    private val fboTextures = IntArray(textureSize)
    var inTexture: Int = 0

    init {
        FboHelper.createFrameBuffer(width, height, frame, fboTextures)
    }

    fun addFilter(lyFilter: Filter) {
        mFilters.add(lyFilter)
    }

    fun removeFilter(filter: Filter) {
        mFilters.remove(filter)
    }

    fun removeAllFilter() {
        mFilters.clear()
    }

    private var textureIndex = 0
    fun draw() {
        textureIndex = 0
        synchronized(mFilters) {
            for (filter in mFilters) {
                FboHelper.bindFbo(fboTextures[textureIndex % 2], frame[0])
                if (textureIndex == 0) {
                    if( filter.getValue(Filter.inTextureString) is Bitmap){
                        val texture = IntArray(1)
                        TextureHelper.createTexture(filter.getValue(Filter.inTextureString) as Bitmap,texture)
                        filter.setParameter(Filter.inTextureString , texture[0])
                    }
                    inTexture = filter.getValue(Filter.inTextureString) as Int
                } else {
//                    filter.inTexture = fboTextures[(textureIndex - 1) % 2]
                    filter.setParameter(Filter.inTextureString, fboTextures[(textureIndex - 1) % 2])
                }
                filter.draw()
                FboHelper.unBindFbo()
                textureIndex++
            }
        }
    }

    fun getOutTexture(): Int {
        return if (mFilters.size == 0) {
            inTexture
        } else {
            LogUtil.e("out Texture = " + fboTextures[(textureIndex - 1) % 2])
            fboTextures[(textureIndex - 1) % 2]
        }
    }

    fun onSizeChange(w: Int, h: Int) {
        width = w
        height = h
        FboHelper.deleteFrameBuffer(frame)
        TextureHelper.deleteTexture(fboTextures)
        FboHelper.createFrameBuffer(width, height, frame, fboTextures)
    }

    fun release() {
        GLES20.glDeleteTextures(textureSize, fboTextures, 0)
        GLES20.glDeleteFramebuffers(1, frame, 0)
    }

}