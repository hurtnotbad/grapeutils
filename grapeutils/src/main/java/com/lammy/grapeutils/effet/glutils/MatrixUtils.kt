package com.lammy.grapeutils.effet.glutils

import android.opengl.Matrix

class MatrixUtils {


    fun translateM(m: FloatArray, x: Float, y: Float, z: Float) {
        for (i in 0..3) {
            m[12 + i] += m[i] * x + m[4 + i] * y + m[8 + i] * z
        }
    }

    fun scaleM(m: FloatArray, x: Float, y: Float, z: Float) {
        for (i in 0..3) {
            m[i] *= x
            m[4 + i] *= y
            m[8 + i] *= z
        }
    }

    fun rotateM(m: FloatArray?, a: Float, x: Float, y: Float, z: Float) {
        Matrix.rotateM(m,0,a,x,y,z)
    }

}