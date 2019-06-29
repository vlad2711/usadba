package com.vkram2711.usadba.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import com.vkram2711.usadba.R
import com.vkram2711.usadba.adapters.BufferReportsAdapter
import com.vkram2711.usadba.callback.OnDataReceivedCallback
import com.vkram2711.usadba.utils.DatabaseUtils
import com.vkram2711.usadba.utils.ExcelUtils
import kotlinx.android.synthetic.main.activity_report_merge.*

class ReportMergeActivity : AppCompatActivity(), OnDataReceivedCallback {

    lateinit var district: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_merge)

        district = intent.getStringExtra("district")!!


        DatabaseUtils.getFiles(district, this)

    }

    override fun onReceived(data: Map<String, Any>?) {
        buffer_report_list.adapter = BufferReportsAdapter()
        buffer_report_list.layoutManager = LinearLayoutManager(this)

        create_report.setOnClickListener {
            val wb = ExcelUtils().createWorkbook("first.xls")!!
            ExcelUtils().generateFirstReport(wb, district,"first.xls" )
            finish()
        }
    }
}
