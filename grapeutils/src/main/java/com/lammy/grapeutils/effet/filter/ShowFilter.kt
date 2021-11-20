package com.lammy.grapeutils.effet.filter

import com.lammy.grapeutils.effet.common.ShaderConstant

class ShowFilter: LYFilter() {
    override var vertexShader = ShaderConstant.SHOW_FILTER_VERTEX_SHADER
    override var fragmentShader = ShaderConstant.NO_FILTER_FRAGMENT_SHADER
}