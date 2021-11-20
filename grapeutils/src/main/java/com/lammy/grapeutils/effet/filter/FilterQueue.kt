package com.lammy.grapeutils.effet.filter

import android.graphics.Bitmap
import android.opengl.GLES20
import com.lammy.grapeutils.effet.common.ImageTexture
import com.lammy.grapeutils.effet.common.Texture
import com.lammy.grapeutils.effet.glHelper.FboHelper
import com.lammy.grapeutils.effet.glHelper.TextureHelper
import com.lammy.grapeutils.log.LogUtil
import java.util.*

class FilterQueue(var width: Int, var height: Int) {
    private val mFilters = ArrayList<Filter>()
    private val textureSize = 2
    private val frame = IntArray(1)
    private val fboTexturesArray = IntArray(textureSize)
    private lateinit var fboTextures :Array<Texture>

    private lateinit var inTexture: Texture

    init {
        initFboTexture()
    }

    private fun initFboTexture(){
        FboHelper.createFrameBuffer(width, height, frame, fboTexturesArray)
        fboTextures = arrayOf( ImageTexture(fboTexturesArray[0],width,height),  ImageTexture(fboTexturesArray[1],width,height))
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
                FboHelper.bindFbo(fboTextures[textureIndex % 2].textureId, frame[0])
                if (textureIndex == 0) {
                    if( filter.getValue(Filter.inTextureString) is Bitmap){
                        val texture = IntArray(1)
                        TextureHelper.createTexture(filter.getValue(Filter.inTextureString) as Bitmap,texture)
                        filter.setParameter(Filter.inTextureString , ImageTexture(texture[0],width,height))
                    }
                    inTexture = (filter.getValue(Filter.inTextureString) as ImageTexture)
                } else {
                    filter.setParameter(Filter.inTextureString, fboTextures[(textureIndex - 1) % 2])
                }
                filter.draw()
                FboHelper.unBindFbo()
                textureIndex++
            }
        }
    }

    fun getOutTexture(): Texture {
        return if (mFilters.size == 0) {
            inTexture
        } else {
//            LogUtil.e("out Texture = " + fboTextures[(textureIndex - 1) % 2])
            fboTextures[(textureIndex - 1) % 2]
        }
    }

    fun onSizeChange(w: Int, h: Int) {
        width = w
        height = h
        FboHelper.deleteFrameBuffer(frame)
        TextureHelper.deleteTexture(fboTexturesArray)
        initFboTexture()
    }



    fun release() {
        GLES20.glDeleteTextures(textureSize,fboTexturesArray , 0)
        GLES20.glDeleteFramebuffers(1, frame, 0)
    }

}