package com.vkram2711.usadba.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.vkram2711.usadba.R
import com.vkram2711.usadba.models.Job
import com.vkram2711.usadba.utils.DatabaseUtils
import com.vkram2711.usadba.utils.Utils
import kotlinx.android.synthetic.main.activity_form.*
import org.greenrobot.eventbus.EventBus

class FormActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private val TAG = this::class.java.simpleName
    var jobPosition = -1
    var category = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        category = intent.getIntExtra("category", 0)
        userInterfaceInit()
    }

    private fun userInterfaceInit(){

        val jobs = arrayOfNulls<String>(Utils.jobs[category].size)
        Log.d(TAG, Utils.jobs.size.toString())
        var k = 0
        for (i in 0 until Utils.jobs[category].size) {
            if(category == Utils.jobs[category][i].category) {
                jobs[k] = Utils.jobs[category][i].jobTitle
                k++
            }
        }
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, jobs)

        job.adapter = adapter
        job.onItemSelectedListener = this

        add.setOnClickListener {
            if(jobPosition >= 0){
                val j = Utils.jobs[category][jobPosition]
                val job = Job(j.number, j.price, j.jobTitle, count.text.toString(), j.unit, j.category)
                Utils.additionalJobs[category].add(job)
                EventBus.getDefault().post("event")
                finish()
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        jobPosition = -1
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        jobPosition = position
    }
}
