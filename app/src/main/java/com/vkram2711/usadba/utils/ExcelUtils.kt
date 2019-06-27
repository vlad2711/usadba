package com.vkram2711.usadba.utils

import android.os.Environment
import jxl.Workbook
import jxl.write.WritableWorkbook
import android.os.Environment.getExternalStorageDirectory
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.vkram2711.usadba.callback.OnActCreationFinished
import com.vkram2711.usadba.models.ActBufferModel
import jxl.WorkbookSettings
import jxl.format.Alignment
import jxl.format.Border
import jxl.format.BorderLineStyle
import jxl.format.CellFormat
import jxl.write.Label
import jxl.write.WritableCellFormat
import jxl.write.WritableSheet
import java.io.File
import java.io.IOException


class ExcelUtils {
    private val TAG = this::class.java.name

    fun generateFirstReport(wb: WritableWorkbook){
        val sheet = wb.createSheet("Акт 1", 0)

        for (x in 0 until 30) {
            val cell = sheet.getColumnView(x)
            cell.size = 900
            sheet.setColumnView(x, cell)
        }



        sheet.addCell(Label(19,1, "Приложение 6"))
        sheet.addCell(Label(19,2, "к договору № D190098417   от 16.04.2019 г."))
        sheet.addCell(Label(19,3, "к Заказу № 1/8417-2019   от 06.05.2019 г."))

        sheet.addCell(Label(1,7, "Директор Департамента "))
        sheet.addCell(Label(1,8, "эксплуатации сети "))
        sheet.addCell(Label(1,9, "структурного подразделения "))
        sheet.addCell(Label(1,10, "\"КОПЫТА\""))

        sheet.addCell(Label(19,7, "Генеральный директор "))
        sheet.addCell(Label(20,8, "ООО «РОМАШКА»"))


        sheet.addCell(Label(1,11, "___________________ ИВАНОВ О.А."))
        sheet.addCell(Label(1,13, "«_____»____________ 2019 г."))

        sheet.addCell(Label(19,11, "_______________  ПЕТРОВ И.Н."))
        sheet.addCell(Label(19,13, "«_____»____________ 2019 г."))

        val centerAlignment = WritableCellFormat()
        centerAlignment.alignment = Alignment.CENTRE
        sheet.addCell(Label(1,16, "АКТ № 00/Ю-2019-РЕВ", centerAlignment))
        sheet.addCell(Label(1,17, "ПРИЕМКИ-СДАЧИ ВЫПОЛНЕННЫХ РАБОТ", centerAlignment))
        sheet.addCell(Label(1,18, "к Заказу № 1/8417 от 06.05.2019 г.", centerAlignment))
        sheet.addCell(Label(1,19, "за ИЮНЬ месяц 2019 года", centerAlignment))

        sheet.mergeCells(1, 16,25,16)
        sheet.mergeCells(1, 17,25,17)
        sheet.mergeCells(1, 18,25,18)
        sheet.mergeCells(1, 19,25,19)

        sheet.addCell(Label(1,21, "г. Москва"))
        sheet.addCell(Label(1,22, "Заказчик:"))
        sheet.mergeCells(4, 22,17,22)
        sheet.addCell(Label(2,22, "ПАО  «КОПЫТА»"))
        sheet.addCell(Label(1,23, "Подрядчик:"))
        sheet.mergeCells(4, 23,17,23)
        sheet.addCell(Label(2,23, "ООО «РОМАШКА»"))
        sheet.addCell(Label(1,24, "Договор:"))
        sheet.mergeCells(4, 24,17,24)
        sheet.addCell(Label(2,24, "№ D190108417  от 16.04.2019 г."))
        sheet.addCell(Label(20,21, "«_____»____________ 2019 г."))

        sheet.mergeCells(1,22,3,22)
        sheet.mergeCells(1,23,3,23)
        sheet.mergeCells(1,24,3,24)
        sheet.mergeCells(4,22,17,22)
        sheet.mergeCells(4,23,17,23)
        sheet.mergeCells(4,24,17,24)

        sheet.addCell(Label(1,26, "2.  В соответствии с требованиями Договора Подрядчиком представлены следующие документы: акты ТО АМС, фотоотчеты"))
        sheet.addCell(Label(1,27, "3.  За выполненную работу в соответствии с разделами 4, 5 и 6 настоящего Договора Заказчик выплачивает Подрядчику стоимость работ:"))
        sheet.addCell(Label(1,28, "3.1   За техническое обслуживание АО:"))

        sheet.mergeCells(1, 25,26,25)
        sheet.mergeCells(1, 26,26,26)
        sheet.mergeCells(1, 27,26,27)
        sheet.mergeCells(1, 27,26,27)

        sheet.addCell(Label(1,29, "Сумма:"))
        sheet.addCell(Label(1,31, "плюс НДС 20% в сумме:"))
        sheet.addCell(Label(1,33, "Итого с учетом НДС 20% сумма:"))

        sheet.mergeCells(1, 29, 9, 29)
        sheet.mergeCells(1, 31, 9, 31)
        sheet.mergeCells(1, 33, 9, 33)

        sheet.addCell(Label(1,35, "3.2   За текущий ремонт АО:"))
        sheet.addCell(Label(1,36, "\"Сумма – ____________ (_____________________________________________) рублей,\n" +
                "плюс НДС 20% в сумме – ____________ (_______________________________) рублей,\n" +
                "Итого с учетом НДС 20% сумма – ____________ (________________________) рублей.\""))


        sheet.addCell(Label(1,40, "РАБОТУ СДАЛ:"))
        sheet.addCell(Label(1,41, "от Подрядчика:"))
        sheet.addCell(Label(1,42, "Технический директор"))
        sheet.addCell(Label(1,44, "_________________  Логинов В.А."))
        sheet.addCell(Label(1,47, "«_____»____________ 2019 г."))
        sheet.addCell(Label(20,40, "РАБОТУ ПРИНЯЛ:"))
        sheet.addCell(Label(20,41, "от Заказчика:"))
        sheet.addCell(Label(20,42, "Начальника отдела ЭРП "))
        sheet.addCell(Label(20,44, "_________________ ИВАНОВ Н.К."))
        sheet.addCell(Label(20,48, "«_____»____________ 2019 г."))

        sheet.addCell(Label(20,50, "Приложение 1"))
        sheet.addCell(Label(20,51, "к Акту № 00/Ю-2019-РЕВ"))
        sheet.addCell(Label(20,52, "к Заказу № 1/8417-2019 от 06.05.2019 г."))
        sheet.addCell(Label(20,53, "за июнь месяц 2019 года"))

        sheet.addCell(Label(20,55, "Генеральный директор "))
        sheet.addCell(Label(20,56, "ООО «РОМАШКА»"))

        sheet.addCell(Label(1,55, "Директор Департамента "))
        sheet.addCell(Label(1,56, "эксплуатации сети "))
        sheet.addCell(Label(1,57, "структурного подразделения "))
        sheet.addCell(Label(1,58, "«КОПЫТА»"))

        sheet.addCell(Label(1,60, "___________________ ИВАНОВ О.А."))
        sheet.addCell(Label(1,62, "«_____»____________ 2019 г."))
        sheet.addCell(Label(20,60, "_______________  ПЕТРОВ И.Н."))
        sheet.addCell(Label(20,62, "«_____»____________ 2019 г."))

        sheet.addCell(Label(1,64, "Заказчик:"))
        sheet.addCell(Label(1,65, "Подрядчик:"))
        sheet.addCell(Label(1,66, "Договор:"))
        sheet.addCell(Label(4,64, "\"КОПЫТА\""))
        sheet.addCell(Label(4,65, "ООО «РОМАШКА»"))
        sheet.addCell(Label(4,66, "№  D190098417 от 16.04.2019 г."))

        sheet.addCell(Label(1,68, "РАСШИФРОВКА  СТОИМОСТИ", centerAlignment))
        sheet.addCell(Label(1,69, "к Акту № 00/Ю-2019-РЕВ приемки-сдачи выполненных работ", centerAlignment))
        sheet.addCell(Label(1,70, "По техническому обслуживанию и текущему ремонту", centerAlignment))
        sheet.addCell(Label(1,71, "за июнь месяц 2019 года", centerAlignment))

        sheet.mergeCells(1, 68,25,68)
        sheet.mergeCells(1, 69,25,69)
        sheet.mergeCells(1, 70,25,70)
        sheet.mergeCells(1, 71,25,71)

        val border = WritableCellFormat()
        border.setBorder(Border.ALL, BorderLineStyle.MEDIUM)
        sheet.addCell(Label(1,73, "№ \nп/п", border))
        sheet.addCell(Label(2,73, "№ БС,  Адрес объектов ОАО МТС (виды работ)", border))
        sheet.addCell(Label(16,73, "Решение о приемке", border))
        sheet.addCell(Label(19,73, "Стоимость выполненных работ \n (без НДС) (в руб.)", border))
        sheet.addCell(Label(25,73, "Стоимость принятых работ \n (без НДС)(в руб.)", border))
        sheet.addCell(Label(2,74, "ХХХХХХХХХХ район", border))

        sheet.mergeCells(2, 73, 15, 73)
        sheet.mergeCells(16, 73, 18, 73)
        sheet.mergeCells(19, 73, 24, 73)
        sheet.mergeCells(25, 73, 27, 73)

        sheet.mergeCells(1, 74, 33, 74)
        sheet.mergeCells(16, 74, 18, 74)
        sheet.mergeCells(19, 74, 24, 74)
        sheet.mergeCells(25, 74, 27, 74)

        var startRow = 75
        var rowCount = 1

        val gson = Gson()
        sheet.addCell(Label(2, startRow, rowCount.toString()))

        val queue = ArrayList<String>()
        val bts = ArrayList<Int>()
        var sum = 0.0
        val onActCreationFinished = object: OnActCreationFinished{
            override fun onFinished(wb: WritableWorkbook, queue: ArrayList<String>) {
                Log.d(TAG, queue.size.toString())
                if(queue.isEmpty()){
                    var ids = ""
                    for(i in 0 until bts.size){
                        if(i != 0) ids += ","
                        ids+= bts[i]
                    }
                    val border = WritableCellFormat()
                    border.setBorder(Border.ALL, BorderLineStyle.MEDIUM)
                    sheet.addCell(Label(1,25, "1.  Согласно Методики приемки работ, предусмотренной п.4.1. Договора и приложением 5 к Договору проверено качество работ на $ids. \nКачество работ проверено полномочным представителем Заказчика в присутствии Подрядчика и соответствует требованиям и условиям Договора."))
                    sheet.addCell(Label(1, startRow, "ИТОГО ЗА ТО:", border))
                    sheet.addCell(Label(20, startRow, sum.toString(), border))
                    sheet.addCell(Label(26, startRow, sum.toString(), border))
                    sheet.mergeCells(20, startRow, 25, startRow + 1)
                    sheet.mergeCells(26, startRow, 29, startRow + 1)
                    sheet.mergeCells(1 , startRow,19, startRow + 1)

                    sheet.addCell(Label(10,29, sum.toString()))
                    sheet.addCell(Label(10,31, (sum*0.2).toString()))
                    sheet.addCell(Label(10,33, (sum + sum*0.2).toString()))

                    wb.write()
                    wb.close()
                }
            }

        }
        for(i in 0 until Utils.reports.size){
            if(Utils.reports[i]!!.selected){
                queue.add(Utils.reports[i]!!.name)
                val storageRef = FirebaseStorage.getInstance().reference.child("buffer/СЕВЕР/${Utils.reports[i]!!.name}.json")

                storageRef.getBytes(1024*1024).addOnSuccessListener {
                    val model = gson.fromJson(it.toString(Charsets.UTF_8), ActBufferModel::class.java)
                    Log.d(TAG, it.toString(Charsets.UTF_8))


                    sheet.addCell(Label(1, startRow, rowCount.toString(), border))
                    sheet.addCell(Label(2, startRow, "БС-${model.id}", border))
                    sheet.addCell(Label(4, startRow, model.address + "," + model.region, border))
                    sheet.addCell(Label(20, startRow, model.price.toString(), border))
                    sheet.addCell(Label(26, startRow, model.price.toString(), border))
                    sheet.addCell(Label(2, startRow + 1, model.job, border))
                    sheet.addCell(Label(16, startRow, "", border))
                    sheet.mergeCells(1, startRow, 1, startRow + 1)
                    sheet.mergeCells(2, startRow, 3, startRow)
                    sheet.mergeCells(4, startRow, 15, startRow)
                    sheet.mergeCells(16, startRow, 19, startRow + 1)
                    sheet.mergeCells(20, startRow, 25, startRow + 1)
                    sheet.mergeCells(26, startRow, 29, startRow + 1)
                    sheet.mergeCells(2, startRow + 1, 15, startRow + 1)
                    startRow += 2
                    queue.remove(Utils.reports[i]!!.name)
                    rowCount++

                    sum += model.price
                    bts.add(model.id)
                    onActCreationFinished.onFinished(wb, queue)

                }.addOnFailureListener {
                    it.printStackTrace()
                }

            }
        }



    }


    fun generateSecondReport(wb: WritableWorkbook){
        val sheet = wb.createSheet("Матрица ДопРабот", 0)

        sheet.addCell(Label(0, 0, "№ БС"))
        sheet.addCell(Label(1, 0, "Адрес"))
        sheet.addCell(Label(2, 0, "Тип АО"))
        sheet.addCell(Label(3, 0, "Виды работ"))
        sheet.addCell(Label(4, 0, "Единица измерения"))
        sheet.addCell(Label(5, 0, "Кол-во"))
        sheet.addCell(Label(6, 0, "Цена за ед."))
        sheet.addCell(Label(7, 0, "Стоимость вып работ (без НДС в руб.)"))

        for (i in 0 until Utils.additionalJobs.size) {
            for(j in 0 until Utils.additionalJobs[i].size) {

                sheet.addCell(Label(0, i + 1, Utils.bts["№ БТС"].toString()))
                sheet.addCell(Label(
                        1,
                        i + 1,
                        Utils.removeSpecialSymbols(Utils.bts["Адрес"].toString()) + ", " + Utils.removeSpecialSymbols(
                            Utils.bts["Район"].toString()
                        )
                    )
                )

                sheet.addCell(Label(2, i + 1, Utils.removeSpecialSymbols(Utils.bts["Тип АО"].toString())))
                sheet.addCell(Label(3, i + 1, Utils.additionalJobs[i][j].jobTitle))
                sheet.addCell(Label(4, i + 1, Utils.additionalJobs[i][j].unit))
                sheet.addCell(Label(5, i + 1, Utils.additionalJobs[i][j].count))
                sheet.addCell(Label(6, i + 1, Utils.additionalJobs[i][j].price))
                sheet.addCell(
                    Label(
                        7,
                        i + 1,
                        (Utils.additionalJobs[i][j].price.replace(',', '.').replace(" ", "").toDouble() * Integer.parseInt(Utils.additionalJobs[i][j].count)).toString()
                    )
                )
            }
        }

        wb.write()
        wb.close()
    }



    fun createWorkbook(fileName: String): WritableWorkbook? {
        //exports must use a temp file while writing to avoid memory hogging
        val wbSettings = WorkbookSettings()
        wbSettings.useTemporaryFileDuringWrite = true
        val sdCard = getExternalStorageDirectory().absolutePath + "/" + "reports"
        val dir = File(sdCard)
        dir.mkdirs()
        val wbfile = File(dir, fileName)

        var wb: WritableWorkbook? = null

        try {
            wb = Workbook.createWorkbook(wbfile, wbSettings)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return wb
    }
}
