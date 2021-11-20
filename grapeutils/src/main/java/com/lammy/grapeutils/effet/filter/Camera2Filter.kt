package com.lammy.grapeutils.effet.filter

import android.graphics.SurfaceTexture
import com.lammy.effect.glutils.BufferUtil
import com.lammy.grapeutils.camera.CameraInterface

import com.lammy.grapeutils.effet.common.OesTexture
import com.lammy.grapeutils.effet.common.ShaderConstant
import com.lammy.grapeutils.effet.common.ShaderParameter
import com.lammy.grapeutils.effet.glHelper.TextureHelper
import com.lammy.grapeutils.effet.glutils.ContextUtil
import com.lammy.grapeutils.log.LogUtil


class Camera2Filter: LYFilter() {
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
    override var vertexShader = ShaderConstant.SHOW_FILTER_VERTEX_SHADER
    override var fragmentShader = """#extension GL_OES_EGL_image_external : require
                precision mediump float;
                varying vec2 textureCoordinate;
                uniform samplerExternalOES inputTexture;
        
                void main()
                {
                    gl_FragColor = texture2D( inputTexture, textureCoordinate );
                }"""

    private lateinit var oesTexture:OesTexture
    private var surfaceTexture:SurfaceTexture? = null
    var camera:CameraInterface = CameraInterface(ContextUtil.globalContext)

    override fun addParameters() {
        parameters["vPosition"] = ShaderParameter(
            "vPosition",
            ShaderParameter.TYPE_ATTRIBUTE,
            ShaderParameter.VALUE_TYPE_ATTRIBUTE_PARAMETER
        )
        parameters["inputTextureCoordinate"] = ShaderParameter(
            "inputTextureCoordinate",
            ShaderParameter.TYPE_ATTRIBUTE,
            ShaderParameter.VALUE_TYPE_ATTRIBUTE_PARAMETER
        )
        parameters["inputTexture"] = ShaderParameter(
            "inputTexture",
            ShaderParameter.TYPE_TEXTURE,
            ShaderParameter.VALUE_TYPE_IMAGE_TEXTURE
        )
    }
    override fun setDefaultParameterValues() {
        oesTexture = OesTexture()
        parameters["vPosition"]?.value = ShaderParameter.AttributeParameter(ShaderConstant.POSITIONS, 2)
        parameters["inputTextureCoordinate"]?.value = ShaderParameter.AttributeParameter(ShaderConstant.COORDINATE, 2)
        parameters["inputTexture"]?.value = oesTexture
    }

    // this should use in front open camera
    override fun initProgram() {
        super.initProgram()
        surfaceTexture?:let {
            val oesTextureArray = IntArray(1)
            TextureHelper.createOESTexture(oesTextureArray)
            surfaceTexture = SurfaceTexture(oesTextureArray[0])
            oesTexture.textureId = oesTextureArray[0]
            camera.setSurfaceTexture(surfaceTexture)
            setCameraId("0")
            camera.openCamera()
        }
    }
    private fun setCameraId(cameraId: String) {
        camera.cameraId = cameraId
        when (cameraId) {
            "0" -> {
                parameters["inputTextureCoordinate"]?.value  = ShaderParameter.AttributeParameter(coordCameraBack,2)
            }
            "1" -> {
                parameters["inputTextureCoordinate"]?.value  =  ShaderParameter.AttributeParameter(coordCameraFront, 2)
            }
            else -> {
                parameters["inputTextureCoordinate"]?.value  =  ShaderParameter.AttributeParameter(coordCameraBack, 2)
                LogUtil.e("please set the cameraId first !")
            }
        }
    }

    fun changeCamera() {
        camera.changeCamera()
        setCameraId(camera.cameraId)
    }
    override fun draw() {
        initProgram()
        surfaceTexture?.updateTexImage()
        super.draw()
    }


}