package com.lammy.grapeutils.effet.filter

import com.lammy.grapeutils.effet.common.ShaderConstant

class NoFilter: LYFilter() {
    override var vertexShader = ShaderConstant.NO_FILTER_VERTEX_SHADER
    override var fragmentShader = ShaderConstant.NO_FILTER_FRAGMENT_SHADER
}