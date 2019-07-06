package com.vkram2711.usadba.activity

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import com.vkram2711.usadba.R
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.vkram2711.usadba.Constants
import com.vkram2711.usadba.callback.OnDataReceivedCallback
import com.vkram2711.usadba.models.ActBufferModel
import com.vkram2711.usadba.utils.DatabaseUtils
import com.vkram2711.usadba.utils.ExcelUtils
import com.vkram2711.usadba.utils.Utils
import kotlinx.android.synthetic.main.activity_new_report.*
import android.widget.ArrayAdapter

class NewReportActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, OnDataReceivedCallback {
    private val jobs = arrayOf("Ревизия", "ТО", "ТО+Геодезия", "Геодезия", "Ревизия+Геодезия", "РВР")
    private var currentJob: String? = null
    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_report)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        userInterfaceInit()

    }

    private fun userInterfaceInit(){


        job.setOnCheckedChangeListener { p0, p1 ->
            onRadioButtonClicked(p1)
            Log.d("radio", p1.toString())
        }
        start.setOnClickListener {
            if(id.text.isNotEmpty() && currentJob != null && Utils.partner != null) {
                DatabaseUtils.getBts(Integer.parseInt(id.text.toString()), this@NewReportActivity)
            } else{
                Toast.makeText(this, "не введен номер БС или вид работ или имя напарника", Toast.LENGTH_SHORT).show()
            }
        }

        val users = Array(Utils.users.size) {""}
        for(i in 0 until Utils.users.size){
            users[i] = Utils.users[i].name
        }
        val spinnerArrayAdapter = ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item,
            users
        ) //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(
            android.R.layout
                .simple_spinner_dropdown_item
        )

        partner.adapter = spinnerArrayAdapter
        partner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Utils.partner = Utils.users[p2]
            }

        }

        if(Utils.user.permission == "2") {
            report.setOnClickListener {
                val intent = Intent(this@NewReportActivity, ReportMergeActivity::class.java)
                intent.putExtra("district", "north")
                startActivity(intent)
            }
        } else {
            report.visibility = View.GONE
        }
    }

    override fun onReceived(data: Map<String, Any>?) {

        if(data != null) {
            DatabaseUtils.uploadFileToStorage(
                ActBufferModel(
                    data["№ БТС"].toString().toInt(),
                    data["Адрес"].toString(),
                    getPrice(currentJob!!, data),
                    data["Раздел"].toString(),
                    data["Район"].toString(),
                    currentJob!!
                )
            )


            val intent = Intent(this, ReportActivity::class.java)
            intent.putExtra("district", data["Раздел"].toString())
            startActivity(intent)
        } else {
            Toast.makeText(this, "Указан неверный адресс бтс", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getPrice(currentJob: String, data: Map<String, Any>): Double{
        return when(currentJob){
            jobs[0] -> data["Ревизия"].toString().toDouble()
            jobs[1] -> data["ТО"].toString().toDouble()
            jobs[2] -> data["ТО"].toString().toDouble() + data["Геодезия"].toString().toDouble()
            jobs[3] -> data["Геодезия"].toString().toDouble()
            jobs[4] -> data["Геодезия"].toString().toDouble() + data["Ревизия"].toString().toDouble()
            else -> 0.0
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.PERMISSION_REQUEST_CODE) {
            userInterfaceInit()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPermissions(vararg permissions: String) = ActivityCompat.requestPermissions(this, permissions, Constants.PERMISSION_REQUEST_CODE)

    private fun onRadioButtonClicked(id: Int) {
        when (id) {
            R.id.revision -> currentJob = jobs[0]
            R.id.to -> currentJob = jobs[1]
            R.id.to_geodesy -> currentJob = jobs[2]
            R.id.geodesy -> currentJob = jobs[3]
            R.id.revision_geodesy -> currentJob = jobs[4]
            R.id.rvr -> currentJob = jobs[5]
        }
    }
}
