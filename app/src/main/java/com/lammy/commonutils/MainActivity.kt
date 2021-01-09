package com.lammy.commonutils

import android.os.Bundle
import com.lammy.grapeutils.log.LogUtil
import com.lammy.grapeutils.permission.PermissionActivity

class MainActivity : PermissionActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onDonePermissionGranted() {
        LogUtil.e("hello")
    }

}