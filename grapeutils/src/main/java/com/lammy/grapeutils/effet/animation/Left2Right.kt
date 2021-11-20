package com.lammy.grapeutils.effet.animation

import android.opengl.Matrix
import com.lammy.effect.glutils.Matrix2DUtils

class Left2Right : LYAnimation() {

    override fun updateParameters() {

        val projectMatrix = Matrix2DUtils.getIdentityMatrix()
        val viewMatrix = Matrix2DUtils.getIdentityMatrix()
        val modelMatrix = Matrix2DUtils.getIdentityMatrix()

//        /******************* mvp demo************************/
//        val newMatrix =  Matrix2DUtils.getIdentityMatrix()
//        val mvp = Matrix2DUtils.getIdentityMatrix()
//        Matrix.translateM(modelMatrix,0, 1f,0f, 0f)
//        Matrix.rotateM(modelMatrix,0,90f, 0f, 0f, 1f)
//        Matrix.scaleM(modelMatrix,0, 0.5f, 0.5f, 0f)
//        //正交投影, gl 默认就是正交投影，因此可以不用设置
////        Matrix.orthoM(projectMatrix,0,-1f, 1f, -height.toFloat()/width, height.toFloat()/width, 3f,100f)
//        Matrix.orthoM(projectMatrix,0,-1f, 1f, -1f, 1f, 3f,100f)
//        Matrix.setLookAtM(viewMatrix,0, 0f,0f,100f,0f,0f,0f, 0f,1f,0f)
//        Matrix.multiplyMM(newMatrix, 0, projectMatrix, 0, viewMatrix, 0)
//        Matrix.multiplyMM(mvp, 0, modelMatrix, 0, newMatrix, 0)
//        setMatrix(mvp)
//        /******************* mvp demo end************************/

        /******************* mvp demo2 与demo1效果一样************************/
        val newMatrix = Matrix2DUtils.getIdentityMatrix()
        val mvp = Matrix2DUtils.getIdentityMatrix()
        var transMatrix = Matrix2DUtils.getIdentityMatrix()
        var rotateMatrix = Matrix2DUtils.getIdentityMatrix()
        var scaleMatrix = Matrix2DUtils.getIdentityMatrix()
        Matrix.translateM(transMatrix, 0, 1f, 0f, 0f)
        Matrix.rotateM(rotateMatrix, 0, 90f, 0f, 0f, 1f)
        Matrix.scaleM(scaleMatrix, 0, 0.5f, 0.5f, 0f)
        Matrix.multiplyMM(modelMatrix, 0, transMatrix, 0, rotateMatrix, 0)
        Matrix.multiplyMM(modelMatrix, 0, modelMatrix, 0, scaleMatrix, 0)
        //正交投影, gl 默认就是正交投影，因此可以不用设置
//        Matrix.orthoM(projectMatrix,0,-1f, 1f, -height.toFloat()/width, height.toFloat()/width, 3f,100f)
        Matrix.orthoM(projectMatrix, 0, -1f, 1f, -1f, 1f, 3f, 100f)
        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, 100f, 0f, 0f, 0f, 0f, 1f, 0f)
        Matrix.multiplyMM(newMatrix, 0, projectMatrix, 0, viewMatrix, 0)
        Matrix.multiplyMM(mvp, 0, modelMatrix, 0, newMatrix, 0)
        setMatrix(mvp)
        /******************* mvp demo2 end************************/

        // left to right
        val mvpMatrix = Matrix2DUtils.getIdentityMatrix()
        Matrix.translateM(mvpMatrix, 0, getTranslateX(), 0f, 0f)
        setMatrix(mvpMatrix)
    }

    var index = 0
    private fun getTranslateX(): Float {
        index++
        return (index * 0.005f) % 2
    }

}