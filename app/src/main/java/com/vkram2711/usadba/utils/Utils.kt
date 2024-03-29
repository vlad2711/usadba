package com.vkram2711.usadba.utils

import com.vkram2711.usadba.models.BufferReportModel
import com.vkram2711.usadba.models.Job
import com.vkram2711.usadba.models.User
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class Utils {
    companion object{
        var jobs = arrayOf(ArrayList<Job>(), ArrayList<Job>(), ArrayList<Job>(), ArrayList<Job>(), ArrayList<Job>(),ArrayList<Job>())
        var additionalJobs = arrayOf(ArrayList<Job>(), ArrayList<Job>(), ArrayList<Job>(), ArrayList<Job>(), ArrayList<Job>(),ArrayList<Job>())
        lateinit var reports: Array<BufferReportModel?>
        lateinit var bts: Map<String, Any>
        lateinit var users: ArrayList<User>
        lateinit var user: User
        var partner: User? = null

        fun removeSpecialSymbols(string: String): String{
            return string.replace("%2E", ".", false).replace("%2F", "/", false)
        }

        fun getCategory(string: String): Int{
            return when(string){
                "Благоустройство" -> 0
                "Контейнер" -> 1
                "Система СОМ" -> 2
                "АО" -> 3
                "Мачты" -> 4
                "Фидерный мост" -> 5
                else -> 6
            }
        }

        fun getCategoryTitle(category: Int): String{
            return when(category){
                 0-> "Благоустройство"
                 1-> "Контейнер"
                 2-> "Система СОМ"
                 3-> "АО"
                 4-> "Мачты"
                 5-> "Фидерный мост"

                else -> "РВР"
            }
        }

        fun getDate(): String{
            val c = Calendar.getInstance().time
            val df = SimpleDateFormat("dd-MMM-yyyy", Locale.ROOT)
            return df.format(c)
        }
    }
}