//package com.lammy.grapeutils.effet.filter
//
//import com.lammy.grapeutils.effet.common.ShaderConstant
//import com.lammy.grapeutils.effet.common.ShaderParameter2
//
//class TransColorFilter : Filter() {
//
//    companion object {
//        const val transfer = "transTexture"
//    }
//
//    init {
//        initParameters()
//    }
//
//    override fun getVertexShader(): String {
//        return ShaderConstant.NO_FILTER_VERTEX_SHADER
//    }
//
//    override fun getFragmentShader(): String {
//        return """
//precision mediump float;
//varying vec2 textureCoordinate;
//uniform sampler2D inputTexture;
//uniform sampler2D transTexture;
//vec3 sort(float rgb[3]){// 数组传进去，但不会返回，就是说不会修改main种rgb数组值
//    for(int i = 0 ; i < 3; i ++)
//    for(int j = i ; j < 3; j ++) {
//        if(rgb[i] > rgb[j]) {
//            float value = rgb[i];
//            rgb[i] = rgb[j];
//            rgb[j] = value;
//        }
//    }
//    return vec3(rgb[0], rgb[1],rgb[2]);
//}
//void main()
//{
//    vec4 color1 = texture2D(inputTexture, textureCoordinate);
//    vec4 color2= texture2D(transTexture, textureCoordinate);
//
//    //计算 index， color2种rgb从小到大的index
//    int index[] = int[3](0,1,2);
//    float rgb[] = float[3](color2.r , color2.g, color2.b);
//    for(int i = 0 ; i < 3; i ++)
//    for(int j = i ; j < 3; j ++) {
//        if(rgb[i] > rgb[j]) {
//            int a = index[i];
//            index[i] = index[j];
//            index[j] = a;
//            float value = rgb[i];
//            rgb[i] = rgb[j];
//            rgb[j] = value;
//        }
//    }
//
//// 对color1进行排序
//    vec3 rgbVec = sort(float[3](color1.r , color1.g, color1.b));
//    // 让color1的rgb大小排列与 color2的颜色顺序一致
//    float re[3];
//    re[index[0]] = rgbVec.r;
//    re[index[1]] = rgbVec.g;
//    re[index[2]] = rgbVec.b;
//    vec4 newColor = vec4(re[0],re[1],re[2],1.0);
//
//    gl_FragColor = mix(color1, newColor, 0.5);
////    gl_FragColor = color1;
//}
//"""
//    }
//
//    override fun initParameters() {
//        super.initParameters()
//        addParameter(transfer, null, ShaderParameter2.TYPE_TEXTURE)
//    }
//
//    override fun setParameters() {
//        super.setParameters()
//        setUniformTexture(transfer, 3)
//    }
//
//    override fun draw() {
//        super.draw()
//        printParameter("lammy-tr")
//    }
//}