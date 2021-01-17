package com.lammy.grapeutils.effet.filter

import com.lammy.grapeutils.effet.common.ShaderConstant

class ShowFilter: Filter() {

    init {
        initParameters()
    }

    override fun getVertexShader(): String {
        return ShaderConstant.SHOW_FILTER_VERTEX_SHADER
    }

    override fun getFragmentShader(): String {
        return ShaderConstant.NO_FILTER_FRAGMENT_SHADER
    }

}