package com.lammy.effect.glutils

import java.nio.*

class BufferUtil {
    companion object{
        fun floatToBuffer(a: FloatArray): FloatBuffer? {
            val mBuffer: FloatBuffer
            //先初始化buffer，数组的长度*4，因为一个float占4个字节
            val mbb = ByteBuffer.allocateDirect(a.size * 4)
            //数组排序用nativeOrder
            mbb.order(ByteOrder.nativeOrder())
            mBuffer = mbb.asFloatBuffer()
            mBuffer.put(a)
            mBuffer.position(0)
            return mBuffer
        }

        fun intToBuffer(a: IntArray): IntBuffer? {
            val mBuffer2: IntBuffer
            //先初始化buffer，数组的长度*4，因为一个float占4个字节
            val mbb = ByteBuffer.allocateDirect(a.size * 4)
            //数组排序用nativeOrder
            mbb.order(ByteOrder.nativeOrder())
            mBuffer2 = mbb.asIntBuffer()
            mBuffer2.put(a)
            mBuffer2.position(0)
            return mBuffer2
        }

        fun shortToBuffer(shortDatas: ShortArray): ShortBuffer? {
            val dlb = ByteBuffer.allocateDirect(shortDatas.size * 2)
            dlb.order(ByteOrder.nativeOrder())
            val shortBuffer = dlb.asShortBuffer()
            shortBuffer.put(shortDatas)
            shortBuffer.position(0)
            return shortBuffer
        }
    }
}