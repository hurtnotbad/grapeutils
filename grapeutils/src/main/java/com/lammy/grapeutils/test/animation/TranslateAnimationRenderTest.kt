package com.lammy.grapeutils.test.animation

import android.graphics.BitmapFactory
import android.opengl.GLSurfaceView
import com.lammy.grapeutils.effet.animation.Animation
import com.lammy.grapeutils.effet.animation.NoAnimation
import com.lammy.grapeutils.effet.base.BasicRenderer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class TranslateAnimationRenderTest(glSurfaceView1: GLSurfaceView): BasicRenderer(glSurfaceView1) {

   val animation:NoAnimation = NoAnimation()

    override fun onDrawFrame(gl: GL10?) {
        animation.drawWithClear()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        super.onSurfaceChanged(gl, width, height)
        animation.setSize(width,height)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        super.onSurfaceCreated(gl, config)
        val bitmap = BitmapFactory.decodeStream(glSurfaceView.context.assets.open("test.jpg"))
        animation.setParameter(Animation.inTexture, bitmap)
    }
}