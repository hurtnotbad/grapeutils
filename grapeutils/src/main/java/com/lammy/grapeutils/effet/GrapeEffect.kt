package com.lammy.grapeutils.effet

import android.content.Context

class GrapeEffect {
    companion object{
        lateinit var grapeContext: Context
        fun initEffect(applicationContext: Context){
            grapeContext = applicationContext
        }
    }
}