package com.lammy.grapeutils.test.filter

import android.graphics.BitmapFactory
import android.graphics.BitmapFactory.Options
import android.opengl.GLSurfaceView
import com.lammy.grapeutils.effet.base.BasicRenderer
import com.lammy.grapeutils.effet.common.ImageTexture
import com.lammy.grapeutils.effet.common.ShaderConstant
import com.lammy.grapeutils.effet.filter.*
import com.lammy.grapeutils.log.LogUtil
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class FilterRenderTest(glSurfaceView1: GLSurfaceView): BasicRenderer(glSurfaceView1) {

    private lateinit var filterQueue: FilterQueue
    lateinit var filter: Filter
    lateinit var showFilter:Filter
    override fun onDrawFrame(gl: GL10?) {
//        filterQueue.draw()
//        showFilter.setParameter(Filter.inTextureString, filterQueue.getOutTexture())
//        showFilter.draw()
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
//        LogUtil.e("onSurfaceCreated .....")
//        val bitmap = BitmapFactory.decodeStream(glSurfaceView.context.assets.open("test.jpg"))
//        var option = Options()
//        option.inJustDecodeBounds = false
////        option.inSampleSize = 32;
//        option.inSampleSize = 32;
//        val trans = BitmapFactory.decodeStream(glSurfaceView.context.assets.open("trans.jpg"),null,option)!!
//
//        LogUtil.e("trans = null is ${trans == null} trans width = ${trans?.width}" )
//        filter = TransColorFilter()
////        filter = GrayFilter()
//        showFilter = ShowFilter()
//        filter.setParameter(ShaderConstant.INPUT_TEXTURE_UNIFORM_STRING,bitmap )
//
//
////
//        var inTexture = IntArray(1)
////        TextureHelper.createTexture(trans!!, inTexture)
//        var texture = ImageTexture(trans)
//        filter.setParameter(TransColorFilter.transfer,texture)


//        filter.setParameter(TransColorFilter.transfer,trans)
    }


}