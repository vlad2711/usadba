package com.vkram2711.usadba.callback

import com.vkram2711.usadba.models.User

interface OnUserLogined {
    fun onLogined(user: User?)
}