package com.vkram2711.usadba.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.vkram2711.usadba.R
import com.vkram2711.usadba.adapters.ViewPagerAdapter
import com.vkram2711.usadba.models.Job
import kotlinx.android.synthetic.main.activity_report.*
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.EventBus

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
    }

}
