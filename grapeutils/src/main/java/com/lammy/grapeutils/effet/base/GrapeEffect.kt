package com.lammy.grapeutils.effet.base

import android.content.Context

class GrapeEffect {
    companion object{
        lateinit var grapeContext: Context
        fun initEffect(applicationContext: Context){
            grapeContext = applicationContext
        }
    }
}