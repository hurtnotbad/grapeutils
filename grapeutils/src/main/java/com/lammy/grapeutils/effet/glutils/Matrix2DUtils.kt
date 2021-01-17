package com.lammy.effect.glutils

import android.opengl.Matrix
import com.lammy.grapeutils.log.LogUtil

object Matrix2DUtils {

    fun getIdentityMatrix(): FloatArray {
        return floatArrayOf(
            1f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f
        )
    }

    fun printf(tag: String, matrix: FloatArray) {
//        for(i in 0..3) {
//            LogUtil.e(tag + " ${matrix[i * 4]} , ${matrix[i * 4 + 1]}, ${matrix[i * 4 + 2]}, ${matrix[i * 4 + 3]} ")
//        }
        LogUtil.e(
            tag + "\n${matrix[0]} , ${matrix[1]}, ${matrix[2]}, ${matrix[3]} \n" +
                    "${matrix[4]} , ${matrix[5]}, ${matrix[6]}, ${matrix[7]} \n" +
                    "${matrix[8]} , ${matrix[9]}, ${matrix[10]}, ${matrix[11]} \n" +
                    "${matrix[12]} , ${matrix[13]}, ${matrix[14]}, ${matrix[15]} "
        )

    }

    fun translateXY(matrix: FloatArray, x: Float, y: Float) {
        Matrix.translateM(matrix, 0, x, y, 0.0f)
    }

    fun scale(matrix: FloatArray, scaleSize: Float) {
        Matrix.scaleM(matrix, 0, scaleSize, scaleSize, scaleSize)
    }


}