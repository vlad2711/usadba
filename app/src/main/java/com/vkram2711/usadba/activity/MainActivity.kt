package com.vkram2711.usadba.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.vkram2711.usadba.R
import com.vkram2711.usadba.callback.OnDataReceivedCallback
import com.vkram2711.usadba.utils.DatabaseUtils

class MainActivity : AppCompatActivity(), OnDataReceivedCallback {
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userInterfaceInit()
    }

    private fun userInterfaceInit(){
        DatabaseUtils.getJobs("north")
    }

    override fun onReceived(data: Map<String, Any>?) {
        Log.d(TAG, data.toString())
    }
}
