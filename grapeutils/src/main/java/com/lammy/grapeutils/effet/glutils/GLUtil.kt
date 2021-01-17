package com.lammy.grapeutils.effet.glutils

import android.opengl.GLES20
import android.opengl.GLU
import com.lammy.grapeutils.log.LogUtil

class GLUtil {
    companion object {
        const val INVALID_PROGRAM = -1
        const val INVALID_TEXTURE = -1
        fun checkError(tag: String) {
            var error = GLES20.glGetError()
            if (error != GLES20.GL_NO_ERROR) {
                val throwable = Throwable()
                LogUtil.e(
                    tag + "GL Error: ${GLU.gluErrorString(error)}" + throwable.printStackTrace(),
                    throwable
                )
            }
        }

    }
}