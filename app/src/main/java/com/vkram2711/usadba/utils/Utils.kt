package com.vkram2711.usadba.utils

import com.vkram2711.usadba.models.Job

class Utils {
    companion object{
        var jobs = arrayOf(ArrayList<Job>(), ArrayList<Job>(), ArrayList<Job>(), ArrayList<Job>(), ArrayList<Job>(),ArrayList<Job>())
        var additionalJobs = arrayOf(ArrayList<Job>(), ArrayList<Job>(), ArrayList<Job>(), ArrayList<Job>(), ArrayList<Job>(),ArrayList<Job>())
        lateinit var bts: Map<String, Any>


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
                else -> 5
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
                else -> "Благоустройство"
            }
        }

    }
}