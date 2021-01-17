package com.lammy.grapeutils.effet.glHelper

import android.opengl.GLES20
import com.lammy.grapeutils.log.LogUtil

class FboHelper {
    companion object{
        //创建FrameBuffer 带renderer信息的
        fun createFrameBuffer(width: Int, height: Int, fFrame: IntArray, fRender: IntArray, fTexture: IntArray): Boolean {
            deleteFrameBuffer(fRender, fFrame, fTexture)
            GLES20.glGenFramebuffers(1, fFrame, 0)
            GLES20.glGenRenderbuffers(1, fRender, 0)
            TextureHelper.genTextureWithNoData(fTexture, 0, fTexture.size, width, height)
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fFrame[0])
            GLES20.glBindRenderbuffer(GLES20.GL_RENDERBUFFER, fRender[0])
            GLES20.glRenderbufferStorage(GLES20.GL_RENDERBUFFER, GLES20.GL_DEPTH_COMPONENT16, width, height)
            GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, fTexture[0], 0)
            GLES20.glFramebufferRenderbuffer(GLES20.GL_FRAMEBUFFER, GLES20.GL_DEPTH_ATTACHMENT, GLES20.GL_RENDERBUFFER, fRender[0])
            val status = GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER)
            if (status == GLES20.GL_FRAMEBUFFER_COMPLETE) {
                unBindFrameBuffer()
                return true
            }
            unBindFrameBuffer()
            return false
        }

        fun bindFrameTexture(frameBufferId: Int, textureId: Int, fRenderId: Int) {
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, frameBufferId)
            GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, textureId, 0)
            GLES20.glFramebufferRenderbuffer(GLES20.GL_FRAMEBUFFER, GLES20.GL_DEPTH_ATTACHMENT, GLES20.GL_RENDERBUFFER, fRenderId)
        }
        fun unBindFrameBuffer() {
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0)
            GLES20.glBindRenderbuffer(GLES20.GL_RENDERBUFFER, 0)
        }

        fun deleteFrameBuffer(fRender: IntArray, fFrame: IntArray, fTexture: IntArray) {
            GLES20.glDeleteRenderbuffers(fRender.size, fRender, 0)
            GLES20.glDeleteFramebuffers(fFrame.size, fFrame, 0)
            GLES20.glDeleteTextures(fTexture.size, fTexture, 0)
        }


        /************************** 不带 render buffer的**********************************/


        fun createFrameBuffer(width: Int, height: Int, fFrame: IntArray, fTexture: IntArray): Boolean {
            GLES20.glGenFramebuffers(1, fFrame, 0)
            TextureHelper.genTextureWithNoData(fTexture, 0, fTexture.size, width, height)
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fFrame[0])
            GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, fTexture[0], 0)
            val status = GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER)
            if (status == GLES20.GL_FRAMEBUFFER_COMPLETE) {
                unBindFrameBuffer()
                return true
            }
            unBindFrameBuffer()
            return false
        }


        fun createFrameBuffer(frame:IntArray) {
            GLES20.glGenFramebuffers(frame.size, frame, 0)
        }

        fun deleteFrameBuffer(frame: IntArray){
            GLES20.glDeleteFramebuffers(frame.size, frame, 0)
        }


        fun bindFbo(texture:Int, frame: Int):Boolean{
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, frame)
            GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, texture, 0)
            val status = GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER)
            if (status == GLES20.GL_FRAMEBUFFER_COMPLETE) {
                LogUtil.d("bind Fbo success")
                return true
            }
            LogUtil.e("bind Fbo failed")
            unBindFbo()
            return false
        }

        fun bindFbo(texture:IntArray, width:Int, height:Int):Boolean{
            val frame = IntArray(1)
            GLES20.glGenFramebuffers(1, frame, 0)
            if(texture[0] == 0) {
                TextureHelper.genTextureWithNoData(texture, 0, texture.size, width, height)
                LogUtil.e("build fbo texture = ${texture[0]}")
            }
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, frame[0])
            GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, texture[0], 0)
            val status = GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER)
            if (status == GLES20.GL_FRAMEBUFFER_COMPLETE) {
                return true
            }
            LogUtil.e("bind fbo failed")
            unBindFrameBuffer()
            return false
        }

        fun unBindFbo() {
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0)
        }

    }


}