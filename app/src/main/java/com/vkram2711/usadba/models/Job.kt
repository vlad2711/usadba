package com.vkram2711.usadba.models

data class Job(val number: String,
               val price: String,
               val jobTitle: String,
               var count: Int?,
               val unit: String,
               val category: Int)