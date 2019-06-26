package com.vkram2711.usadba.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.vkram2711.usadba.R
import com.vkram2711.usadba.adapters.BufferReportsAdapter
import com.vkram2711.usadba.callback.OnDataReceivedCallback
import com.vkram2711.usadba.utils.DatabaseUtils
import kotlinx.android.synthetic.main.activity_report_merge.*

class ReportMergeActivity : AppCompatActivity(), OnDataReceivedCallback {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_merge)

        DatabaseUtils.getFiles("СЕВЕР", this)

    }

    override fun onReceived(data: Map<String, Any>?) {
        buffer_report_list.adapter = BufferReportsAdapter()
        buffer_report_list.layoutManager = LinearLayoutManager(this)
        buffer_report_list.adapter!!.notifyDataSetChanged()
    }
}
