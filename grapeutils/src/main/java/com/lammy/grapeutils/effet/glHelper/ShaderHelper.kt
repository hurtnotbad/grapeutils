package com.lammy.effect.glHelper

import android.content.Context
import android.opengl.GLES20
import com.lammy.grapeutils.log.LogUtil
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class ShaderHelper {
    companion object{
        fun readTextFileFromResourceRaw(context: Context, resourceId: Int): String? {
            val body = StringBuilder()
            try {
                val inputStream = context.resources.openRawResource(resourceId)
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                var nextLine: String?
                while (bufferedReader.readLine().also { nextLine = it } != null) {
                    body.append(nextLine)
                    body.append('\n')
                }
            } catch (e: IOException) {
                throw RuntimeException("无法读取资源")
            }
            return body.toString()
        }

        // 读取assets文件下shader文件为string
        fun readTextFileFromResourceAssets(context: Context, assetsPath: String?): String? {
            val body = StringBuilder()
            try {
                val inputStream = context.assets.open(assetsPath!!)
                val inputStreamReader =
                    InputStreamReader(inputStream)
                val bufferedReader =
                    BufferedReader(inputStreamReader)
                var nextLine: String?
                while (bufferedReader.readLine().also { nextLine = it } != null) {
                    body.append(nextLine)
                    body.append('\n')
                }
            } catch (e: IOException) {
                throw RuntimeException("无法读取资源")
            }
            return body.toString()
        }


        private fun compileVertexShader(shaderCode: String): Int {
            return compileShader(GLES20.GL_VERTEX_SHADER, shaderCode)
        }

        private fun compileFragmentShader(shaderCode: String): Int {
            return compileShader(GLES20.GL_FRAGMENT_SHADER, shaderCode)
        }

        private fun compileShader(type: Int, shaderCode: String): Int {
            val shaderObjectId = GLES20.glCreateShader(type)
            if (shaderObjectId == 0) {
                LogUtil.e("创建着色器失败！")
                return 0
            }
            //上传代码到着色器
            GLES20.glShaderSource(shaderObjectId, shaderCode)
            //编译着色器
            GLES20.glCompileShader(shaderObjectId)

            //取出编译状态
            val compileStatus = IntArray(1)
            //将编译状态写入第0个元素
            GLES20.glGetShaderiv(shaderObjectId, GLES20.GL_COMPILE_STATUS, compileStatus, 0)


            // 编译失败
            if (compileStatus[0] == 0) {
                LogUtil.e("compiling failed")
                // 输出编译日志
                LogUtil.e(
                    """
                        result of compiling source :
                        $shaderCode
                        :${GLES20.glGetShaderInfoLog(shaderObjectId)}
                        """.trimIndent()
                )
                GLES20.glDeleteShader(shaderObjectId)
                return 0
            } else {
                LogUtil.i("shader compiling success")
            }
            return shaderObjectId
        }

        // 将 顶点着色器和片着色器链接到一个program中
        private fun linkProgram(vertexShaderId: Int, fragmentShaderId: Int): Int {
            if (vertexShaderId == 0 || fragmentShaderId == 0) {
                LogUtil.e("vertexShader or  fragmentShader  id is 0")
                return 0
            }
            val programObjectId = GLES20.glCreateProgram()
            if (programObjectId == 0) {
                LogUtil.e("can not create new program !")
                return 0
            }
            GLES20.glAttachShader(programObjectId, vertexShaderId)
            GLES20.glAttachShader(programObjectId, fragmentShaderId)
            GLES20.glLinkProgram(programObjectId)
            val linkState = IntArray(1)
            GLES20.glGetProgramiv(programObjectId, GLES20.GL_LINK_STATUS, linkState, 0)
            if (linkState[0] == 0) {
                // 输出编译日志
                LogUtil.e(
                    """
                        linking of program failed  :
                        
                        """.trimIndent() + GLES20.glGetProgramInfoLog(
                        programObjectId
                    )
                )
                return 0
            } else {
                LogUtil.i("linking of program success !")
            }
            return programObjectId
        }


        private fun validateProgram(programObjectId: Int): Boolean {
            GLES20.glValidateProgram(programObjectId)
            val state = IntArray(1)
            GLES20.glGetProgramiv(programObjectId, GLES20.GL_VALIDATE_STATUS, state, 0)

            // 输出编译日志
            if (state[0] == 0) {
                LogUtil.e(
                    """
                        validateProgram failed ,result of validating program :${state[0]}
                        : log:${GLES20.glGetProgramInfoLog(programObjectId)}
                        """.trimIndent()
                )
            }
            return state[0] != 0
        }

        fun buildProgram(
            vertexShaderSource: String,
            fragmentShaderSource: String
        ): Int {
            val program: Int
            //compile the shaders
            val vertexShader = compileVertexShader(vertexShaderSource)
            val fragmentShader = compileFragmentShader(fragmentShaderSource)
            program = linkProgram(vertexShader, fragmentShader)
            if (LogUtil.debug) {
                validateProgram(program)
            }
            return program
        }

    }
}