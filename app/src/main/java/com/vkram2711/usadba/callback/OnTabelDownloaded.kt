package com.vkram2711.usadba.callback

import jxl.write.WritableWorkbook

interface OnTabelDownloaded {
    fun onDownloaded(wb: WritableWorkbook)
}