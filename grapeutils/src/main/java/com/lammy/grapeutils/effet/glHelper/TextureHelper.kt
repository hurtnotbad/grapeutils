package com.lammy.grapeutils.effet.glHelper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES11Ext
import android.opengl.GLES20
import android.opengl.GLUtils
import com.lammy.grapeutils.log.LogUtil
import javax.microedition.khronos.opengles.GL10
import javax.microedition.khronos.opengles.GL10.GL_RGBA

class TextureHelper {
    companion object{
        fun loadTexture(context: Context, resourceId: Int): Int {

            // 创建纹理
            val textureObjectIds = IntArray(1)
            GLES20.glGenTextures(1, textureObjectIds, 0)
            if (textureObjectIds[0] == 0) {
                LogUtil.e("could't generate a new opengl texture object ")
                return 0
            }
            val options = BitmapFactory.Options()
            options.inScaled = false
            val bitmap =
                BitmapFactory.decodeResource(context.resources, resourceId, options)
            if (bitmap == null) {
                LogUtil.e("加载纹理图片" + resourceId + "失败")
                GLES20.glDeleteTextures(1, textureObjectIds, 0)
                return 0
            }
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureObjectIds[0])
            GLES20.glTexParameteri(
                GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_LINEAR_MIPMAP_LINEAR
            )
            GLES20.glTexParameteri(
                GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_LINEAR
            )
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)
            bitmap.recycle()
            // 生成mip贴图
            GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D)
            // 解除纹理绑定，传入0即是 解除
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
            return textureObjectIds[0]
        }

        fun loadTexture(bitmap: Bitmap): Int {
            // 创建纹理
            val textureObjectIds = IntArray(1)
            GLES20.glGenTextures(1, textureObjectIds, 0)
            if (textureObjectIds[0] == 0) {
                LogUtil.e("could't generate a new opengl texture object ")
                return 0
            }
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureObjectIds[0])
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR)
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)
            bitmap.recycle()
            // 生成mip贴图
            GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D)
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
            return textureObjectIds[0]
        }

        private fun useTexParameter() {
            //设置缩小过滤为使用纹理中坐标最接近的一个像素的颜色作为需要绘制的像素颜色
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR.toFloat())
            //设置放大过滤为使用纹理中坐标最接近的若干个颜色，通过加权平均算法得到需要绘制的像素颜色
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR.toFloat())
            //设置环绕方向S，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE.toFloat())
            //设置环绕方向T，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE.toFloat())
        }

        private fun useTexParameterNear() {
            //设置缩小过滤为使用纹理中坐标最接近的一个像素的颜色作为需要绘制的像素颜色
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST.toFloat())
            //设置放大过滤为使用纹理中坐标最接近的若干个颜色，通过加权平均算法得到需要绘制的像素颜色
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST.toFloat())
            //设置环绕方向S，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE.toFloat())
            //设置环绕方向T，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE.toFloat())
        }

        fun genTextureWithNoData(textures:IntArray, start:Int, size: Int, width:Int, height:Int){
            GLES20.glGenTextures(size, textures, start)
            for (i in 0 until size) {
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[i])
                GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null)
                useTexParameter()
            }
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)

        }

        fun createTexture(bitmap: Bitmap, texture: IntArray): Int {
            if (!bitmap.isRecycled) {
                GLES20.glGenTextures(1, texture, 0)
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture[0])
                useTexParameter()
                GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
                bitmap.recycle()
                return texture[0]
            }
            return 0
        }


        fun createOESTexture(tex: IntArray): Int {
            GLES20.glGenTextures(1, tex, 0)
            GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, tex[0])
            GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST.toFloat())
            GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR.toFloat())
            GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE.toFloat())
            GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE.toFloat())
            GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 0)
            return tex[0]
        }

        fun deleteTexture(textures: IntArray){
            GLES20.glDeleteTextures(textures.size, textures, 0)
        }

    }
}