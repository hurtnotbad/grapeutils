package com.lammy.grapeutils.effet.filter

import android.provider.Settings
import com.lammy.effect.glutils.BufferUtil
import com.lammy.grapeutils.effet.common.*
import com.lammy.grapeutils.utils.AssetsUtil


class LutTableFilter: LYFilter() {


    override var vertexShader = ShaderConstant.SHOW_FILTER_VERTEX_SHADER
    override var fragmentShader = """varying highp vec2 textureCoordinate;
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

    override fun addParameters() {
        parameters["vPosition"] = ShaderParameter(
            "vPosition",
            ShaderParameter.TYPE_ATTRIBUTE,
            ShaderParameter.VALUE_TYPE_ATTRIBUTE_PARAMETER
        )
        parameters["inputTextureCoordinate"] = ShaderParameter(
            "inputTextureCoordinate",
            ShaderParameter.TYPE_ATTRIBUTE,
            ShaderParameter.VALUE_TYPE_ATTRIBUTE_PARAMETER
        )
        parameters["vTexture"] = ShaderParameter(
            "vTexture",
            ShaderParameter.TYPE_TEXTURE,
            ShaderParameter.VALUE_TYPE_IMAGE_TEXTURE
        )
        parameters["lutTexture"] = ShaderParameter(
            "lutTexture",
            ShaderParameter.TYPE_TEXTURE,
            ShaderParameter.VALUE_TYPE_IMAGE_TEXTURE
        )
        parameters["intensity"] = ShaderParameter(
            "intensity",
            ShaderParameter.TYPE_UNIFORM,
            ShaderParameter.VALUE_TYPE_FLOAT
        )
    }
   override  fun setInTexture(texture: Texture){
        parameters["vTexture"]?.value = texture
    }

    override fun getInTexture(): Texture{
        return parameters["vTexture"]?.value as Texture
    }

    override fun setDefaultParameterValues() {
        parameters["vPosition"]?.value = ShaderParameter.AttributeParameter(ShaderConstant.POSITIONS, 2)
        parameters["inputTextureCoordinate"]?.value = ShaderParameter.AttributeParameter(ShaderConstant.COORDINATE, 2)
        parameters["vTexture"]?.value =  AssetsUtil.testInTexture
        parameters["lutTexture"]?.value = ImageTexture(AssetsUtil.getBitmapFormAssets("highkey.png"), 1)
        parameters["intensity"]?.value = 0.8f
    }
}