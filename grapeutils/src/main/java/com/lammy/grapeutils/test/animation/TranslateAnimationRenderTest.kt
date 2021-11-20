package com.lammy.grapeutils.test.animation

import android.graphics.BitmapFactory
import android.opengl.GLSurfaceView
import com.lammy.grapeutils.effet.animation.Animation
import com.lammy.grapeutils.effet.animation.BookAnimation
import com.lammy.grapeutils.effet.animation.NoAnimation
import com.lammy.grapeutils.effet.base.BasicRenderer
import com.lammy.grapeutils.effet.filter.*
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class TranslateAnimationRenderTest(glSurfaceView1: GLSurfaceView): BasicRenderer(glSurfaceView1) {

//   private val animation:NoAnimation = NoAnimation()
   private val animation: BookAnimation = BookAnimation()

    private var filter = Camera2Filter()
    private var showFilter = GrayFilter()
    lateinit var queuen:LYFilterQueue
    override fun onDrawFrame(gl: GL10?) {
//        animation.drawWithClear()
//        filter.draw()
        queuen.draw()
        showFilter.setInTexture(queuen.getOutTexture())
        showFilter.draw()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        super.onSurfaceChanged(gl, width, height)
//        animation.setSize(width,height)

        queuen = LYFilterQueue(width, height)
        queuen.addFilter(filter)
    }

}