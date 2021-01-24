package com.lammy.grapeutils.effet.filter

import android.graphics.BitmapFactory
import com.lammy.grapeutils.effet.base.GrapeEffect
import com.lammy.grapeutils.effet.common.ShaderConstant
import com.lammy.grapeutils.effet.common.ShaderParameter


class LutTableFilter: Filter() {

    init {
        initParameters()
    }

    override fun getVertexShader(): String {
        return ShaderConstant.SHOW_FILTER_VERTEX_SHADER
    }

    override fun getFragmentShader(): String {
        return """varying highp vec2 textureCoordinate;
                uniform sampler2D vTexture;
                uniform sampler2D lutTexture;
                uniform highp float intensity;
                //lookup table 为大小为512*512，个数为8*8的格子表
                void main(){
                    highp vec4 textureColor = texture2D(vTexture, textureCoordinate);
                //    //根据当前颜色的蓝色通道查找所在格子
                    highp float blueColor = textureColor.b * 63.0;
                    highp vec2 quad1;
                    quad1.y = floor(floor(blueColor) / 8.0);
                    quad1.x = floor(blueColor) - (quad1.y * 8.0);
                
                    highp vec2 quad2;
                    quad2.y = floor(ceil(blueColor) / 8.0);
                    quad2.x = ceil(blueColor) - (quad2.y * 8.0);
                    highp vec2 texPos1;
                    texPos1.x = (quad1.x * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.r);
                    texPos1.y = (quad1.y * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.g);
                    highp vec2 texPos2;
                    texPos2.x = (quad2.x * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.r);
                    texPos2.y = (quad2.y * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.g);
                    lowp vec4 newColor1 = texture2D(lutTexture, texPos1);
                    lowp vec4 newColor2 = texture2D(lutTexture, texPos2);
                    lowp vec4 newColor = mix(newColor1, newColor2, fract(blueColor));
                    gl_FragColor = mix(textureColor, vec4(newColor.rgb, textureColor.w), intensity);
                }"""
    }
    companion object{
        const val lut = "lutTexture"
        const val intensity = "intensity"
    }

    override fun initParameters() {
        super.initParameters()
        val lutImage = BitmapFactory.decodeStream(GrapeEffect.grapeContext.assets.open("highkey.png"))
        addParameter(lut,lutImage, ShaderParameter.TYPE_TEXTURE)
        addParameter(intensity,0.8f, ShaderParameter.TYPE_UNIFORM)
    }

    override fun setParameters() {
        super.setParameters()
        setUniform1f(intensity)
        setUniformTexture(lut,1)
    }

}