package com.lammy.grapeutils.permission

import android.Manifest
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lammy.grapeutils.effet.glutils.ContextUtil
import com.lammy.grapeutils.log.LogUtil

abstract class PermissionActivity : AppCompatActivity() {

    companion object{
        const val TAG = "PermissionActivity:"
    }
    private val requestCode = 100
    private val permissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ContextUtil.globalContext = applicationContext
        requestPermissions()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == requestCode) {
            for (result in grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "grand failed !", Toast.LENGTH_LONG).show()
                    return
                }
            }
            onDonePermissionGranted()
        }
    }

     open fun requestPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onDonePermissionGranted()
        } else {
            for (permission in permissions) {
                val result = checkSelfPermission(permission)
                if (result == PackageManager.PERMISSION_DENIED) {
                    LogUtil.e(TAG,"no permission: $permission")
                    requestPermissions(permissions, requestCode)
                    return
                }
            }
            onDonePermissionGranted()
        }
    }

     abstract fun  onDonePermissionGranted()

}