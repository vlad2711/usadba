package com.vkram2711.usadba.activity

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import com.vkram2711.usadba.R
import android.widget.ArrayAdapter
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


class NewReportActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, OnDataReceivedCallback {
    private val jobs = arrayOf("Ревизия", "ТО", "ТО+Геодезия", "Геодезия", "Ревизия+Геодезия")

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

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, jobs)

        job_type.adapter = adapter
        job_type.onItemSelectedListener = this

        id.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        start.setOnClickListener {
            if(id.text.isNotEmpty()) {
                DatabaseUtils.getBts(Integer.parseInt(id.text.toString()), this@NewReportActivity)
                DatabaseUtils.getJobs("north")

                val intent = Intent(this, ReportActivity::class.java)
                startActivity(intent)
            }
        }

        report.setOnClickListener {
            startActivity(Intent(this@NewReportActivity, ReportMergeActivity::class.java))
        }
    }

    override fun onReceived(data: Map<String, Any>?) {

        DatabaseUtils.uploadFileToStorage(ActBufferModel(
            data!!["№ БТС"].toString().toInt(),
            data["Адрес"].toString(),
            getPrice(job_type.selectedItemPosition, data),
            data["Раздел"].toString(),
            data["Район"].toString(),
            jobs[job_type.selectedItemPosition]
        ))

    }

    private fun getPrice(position: Int, data: Map<String, Any>): Double{
        return when(position){
            0 -> data["Ревизия"].toString().toDouble()
            1 -> data["ТО"].toString().toDouble()
            2 -> data["ТО"].toString().toDouble() + data["Геодезия"].toString().toDouble()
            3 -> data["Геодезия"].toString().toDouble()
            4 -> data["Геодезия"].toString().toDouble() + data["Ревизия"].toString().toDouble()
            else -> 0.0
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.PERMISSION_REQUEST_CODE) {
            userInterfaceInit()
           // ExcelUtils().generateFirstReport(ExcelUtils().createWorkbook("rep.xls")!!)
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPermissions(vararg permissions: String) = ActivityCompat.requestPermissions(this, permissions, Constants.PERMISSION_REQUEST_CODE)

}
