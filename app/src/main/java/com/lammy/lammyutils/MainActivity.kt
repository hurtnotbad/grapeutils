package com.lammy.lammyutils

import android.opengl.GLSurfaceView
import android.os.Bundle
import com.lammy.grapeutils.effet.GrapeEffect
import com.lammy.grapeutils.log.LogUtil
import com.lammy.grapeutils.permission.PermissionActivity

class MainActivity : PermissionActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GrapeEffect.initEffect(applicationContext)
//        setContentView(R.layout.activity_main)

        var glSurfaceView = GLSurfaceView(this)
        var render = BitmapRender(glSurfaceView)
        setContentView(glSurfaceView)
        render.render()
    }

    override fun onDonePermissionGranted() {
        LogUtil.e("hello")
    }

}