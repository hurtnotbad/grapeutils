package com.lammy.grapeutils.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.lammy.grapeutils.effet.common.ImageTexture
import com.lammy.grapeutils.effet.common.Texture
import com.lammy.grapeutils.effet.glutils.ContextUtil

object AssetsUtil {

   fun getBitmapFormAssets(path:String): Bitmap {
      return BitmapFactory.decodeStream(ContextUtil.globalContext.assets.open(path))
    }

    // 这个是用来测试的纹理，index为0
    val testInTexture:ImageTexture = ImageTexture(AssetsUtil.getBitmapFormAssets("test.jpg"), 0)

}