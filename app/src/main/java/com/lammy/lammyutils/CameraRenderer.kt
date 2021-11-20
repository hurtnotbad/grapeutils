package com.lammy.lammyutils

import android.opengl.GLSurfaceView
import com.lammy.grapeutils.effet.base.BasicRenderer
import com.lammy.grapeutils.camera.CameraInterface
import com.lammy.grapeutils.effet.base.GrapeEffect
import com.lammy.grapeutils.effet.filter.*
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class CameraRenderer (glSurfaceView1: GLSurfaceView): BasicRenderer(glSurfaceView1) {

    private var cameraInterface: CameraInterface =
        CameraInterface(GrapeEffect.grapeContext)
    private lateinit var cameraFilter: CameraFilter
    lateinit var showFilter:Filter
    lateinit var grayFilter:Filter
    private lateinit var filterQueue: FilterQueue

    override fun onDrawFrame(gl: GL10?) {
        filterQueue.draw()
        showFilter.draw()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        super.onSurfaceChanged(gl, width, height)
        filterQueue  = FilterQueue(width, height)
        filterQueue.addFilter(cameraFilter)
        filterQueue.addFilter(grayFilter)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        cameraFilter =  CameraFilter()
        cameraInterface.setSurfaceTexture(cameraFilter.surfaceTexture)
        cameraFilter.setCameraId("0")
        cameraInterface.openCamera()
    }

}