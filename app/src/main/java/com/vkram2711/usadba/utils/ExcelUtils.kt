package com.vkram2711.usadba.utils

import android.os.Environment
import jxl.Workbook
import android.os.Environment.getExternalStorageDirectory
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.vkram2711.usadba.callback.OnActCreationFinished
import com.vkram2711.usadba.models.ActBufferModel
import com.vkram2711.usadba.models.BufferReportModel
import jxl.WorkbookSettings
import jxl.format.Alignment
import jxl.format.Border
import jxl.format.BorderLineStyle
import jxl.format.CellFormat
import jxl.write.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/*
 * Если кто-то читает это... Тут боль, только боль
 */

class ExcelUtils {
    private val TAG = this::class.java.name

    fun generateFirstReport(wb: WritableWorkbook, district: String, fileName: String){
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val sheet = wb.createSheet("Акт 1", 0)

        for (x in 0 until 30) {
            val cell = sheet.getColumnView(x)
            cell.size = 900
            cell.isAutosize = false
            sheet.setColumnView(x, cell)
        }


        for(i in 0 until 3) {
            val rowData = sheet.getRowView(25 + i)
            rowData.isAutosize = true
            sheet.setRowView(25 + i, rowData)
        }


        val wrap =  WritableCellFormat()
        wrap.wrap = true
        wrap.setFont(WritableFont(Font.ARIAL, 8))

        val alignmentRight = WritableCellFormat()
        alignmentRight.setFont(WritableFont(Font.ARIAL, 8))
        alignmentRight.alignment = Alignment.RIGHT
        sheet.addCell(Label(29,1, "Приложение 6", alignmentRight))
        sheet.addCell(Label(29,2, "к договору № D190098417   от 16.04.$year г.", alignmentRight))
        sheet.addCell(Label(29,3, "к Заказу № 1/8417-$year   от 06.05.$year г.", alignmentRight))

        sheet.addCell(Label(1,7, "Директор Департамента "))
        sheet.addCell(Label(1,8, "эксплуатации сети "))
        sheet.addCell(Label(1,9, "структурного подразделения "))
        sheet.addCell(Label(1,10, "\"КОПЫТА\""))

        sheet.addCell(Label(29,7, "Генеральный директор ", alignmentRight))
        sheet.addCell(Label(29,8, "ООО «РОМАШКА»", alignmentRight))


        sheet.addCell(Label(1,11, "___________________ ИВАНОВ О.А."))
        sheet.addCell(Label(1,13, "«_____»____________ $year г."))

        sheet.addCell(Label(29,11, "_______________  ПЕТРОВ И.Н.", alignmentRight))
        sheet.addCell(Label(29,13, "«_____»____________ $year г.", alignmentRight))

        val centerAlignment = WritableCellFormat()
        centerAlignment.alignment = Alignment.CENTRE
        sheet.addCell(Label(1,16, "АКТ № 00/Ю-$year-РЕВ", centerAlignment))
        sheet.addCell(Label(1,17, "ПРИЕМКИ-СДАЧИ ВЫПОЛНЕННЫХ РАБОТ", centerAlignment))
        sheet.addCell(Label(1,18, "к Заказу № 1/8417 от 06.05.$year г.", centerAlignment))
        sheet.addCell(Label(1,19, "за ИЮНЬ месяц $year года", centerAlignment))

        sheet.mergeCells(1, 16,25,16)
        sheet.mergeCells(1, 17,25,17)
        sheet.mergeCells(1, 18,25,18)
        sheet.mergeCells(1, 19,25,19)

        sheet.addCell(Label(1,21, "г. Москва"))
        sheet.addCell(Label(1,22, "Заказчик:"))
        sheet.mergeCells(4, 22,17,22)
        sheet.addCell(Label(5,22, "ПАО  «КОПЫТА»"))
        sheet.addCell(Label(1,23, "Подрядчик:"))
        sheet.mergeCells(4, 23,17,23)
        sheet.addCell(Label(5,23, "ООО «РОМАШКА»"))
        sheet.addCell(Label(1,24, "Договор:"))
        sheet.mergeCells(4, 24,17,24)
        sheet.addCell(Label(5,24, "№ D190108417  от 16.04.$year г."))
        sheet.addCell(Label(29,21, "«_____»____________ $year г.", alignmentRight))

        sheet.mergeCells(1,22,3,22)
        sheet.mergeCells(1,23,3,23)
        sheet.mergeCells(1,24,3,24)
        sheet.mergeCells(4,22,17,22)
        sheet.mergeCells(4,23,17,23)
        sheet.mergeCells(4,24,17,24)

        sheet.addCell(Label(1,26, "2.  В соответствии с требованиями Договора Подрядчиком представлены следующие документы: акты ТО АМС, фотоотчеты", wrap))
        sheet.addCell(Label(1,27, "3.  За выполненную работу в соответствии с разделами 4, 5 и 6 настоящего Договора Заказчик выплачивает Подрядчику стоимость работ:", wrap))
        sheet.addCell(Label(1,28, "3.1   За техническое обслуживание АО:", wrap))

        sheet.mergeCells(1, 25,26,25)
        sheet.mergeCells(1, 26,26,26)
        sheet.mergeCells(1, 27,26,27)
        sheet.mergeCells(1, 28,26,28)

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
        sheet.addCell(Label(1,47, "«_____»____________ $year г."))
        sheet.addCell(Label(29,40, "РАБОТУ ПРИНЯЛ:", alignmentRight))
        sheet.addCell(Label(29,41, "от Заказчика:", alignmentRight))
        sheet.addCell(Label(29,42, "Начальника отдела ЭРП ", alignmentRight))
        sheet.addCell(Label(29,44, "_________________ ИВАНОВ Н.К.", alignmentRight))
        sheet.addCell(Label(29,48, "«_____»____________ $year г.", alignmentRight))

        sheet.addCell(Label(29,50, "Приложение 1", alignmentRight))
        sheet.addCell(Label(29,51, "к Акту № 00/Ю-$year-РЕВ", alignmentRight))
        sheet.addCell(Label(29,52, "к Заказу № 1/8417-$year от 06.05.$year г.", alignmentRight))
        sheet.addCell(Label(29,53, "за июнь месяц $year года", alignmentRight))

        sheet.addCell(Label(20,55, "Генеральный директор "))
        sheet.addCell(Label(20,56, "ООО «РОМАШКА»"))

        sheet.addCell(Label(1,55, "Директор Департамента "))
        sheet.addCell(Label(1,56, "эксплуатации сети "))
        sheet.addCell(Label(1,57, "структурного подразделения "))
        sheet.addCell(Label(1,58, "«КОПЫТА»"))

        sheet.addCell(Label(1,60, "___________________ ИВАНОВ О.А."))
        sheet.addCell(Label(1,62, "«_____»____________ $year г."))
        sheet.addCell(Label(29,60, "_______________  ПЕТРОВ И.Н.", alignmentRight))
        sheet.addCell(Label(29,62, "«_____»____________ $year г.", alignmentRight))

        sheet.addCell(Label(1,64, "Заказчик:"))
        sheet.addCell(Label(1,65, "Подрядчик:"))
        sheet.addCell(Label(1,66, "Договор:"))
        sheet.addCell(Label(4,64, "\"КОПЫТА\""))
        sheet.addCell(Label(4,65, "ООО «РОМАШКА»"))
        sheet.addCell(Label(4,66, "№  D190098417 от 16.04.$year г."))

        sheet.addCell(Label(1,68, "РАСШИФРОВКА  СТОИМОСТИ", centerAlignment))
        sheet.addCell(Label(1,69, "к Акту № 00/Ю-$year-РЕВ приемки-сдачи выполненных работ", centerAlignment))
        sheet.addCell(Label(1,70, "По техническому обслуживанию и текущему ремонту", centerAlignment))
        sheet.addCell(Label(1,71, "за июнь месяц $year года", centerAlignment))

        sheet.mergeCells(1, 68,28,68)
        sheet.mergeCells(1, 69,28,69)
        sheet.mergeCells(1, 70,28,70)
        sheet.mergeCells(1, 71,28,71)

        val border = WritableCellFormat()
        border.setBorder(Border.ALL, BorderLineStyle.MEDIUM)
        border.setFont(WritableFont(Font.ARIAL, 8))
        border.wrap = true

        sheet.addCell(Label(1,73, "№ п/п", border))
        sheet.addCell(Label(2,73, "№ БС,  Адрес объектов ОАО МТС (виды работ)", border))
        sheet.addCell(Label(16,73, "Решение о приемке", border))
        sheet.addCell(Label(20,73, "Стоимость выполненных работ (без НДС) (в руб.)", border))
        sheet.addCell(Label(26,73, "Стоимость принятых работ  (без НДС)(в руб.)", border))
        sheet.addCell(Label(2,74, "ХХХХХХХХХХ район", border))

        sheet.mergeCells(2, 73, 15, 73)
        sheet.mergeCells(16, 73, 19, 73)
        sheet.mergeCells(20, 73, 25, 73)
        sheet.mergeCells(26, 73, 29, 73)

        sheet.mergeCells(1, 74, 29, 74)
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

        val reps = ArrayList<ActBufferModel>()
        val onActCreationFinished = object: OnActCreationFinished{
            override fun onFinished(wb: WritableWorkbook, queue: ArrayList<String>) {
                Log.d(TAG, queue.size.toString())
                if(queue.isEmpty()){
                    var ids = ""
                    for(i in 0 until bts.size){
                        if(i != 0) ids += ","
                        ids+= bts[i]
                    }
                    sum = String.format("%.2f", sum).replace(',', '.').replace("  ", "").toDouble()
                    sheet.addCell(Label(1,25, "1.  Согласно Методики приемки работ, предусмотренной п.4.1. Договора и приложением 5 к Договору проверено качество работ на $ids. \nКачество работ проверено полномочным представителем Заказчика в присутствии Подрядчика и соответствует требованиям и условиям Договора.", wrap))
                    sheet.addCell(Label(1, startRow, "ИТОГО ЗА ТО:", border))
                    sheet.addCell(Label(20, startRow, sum.toString(), border))
                    sheet.addCell(Label(26, startRow, sum.toString(), border))
                    sheet.mergeCells(20, startRow, 25, startRow + 1)
                    sheet.mergeCells(26, startRow, 29, startRow + 1)
                    sheet.mergeCells(1 , startRow,19, startRow + 1)

                    sheet.addCell(Label(10,29, sum.toString()))
                    sheet.addCell(Label(10,31, (sum*0.2).toString()))
                    sheet.addCell(Label(10,33, (sum + sum*0.2).toString()))

                    startRow += 3

                    sheet.addCell(Label(1, startRow, "РАБОТУ СДАЛ:"))
                    sheet.addCell(Label(1, startRow + 1, "от Подрядчика:"))
                    sheet.addCell(Label(1, startRow + 2, "Технический директор"))
                    sheet.addCell(Label(1, startRow + 4, "_____________________ Логинов В.А"))
                    sheet.addCell(Label(1, startRow + 8, "«_____»____________ $year г."))

                    sheet.addCell(Label(29, startRow, "РАБОТУ ПРИНЯЛ:", alignmentRight))
                    sheet.addCell(Label(29, startRow + 1, "от Заказчика:", alignmentRight))
                    sheet.addCell(Label(29, startRow + 2, "Начальник отдела ЭРП", alignmentRight))
                    sheet.addCell(Label(29, startRow + 4, "_____________________ Кузин Н.К", alignmentRight))
                    sheet.addCell(Label(29, startRow + 8, "«_____»____________ $year г.", alignmentRight))

                    generateAdditional(wb, reps, sum, startRow)

                    DatabaseUtils.deleteFilesFromBuffer(district)
                    wb.write()
                    wb.close()

                    DatabaseUtils.uploadFileToStorage(File(getExternalStorageDirectory().absolutePath + "/" + "reports", fileName), "Акт 1/$district" )
                }
            }
        }


        for(i in 0 until Utils.reports.size){
            if(Utils.reports[i]!!.selected){
                queue.add(Utils.reports[i]!!.name)
                val storageRef = FirebaseStorage.getInstance().reference.child("buffer/$district/${Utils.reports[i]!!.name}.json")

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
                    reps.add(model)

                    onActCreationFinished.onFinished(wb, queue)

                }.addOnFailureListener {
                    it.printStackTrace()
                }

            }
        }
    }


    fun generateAdditional(wb: WritableWorkbook, reps: ArrayList<ActBufferModel>, sum: Double, endRow: Int){
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val sheet = wb.getSheet(0)

        for (x in 0 until 30) {
            val cell = sheet.getColumnView(x)
            cell.size = 900
            cell.isAutosize = false
            sheet.setColumnView(x, cell)
        }

        val alignmentRight = WritableCellFormat()
        alignmentRight.setFont(WritableFont(Font.ARIAL, 8))
        alignmentRight.alignment = Alignment.RIGHT

        val centerAlignment = WritableCellFormat()
        centerAlignment.alignment = Alignment.CENTRE


        val border = WritableCellFormat()
        border.setBorder(Border.ALL, BorderLineStyle.MEDIUM)
        border.setFont(WritableFont(Font.ARIAL, 8))
        border.wrap = true

        sheet.addCell(Label(29,1 + endRow, "Приложение 2", alignmentRight))
        sheet.addCell(Label(29,2 + endRow, "к Акту № 00/Ю-$year-РЕВ", alignmentRight))
        sheet.addCell(Label(29,3 + endRow, "к Заказу № 1/8417-$year   от 06.05.$year г.", alignmentRight))
        sheet.addCell(Label(29,4 + endRow, "за июнь месяц 2019 года", alignmentRight))


        sheet.addCell(Label(29,7 + endRow, "Генеральный директор ", alignmentRight))
        sheet.addCell(Label(29,8 + endRow, "ООО «РОМАШКА»", alignmentRight))


        sheet.addCell(Label(1,11 + endRow, "___________________ ИВАНОВ О.А."))
        sheet.addCell(Label(1,13 + endRow, "«_____»____________ $year г."))

        sheet.addCell(Label(29,11 + endRow, "_______________  ПЕТРОВ И.Н.", alignmentRight))
        sheet.addCell(Label(29,13 + endRow, "«_____»____________ $year г.", alignmentRight))

        sheet.addCell(Label(1,7 + endRow, "Директор Департамента "))
        sheet.addCell(Label(1,8 + endRow, "эксплуатации сети "))
        sheet.addCell(Label(1,9 + endRow, "структурного подразделения "))
        sheet.addCell(Label(1,10 + endRow, "\"КОПЫТА\""))

        sheet.addCell(Label(1,22 + endRow, "Заказчик:"))
        sheet.mergeCells(4, 22 + endRow,17,22 + endRow)
        sheet.addCell(Label(5,22 + endRow, "ПАО  «КОПЫТА»"))
        sheet.addCell(Label(1,23 + endRow, "Подрядчик:"))
        sheet.mergeCells(4, 23 + endRow,17,23 + endRow)
        sheet.addCell(Label(5,23 + endRow, "ООО «РОМАШКА»"))
        sheet.addCell(Label(1,24 + endRow, "Договор:"))
        sheet.mergeCells(4, 24 + endRow,17,24 + endRow)
        sheet.addCell(Label(5,24 + endRow, "№ D190108417  от 16.04.$year г."))

        sheet.addCell(Label(1,26 + endRow, "ДОПОЛНЕНИЕ", centerAlignment))
        sheet.addCell(Label(1,27 + endRow, "к Акту № 00/Ю-$year-РЕВ приемки-сдачи выполненных работ", centerAlignment))
        sheet.addCell(Label(1,28 + endRow, "По техническому обслуживанию и текущему ремонту", centerAlignment))
        sheet.addCell(Label(1,29 + endRow, "за июнь месяц $year года", centerAlignment))

        sheet.mergeCells(1, 26 + endRow,29,26 + endRow)
        sheet.mergeCells(1, 27 + endRow,29,27 + endRow)
        sheet.mergeCells(1, 28 + endRow,29,28 + endRow)
        sheet.mergeCells(1, 29 + endRow,29,29 + endRow)

        sheet.addCell(Label(1,31 + endRow, "", border))
        sheet.addCell(Label(2,31 + endRow, "№ БС,  Адрес объектов ПАО МТС, проверенных выборочным контролем", border))
        sheet.addCell(Label(13,31 + endRow, "№ и дата чек-листа", border))
        sheet.addCell(Label(16,31 + endRow, "Суммарная оценка по результатам проверки объекта", border))
        sheet.addCell(Label(21,31 + endRow, "Решение о приемке согласно Прил.5 к Договору", border))
        sheet.addCell(Label(2,32 + endRow, "ХХХХХХХХХХ район", border))

        sheet.mergeCells(2,31 + endRow, 12,31 + endRow)
        sheet.mergeCells(13,31 + endRow, 15,31 + endRow)
        sheet.mergeCells(16,31 + endRow, 20,31 + endRow)
        sheet.mergeCells(21,31 + endRow, 23,31 + endRow)
        sheet.mergeCells(1, 32 + endRow, 23, 32 + endRow)
        var startRow = 33
        for(i in 0 until reps.size) {
            val model = reps[i]
            sheet.addCell(Label(1, startRow + endRow, (i+1).toString(), border))
            sheet.addCell(Label(2, startRow + endRow, "БС-${model.id}", border))
            sheet.addCell(Label(4, startRow + endRow, model.address + "," + model.region, border))

            sheet.addCell(Label(13, startRow + endRow, "", border))
            sheet.addCell(Label(14, startRow + endRow, "", border))
            sheet.addCell(Label(15, startRow + endRow, "", border))

            sheet.addCell(Label(16, startRow + endRow, "1.0", border))
            sheet.addCell(Label(17, startRow + endRow, "", border))
            sheet.addCell(Label(18, startRow + endRow, "", border))
            sheet.addCell(Label(19, startRow + endRow, "", border))
            sheet.addCell(Label(20, startRow + endRow, "", border))

            sheet.addCell(Label(21, startRow + endRow, "1.0", border))
            sheet.addCell(Label(22, startRow + endRow, "", border))
            sheet.addCell(Label(23, startRow + endRow, "", border))


            sheet.addCell(Label(1, startRow + endRow + 1, "", border))
            sheet.addCell(Label(2, startRow + endRow + 1, model.job, border))

            sheet.mergeCells(2, startRow + endRow, 3, startRow + endRow)
            sheet.mergeCells(4, startRow + endRow, 12, startRow + endRow)
            sheet.mergeCells(2, startRow + endRow + 1, 12, startRow  + endRow+ 1)
            for(j in 13 until 24) {
                sheet.addCell(Label(j, startRow  + endRow+ 1, "", border))
            }

            startRow += 2
        }

        val borderRight = WritableCellFormat()
        borderRight.setBorder(Border.ALL, BorderLineStyle.MEDIUM)
        borderRight.setFont(WritableFont(Font.ARIAL, 8))
        borderRight.wrap = true
        borderRight.alignment = Alignment.RIGHT
        sheet.addCell(Label(1, startRow + endRow, "Усредненная оценка:", borderRight))
        sheet.mergeCells(1, startRow + endRow, 15, startRow)

        sheet.addCell(Label(16, startRow + endRow, "1.00", border))
        sheet.addCell(Label(21, startRow + endRow, "", border))
        sheet.mergeCells(16, startRow + endRow, 20, startRow + endRow)
        sheet.mergeCells(21, startRow + endRow, 23, startRow + endRow)


        sheet.addCell(Label(1, startRow + endRow + 2, "По Акту № 00/Ю-$year-РЕВ приемки-сдачи выполнених работ за июнь месяц 2019 года стоимость выполненных рвбот составляет:"))
        sheet.mergeCells(1, startRow + endRow + 2, 28, startRow + endRow + 2)

        sheet.addCell(Label(1, startRow + endRow + 4, "Сумма:", alignmentRight))
        sheet.addCell(Label(1, startRow + endRow + 6, "плюс НДС 20% в сумме:", alignmentRight))
        sheet.addCell(Label(1, startRow + endRow + 8, "итого с учетом НДС 20% в сумме:", alignmentRight))
        sheet.mergeCells(1, startRow + endRow + 4, 9, startRow + endRow + 4)
        sheet.mergeCells(1, startRow + endRow + 6, 9, startRow + endRow + 6)
        sheet.mergeCells(1, startRow + endRow + 8, 9, startRow + endRow + 8)

        sheet.addCell(Label(10, startRow + endRow + 4, sum.toString()))
        sheet.addCell(Label(10, startRow + endRow + 6, (sum*0.2).toString()))
        sheet.addCell(Label(10, startRow + endRow + 8, (sum*1.2).toString()))


        sheet.addCell(Label(1, startRow + endRow + 10, "1. Качество работ проверено полномочным представителем Заказчика в присутствии представителей Подрядчика в соответствии с критериями оценки выполненных работ по обслуживанию АО."))
        sheet.mergeCells(1, startRow + endRow + 10, 26, startRow + endRow + 10)


        sheet.addCell(Label(1, startRow + endRow + 12, "2. В соответствии с требованиями п 4.1 Договора Заказчиком заполнены прилагаемые Чек-листы №"))
        sheet.mergeCells(1, startRow + endRow + 12, 26, startRow + endRow + 12)

        sheet.addCell(Label(1, startRow + endRow + 14, "Согласно п 4.1 Договра стоимость не принятых у Подрядчика работ за июнь месяц $year года составляет: "))
        sheet.mergeCells(1, startRow + endRow + 14, 26, startRow + endRow + 14)

        sheet.addCell(Label(1,startRow + endRow + 16, "\"Сумма – ____________ (_____________________________________________) рублей,\n" +
                "плюс НДС 20% в сумме – ____________ (_______________________________) рублей,\n" +
                "Итого с учетом НДС 20% сумма – ____________ (________________________) рублей.\""))

        sheet.mergeCells(1, startRow + endRow + 16, 26, startRow + 16)


        sheet.addCell(Label(8, startRow + endRow + 18, "Сумма:", alignmentRight))
        sheet.addCell(Label(8, startRow + endRow + 20, "плюс НДС 20% в сумме:", alignmentRight))
        sheet.addCell(Label(8, startRow + endRow + 22, "итого с учетом НДС 20% в сумме:", alignmentRight))
        sheet.mergeCells(8, startRow + endRow + 16, 16, startRow + endRow + 16)
        sheet.mergeCells(8, startRow + endRow + 20, 16, startRow + endRow + 20)
        sheet.mergeCells(8, startRow + endRow + 22, 16, startRow + endRow + 22)

        sheet.addCell(Label(17, startRow + endRow + 16, sum.toString()))
        sheet.addCell(Label(17, startRow + endRow + 20, (sum*0.2).toString()))
        sheet.addCell(Label(17, startRow + endRow + 22, (sum*1.2).toString()))

        sheet.mergeCells(17, startRow + endRow + 16, 21, startRow + endRow + 16)
        sheet.mergeCells(17, startRow + endRow + 20, 21, startRow + endRow + 20)
        sheet.mergeCells(17, startRow + endRow + 22, 21, startRow + endRow + 22)

        startRow += 28

        sheet.addCell(Label(1, startRow + endRow, "РАБОТУ СДАЛ:"))
        sheet.addCell(Label(1, startRow + endRow + 1, "от Подрядчика:"))
        sheet.addCell(Label(1, startRow + endRow + 2, "Технический директор"))
        sheet.addCell(Label(1, startRow + endRow + 4, "_____________________ Логинов В.А"))
        sheet.addCell(Label(1, startRow + endRow + 8, "«_____»____________ $year г."))

        sheet.addCell(Label(29, startRow + endRow, "РАБОТУ ПРИНЯЛ:", alignmentRight))
        sheet.addCell(Label(29, startRow + endRow + 1, "от Заказчика:", alignmentRight))
        sheet.addCell(Label(29, startRow + endRow + 2, "Начальник отдела ЭРП", alignmentRight))
        sheet.addCell(Label(29, startRow + endRow + 4, "_____________________ Кузин Н.К", alignmentRight))
        sheet.addCell(Label(29, startRow + endRow + 8, "«_____»____________ $year г.", alignmentRight))

    }

    fun generateSecondReport(wb: WritableWorkbook, fileName: String, district: String){
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

                sheet.addCell(Label(0, j + 1, Utils.bts["№ БТС"].toString()))
                sheet.addCell(Label(
                        1,
                        i + 1,
                        Utils.removeSpecialSymbols(Utils.bts["Адрес"].toString()) + ", " + Utils.removeSpecialSymbols(
                            Utils.bts["Район"].toString()
                        )
                    )
                )

                sheet.addCell(Label(2, j + 1, Utils.removeSpecialSymbols(Utils.bts["Тип АО"].toString())))
                sheet.addCell(Label(3, j + 1, Utils.additionalJobs[i][j].jobTitle))
                sheet.addCell(Label(4, j + 1, Utils.additionalJobs[i][j].unit))
                sheet.addCell(Label(5, j + 1, Utils.additionalJobs[i][j].count!!.toString()))
                sheet.addCell(Label(6, j + 1, Utils.additionalJobs[i][j].price))
                sheet.addCell(
                    Label(
                        7,
                        j + 1,
                        (Utils.additionalJobs[i][j].price.replace(',', '.').replace(" ", "").toDouble() * Utils.additionalJobs[i][j].count!!).toString()
                    )
                )
            }
        }

        DatabaseUtils.uploadFileToStorage(File(getExternalStorageDirectory().absolutePath + "/" + "reports", fileName), "Акт 2/$district" )


        wb.write()
        wb.close()
    }



    fun createWorkbook(fileName: String): WritableWorkbook?  {
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

    fun generateTabel(wb: WritableWorkbook){
        val sheet = wb.createSheet("Табель", 0)

        val border = WritableCellFormat()
        border.setBorder(Border.ALL, BorderLineStyle.MEDIUM)
        border.setFont(WritableFont(Font.ARIAL, 8))
        border.wrap = true

        sheet.addCell(Label(0, 4, "Дата", border))
        sheet.addCell(Label(1, 4, "ФИО бригадира", border))
        sheet.addCell(Label(6, 4, "ФИО монтажника", border))
        sheet.addCell(Label(11, 4, "Адрес", border))
        sheet.addCell(Label(16, 4, "Тип работ", border))
        sheet.addCell(Label(27, 4, "Единица измерения", border))
        sheet.addCell(Label(28, 4, "Кол-во", border))

        sheet.mergeCells(1, 4, 5, 4)
        sheet.mergeCells(6, 4, 10, 4)
        sheet.mergeCells(11, 4, 15, 4)
        sheet.mergeCells(16, 4, 26, 4)

        val date = SimpleDateFormat("dd/MM/YYYY").format(Date())
        val fioBrig = Utils.user.name
        val fioMon = Utils.partner!!.name

        var startRow = 5
        for(i in 0 until Utils.additionalJobs.size){
            for(j in 0 until Utils.additionalJobs[i].size){
                sheet.addCell(Label(0, startRow, date, border))
                sheet.addCell(Label(1, startRow, fioBrig, border))
                sheet.addCell(Label(6, startRow, fioMon, border))
                sheet.addCell(Label(11, startRow,  Utils.removeSpecialSymbols(Utils.bts["Адрес"].toString()) + ", " + Utils.removeSpecialSymbols(Utils.bts["Район"].toString()), border))
                sheet.addCell(Label(16, startRow, Utils.additionalJobs[i][j].jobTitle, border))
                sheet.addCell(Label(27, startRow, Utils.additionalJobs[i][j].unit, border))
                sheet.addCell(Label(28, startRow, Utils.additionalJobs[i][j].number, border))

                sheet.mergeCells(1, startRow, 5, startRow)
                sheet.mergeCells(6, startRow, 10, startRow)
                sheet.mergeCells(11, startRow, 15, startRow)
                sheet.mergeCells(16, startRow, 26, startRow)
                startRow++
            }
        }

        wb.write()
        wb.close()

        DatabaseUtils.uploadTabel()
    }

    companion object{
        fun bytesToWb(bytes: ByteArray): WritableWorkbook{
            val file  = File(getExternalStorageDirectory(), "reports/${Utils.user.name}.xls")

            if (file.exists()) {
                file.delete()
            }
            val wbSettings = WorkbookSettings()
            wbSettings.useTemporaryFileDuringWrite = true

            val fos= FileOutputStream(file.path)

            fos.write(bytes)
            fos.close()
            return Workbook.createWorkbook(file, wbSettings)
        }
    }
}
