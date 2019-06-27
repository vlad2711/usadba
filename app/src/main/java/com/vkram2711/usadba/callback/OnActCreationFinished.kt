package com.vkram2711.usadba.callback

import jxl.write.WritableWorkbook

interface OnActCreationFinished {
    fun onFinished(wb: WritableWorkbook, queue: ArrayList<String>)
}