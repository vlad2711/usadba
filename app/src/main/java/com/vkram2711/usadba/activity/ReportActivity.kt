package com.vkram2711.usadba.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vkram2711.usadba.R
import com.vkram2711.usadba.adapters.ViewPagerAdapter
import com.vkram2711.usadba.callback.OnDataReceivedCallback
import com.vkram2711.usadba.callback.OnTabChangeCallback
import com.vkram2711.usadba.callback.OnTabelDownloaded
import com.vkram2711.usadba.utils.DatabaseUtils
import com.vkram2711.usadba.utils.ExcelUtils
import com.vkram2711.usadba.utils.Utils
import jxl.write.WritableWorkbook
import kotlinx.android.synthetic.main.activity_report.*
import kotlinx.android.synthetic.main.report_item.*
import java.text.SimpleDateFormat
import java.util.*

class ReportActivity : AppCompatActivity(), OnDataReceivedCallback, OnTabChangeCallback, OnTabelDownloaded {
    private val TAG = this::class.java.canonicalName
    override fun onDownloaded(wb: WritableWorkbook) {
        ExcelUtils().generateTabel(wb)
    }

    private lateinit var district: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        userInterfaceInit()
    }

    private fun userInterfaceInit(){
        district = intent.getStringExtra("district")!!

        DatabaseUtils.getJobs(if(district == "СЕВЕР") "north" else "south", this)


        viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)

    }

    override fun onReceived(data: Map<String, Any>?) {

    }

    override fun onChange(position: Int) {

        for(i in 0 until Utils.jobs[position].size){
            if(Utils.jobs[position][i].count == null){
                Toast.makeText(this, "Не заполнен элемент номер ${i + 1}", Toast.LENGTH_SHORT).show()
                return
            }
        }


        if(position == 5) {
            for (i in 0 until Utils.jobs.size) {
                for (j in 0 until Utils.jobs[i].size) {
                    if (Utils.jobs[i][j].count != 0 && Utils.jobs[i][j].count != null) {
                        Utils.additionalJobs[i].add(Utils.jobs[i][j])
                    }
                }
            }

            for(i in 0 until Utils.additionalJobs.size){
                for(j in 0 until Utils.additionalJobs[i].size){
                    Log.d(TAG, "$i $j ${Utils.additionalJobs[i][j].jobTitle}")
                }
            }
            ExcelUtils().generateSecondReport(ExcelUtils().createWorkbook("${Utils.bts["№ БТС"].toString()}-${SimpleDateFormat("dd-MM-YYYY").format(Date())}.xls")!!, "${Utils.bts["№ БТС"].toString()}-${SimpleDateFormat("dd-MM-YYYY").format(Date())}.xls", district)
            DatabaseUtils.getTabel(this)
            finish()

        } else{
            viewPager.currentItem = viewPager.currentItem + 1
            tabLayout.getTabAt(viewPager.currentItem)!!.select()
        }
    }
}
