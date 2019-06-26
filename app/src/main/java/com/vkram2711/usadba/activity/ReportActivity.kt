package com.vkram2711.usadba.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vkram2711.usadba.R
import com.vkram2711.usadba.adapters.ViewPagerAdapter
import com.vkram2711.usadba.utils.ExcelUtils
import kotlinx.android.synthetic.main.activity_report.*

class ReportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        userInterfaceInit()
    }

    private fun userInterfaceInit(){
        floatingActionButton.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            intent.putExtra("category", tabLayout.selectedTabPosition)
            startActivity(intent)
        }

        viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)

        save.setOnClickListener {
            ExcelUtils().generateSecondReport(ExcelUtils().createWorkbook("jobs.xls")!!)
            finish()
        }
    }

}
