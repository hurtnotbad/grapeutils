package com.lammy.lammyutils

import android.opengl.GLSurfaceView
import android.os.Bundle
import com.lammy.grapeutils.effet.base.GrapeEffect
import com.lammy.grapeutils.log.LogUtil
import com.lammy.grapeutils.permission.PermissionActivity
import com.lammy.grapeutils.test.animation.TranslateAnimationRenderTest
import com.lammy.grapeutils.test.filter.FilterRenderTest

class MainActivity : PermissionActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GrapeEffect.initEffect(applicationContext)

        setContentView(R.layout.activity_main)
        val glSurfaceView = findViewById<GLSurfaceView>(R.id.gl_view)
//        val render = FilterRenderTest(glSurfaceView)
        val render = TranslateAnimationRenderTest(glSurfaceView)
        render.render()


    }

    override fun onDonePermissionGranted() {
        LogUtil.e("hello")
    }

}