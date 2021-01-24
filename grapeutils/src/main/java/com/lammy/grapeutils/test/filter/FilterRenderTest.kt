package com.lammy.grapeutils.test.filter

import android.graphics.BitmapFactory
import android.opengl.GLSurfaceView
import com.lammy.grapeutils.effet.base.BasicRenderer
import com.lammy.grapeutils.effet.common.ImageTexture
import com.lammy.grapeutils.effet.common.ShaderConstant
import com.lammy.grapeutils.effet.filter.Filter
import com.lammy.grapeutils.effet.filter.FilterQueue
import com.lammy.grapeutils.effet.filter.GrayFilter
import com.lammy.grapeutils.effet.filter.ShowFilter
import com.lammy.grapeutils.log.LogUtil
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class FilterRenderTest(glSurfaceView1: GLSurfaceView): BasicRenderer(glSurfaceView1) {

    private lateinit var filterQueue: FilterQueue
    lateinit var filter: Filter
    lateinit var showFilter:Filter
    override fun onDrawFrame(gl: GL10?) {
        LogUtil.e("onDrawFrame ....." )
        filterQueue.draw()
        showFilter.setParameter(Filter.inTextureString, filterQueue.getOutTexture())
        showFilter.draw()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        super.onSurfaceChanged(gl, width, height)
        filterQueue = FilterQueue(width, height)
        filterQueue.addFilter(filter)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        LogUtil.e("onSurfaceCreated .....")
        val bitmap = BitmapFactory.decodeStream(glSurfaceView.context.assets.open("test.jpg"))
        filter = GrayFilter()
        showFilter = ShowFilter()
        filter.setParameter(ShaderConstant.INPUT_TEXTURE_UNIFORM_STRING,bitmap )
    }


}