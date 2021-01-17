package com.lammy.grapeutils.effet

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.GLSurfaceView.RENDERMODE_CONTINUOUSLY
import android.opengl.GLSurfaceView.RENDERMODE_WHEN_DIRTY
import com.lammy.grapeutils.log.LogUtil
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

open class BasicRenderer(glSurfaceView1: GLSurfaceView) : GLSurfaceView.Renderer {
    protected var glSurfaceView = glSurfaceView1
    var width:Int = 0
    var height:Int = 0

    init {
        glSurfaceView.setEGLContextClientVersion(2)
        glSurfaceView.setRenderer(this)
        glSurfaceView.renderMode = RENDERMODE_WHEN_DIRTY
//        glSurfaceView.renderMode = RENDERMODE_CONTINUOUSLY
    }

    override fun onDrawFrame(gl: GL10?) {
        TODO("Not yet implemented")
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        LogUtil.e("width = $width height = $height")
        this.width = width
        this.height = height
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        TODO("Not yet implemented")
    }

    fun render() {
        glSurfaceView.requestRender()
    }
}