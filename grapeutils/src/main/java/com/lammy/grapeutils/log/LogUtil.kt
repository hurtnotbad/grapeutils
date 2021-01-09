package com.lammy.grapeutils.log

import android.util.Log

class LogUtil {

    companion object{
        var debug = true
        val TAG = "lammy-java: "
        fun d(tag: String, meg: String?) {
            if (debug) Log.d(TAG + tag, meg!!)
        }

        fun e(tag: String, meg: String?) {
            if (debug) Log.e(TAG + tag, meg!!)
        }

        fun e(tag: String, meg: String?, throwable: Throwable) {
            if (debug) Log.e(TAG + tag, meg!!, throwable)
        }

        fun i(tag: String, meg: String?) {
            if (debug) Log.i(TAG + tag, meg!!)
        }

        fun v(tag: String, meg: String?) {
            if (debug) Log.v(TAG + tag, meg!!)
        }

        fun w(tag: String, meg: String?) {
            if (debug) Log.w(TAG + tag, meg!!)
        }


        fun d(meg: String?) {
            if (debug) Log.d(TAG, meg!!)
        }

        fun e(meg: String?) {
            if (debug) Log.e(TAG, meg!!)
        }
        fun e( meg: String?, throwable: Throwable) {
            if (debug) Log.e(TAG , meg!!, throwable)
        }

        fun i(meg: String?) {
            if (debug) Log.i(TAG, meg!!)
        }

        fun v(meg: String?) {
            if (debug) Log.v(TAG, meg!!)
        }

        fun w(meg: String?) {
            if (debug) Log.w(TAG, meg!!)
        }



    }



}