package com.lammy.grapeutils.effet.filter

import android.graphics.SurfaceTexture
import android.opengl.GLES11Ext
import com.lammy.effect.glutils.BufferUtil
import com.lammy.grapeutils.effet.common.ShaderConstant
import com.lammy.grapeutils.effet.common.ShaderParameter2
import com.lammy.grapeutils.effet.glHelper.TextureHelper
import com.lammy.grapeutils.log.LogUtil
import java.nio.FloatBuffer

class CameraFilter : Filter() {

    companion object {
        private val coordCameraFront = BufferUtil.floatToBuffer(
            floatArrayOf(
                1f, 1f,
                0f, 1f,
                1f, 0f,
                0f, 0f
            )
        )
        private val coordCameraBack = BufferUtil.floatToBuffer(
            floatArrayOf(
                0f, 1f,
                1f, 1f,
                0f, 0f,
                1f, 0f
            )
        )
    }


    init {
        initParameters()
    }

    private lateinit var vTextureCoordinateBuffer: FloatBuffer

    override fun getVertexShader(): String {
        return ShaderConstant.NO_FILTER_VERTEX_SHADER
    }

    override fun getFragmentShader(): String {
        return """#extension GL_OES_EGL_image_external : require
                precision mediump float;
                varying vec2 textureCoordinate;
                uniform samplerExternalOES vTexture;
        
                void main()
                {
                    gl_FragColor = texture2D( vTexture, textureCoordinate );
                }"""
    }

    override fun initParameters() {
        addParameter(positionsString, ShaderConstant.POSITIONS!!, ShaderParameter2.TYPE_ATTRIBUTE)
        addParameter(textureCoorString, null, ShaderParameter2.TYPE_ATTRIBUTE)
        addParameter(inTextureString, ShaderConstant.INVALID_PARAMETER_LOCATION, ShaderParameter2.TYPE_TEXTURE)

    }

    override fun setParameters() {
        setAttribute(positionsString, 2)
        setAttribute(textureCoorString, 2)
        setUniformTexture(inTextureString, 0)
    }

    override fun getTarget(): Int {
        return GLES11Ext.GL_TEXTURE_EXTERNAL_OES
    }

    var cameraID = "0"
    private var isChangeCamera = false

    fun setCameraId(cameraId: String) {
        isChangeCamera = true
        cameraID = cameraId
        when (cameraId) {
            "0" -> {
                vTextureCoordinateBuffer = coordCameraBack!!
            }
            "1" -> {
                vTextureCoordinateBuffer = coordCameraFront!!
            }
            else -> {
                vTextureCoordinateBuffer = coordCameraBack!!
                LogUtil.e("please set the cameraId first !")
            }
        }
        setParameter(textureCoorString,vTextureCoordinateBuffer)
    }

    override fun draw() {
        surfaceTexture.updateTexImage()
        super.draw()
    }



    private var oesTexture = IntArray(1)
     val surfaceTexture: SurfaceTexture by lazy {
         TextureHelper.createOESTexture(oesTexture)
         var surfaceTexture1 = SurfaceTexture(oesTexture[0])
         parameters[inTextureString]?.value = oesTexture[0]
         surfaceTexture1
     }


}