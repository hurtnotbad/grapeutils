package com.lammy.grapeutils.test

import android.graphics.BitmapFactory
import android.opengl.GLSurfaceView
import com.lammy.grapeutils.effet.animation.*
import com.lammy.grapeutils.effet.base.BasicRenderer
import com.lammy.grapeutils.effet.filter.*
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class TranslateAnimationRenderTest(glSurfaceView1: GLSurfaceView): BasicRenderer(glSurfaceView1) {

//   private val animation:NoAnimation = NoAnimation()

    private var filter = Camera2Filter()
    private var showFilter = GrayFilter()
    private var animation = BookAnimation()
    lateinit var queuen:LYFilterQueue

    override fun onDrawFrame(gl: GL10?) {

//        queuen.draw()
//        showFilter.setInTexture(queuen.getOutTexture())
//        showFilter.draw()

        animation.draw()
    }

}