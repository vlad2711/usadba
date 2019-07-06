package com.vkram2711.usadba.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vkram2711.usadba.R
import com.vkram2711.usadba.callback.OnUserLogined
import com.vkram2711.usadba.models.User
import com.vkram2711.usadba.utils.DatabaseUtils
import com.vkram2711.usadba.utils.ExcelUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity(), OnUserLogined {
    private val TAG = MainActivity::class.java.canonicalName
    override fun onLogined(user: User?) {
        if(user != null) {
            startActivity(Intent(this, NewReportActivity::class.java))
            finish()
        } else{
            Toast.makeText(this, "Неправильный логин или пароль", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userInterfaceInit()

    }

    private fun userInterfaceInit(){
        login.setOnClickListener {
            DatabaseUtils.login(nick.text.toString(), password.text.toString(), this)
        }
    }
}