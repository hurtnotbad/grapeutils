package com.lammy.grapeutils.effet.animation

import android.opengl.Matrix
import com.lammy.effect.glutils.Matrix2DUtils
import com.lammy.grapeutils.effet.common.ShaderConstant
import com.lammy.grapeutils.log.LogUtil

class NoAnimation : Animation() {

    init {
        initParameters()
    }

    override fun getVertexShader(): String {
        return """
            attribute vec4 vPosition;
            attribute vec2 inputTextureCoordinate;
            varying vec2 textureCoordinate;
            uniform mat4 matrix;
            void main()
            {
                gl_Position = matrix*vec4(vPosition.x, -vPosition.y,0.0,1.0);
                textureCoordinate = inputTextureCoordinate;
            }
        """.trimIndent()
    }

    override fun getFragmentShader(): String {
        return ShaderConstant.NO_FILTER_FRAGMENT_SHADER
    }

    var startTranslateXY = floatArrayOf(-2f, -2f)
    var endTranslateXY = floatArrayOf(0.5f, 0.5f)


    var startScaleXY = floatArrayOf(1f, 1f)
    var endScaleXY = floatArrayOf(0.5f, 0.5f)

    var startRotateAngle = 0
    var endRotateAngle = 90

    override fun updateParameters() {
//        setParameter(matrixString, getTranslateMatrix())
//        setParameter(matrixString, getScaleMatrix())

//        var matrix = FloatArray(16)
//        var matrix2 = FloatArray(16)
//        var matrix3 = FloatArray(16)
////        Matrix.multiplyMM(matrix,0,getScaleMatrix(),0,getTranslateMatrix(),0)
//        Matrix.multiplyMM(matrix,0,getTranslateMatrix(),0,getScaleMatrix(),0)
//        Matrix.multiplyMM(matrix,0,getTranslateMatrix(),0,getScaleMatrix(),0)


//        Matrix.scaleM(Matrix2DUtils.getIdentityMatrix(), 0, scaleX, scaleY, 1f)

        var matrix = getRotateMatrix()
        var matrix2 = getScaleMatrix()
        var matrix3 = getTranslateMatrix()


//        Matrix.multiplyMM(result1,0,getTranslateMatrix(),0,getScaleMatrix(),0)
//        Matrix.multiplyMM(result1,0,getRotateMatrix(),0,result1,0)

        var projectMatrix = getTranslateMatrix()
        var viewMatrix = getTranslateMatrix()
        var modelMatrix = Matrix2DUtils.getIdentityMatrix()

        Matrix.orthoM(projectMatrix,0,-1f, 1f, -height.toFloat()/width, height.toFloat()/width, 3f,100f)
        Matrix.setLookAtM(viewMatrix,0, 0f,0f,10f,0f,0f,0f, 0f,1f,0f)

        val progress = getProgress()

        val transX = startTranslateXY[0] + progress * (endTranslateXY[0] - startTranslateXY[0])
        val transY = startTranslateXY[1] + progress * (endTranslateXY[1] - startTranslateXY[1])

        val scaleX = startScaleXY[0] + progress * (endScaleXY[0] - startScaleXY[0])
        val scaleY = startScaleXY[1] + progress * (endScaleXY[1] - startScaleXY[1])

        val rotateAngle = startRotateAngle + progress * (endRotateAngle - startRotateAngle)

//        Matrix.translateM(modelMatrix, 0, transX, transY, 0f)
//        Matrix.scaleM(modelMatrix, 0, scaleX, scaleY, 1f)
//        Matrix.rotateM(modelMatrix,0, rotateAngle, 0f, 0f, 1f)

//        Matrix.multiplyMM(matrix,0,getTranslateMatrix(),0,getScaleMatrix(),0)
//        Matrix.multiplyMM(modelMatrix,0,matrix,0,getRotateMatrix(),0)

        val newMatrix = getTranslateMatrix()
        val mvp = getTranslateMatrix()

        Matrix.multiplyMM(newMatrix, 0, projectMatrix, 0, viewMatrix, 0);

        LogUtil.e("newmatrix = ${newMatrix.contentToString()}" )
        Matrix.multiplyMM(mvp, 0, newMatrix, 0, modelMatrix, 0);

        setParameter(matrixString,mvp)
    }


    private fun getTranslateMatrix(): FloatArray {
        val progress = getProgress()
        val transX = startTranslateXY[0] + progress * (endTranslateXY[0] - startTranslateXY[0])
        val transY = startTranslateXY[1] + progress * (endTranslateXY[1] - startTranslateXY[1])
        val floatArray: FloatArray = Matrix2DUtils.getIdentityMatrix()
        Matrix.translateM(floatArray, 0, transX, transY, 0f)
        return floatArray
    }

    private fun getScaleMatrix(): FloatArray {
        val progress = getProgress()
        val scaleX = startScaleXY[0] + progress * (endScaleXY[0] - startScaleXY[0])
        val scaleY = startScaleXY[1] + progress * (endScaleXY[1] - startScaleXY[1])
        val floatArray: FloatArray = Matrix2DUtils.getIdentityMatrix()
        Matrix.scaleM(floatArray, 0, scaleX, scaleY, 1f)
        return floatArray
    }

    private fun getRotateMatrix(): FloatArray {
        val progress = getProgress()
        val rotateAngle = startRotateAngle + progress * (endRotateAngle - startRotateAngle)
        val floatArray: FloatArray = Matrix2DUtils.getIdentityMatrix()
//        Matrix.scaleM(floatArray, 0, scaleX, scaleY, 1f)
        Matrix.rotateM(floatArray,0, rotateAngle, 0f, 0f, 1f)
        return floatArray
    }

}