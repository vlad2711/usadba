package com.vkram2711.usadba.utils

import android.util.Log
import com.google.firebase.database.*
import com.vkram2711.usadba.callback.OnDataReceivedCallback
import com.vkram2711.usadba.models.Job
import java.util.*
import kotlin.collections.ArrayList


class DatabaseUtils {
    companion object {
        private val TAG = this::class.java.simpleName

        fun getBts(id: Int, onDataReceivedCallback: OnDataReceivedCallback) {
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("bts")

            myRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    p0.toException().printStackTrace()
                }

                override fun onDataChange(snapshot: DataSnapshot) {

                    val map = snapshot.value as ArrayList<Any>

                    Log.d(TAG, map.toString())
                    for (i in 0 until map.size) {
                        
                        val currentObject = map[i] as Map<String, Any>?
                        if (currentObject != null) {
                            if (Objects.equals(currentObject["№ БТС"].toString(), id.toString())) {
                                onDataReceivedCallback.onReceived(currentObject)
                                Utils.bts = currentObject
                                return
                            }
                        }
                    }

                    onDataReceivedCallback.onReceived(null)
                }
            })
        }

        fun getJobs(type: String) {
            Utils.jobs = arrayOf(ArrayList<Job>(), ArrayList<Job>(), ArrayList<Job>(), ArrayList<Job>(), ArrayList<Job>(),ArrayList<Job>())
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference(type)

            myRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    p0.toException().printStackTrace()
                }


                override fun onDataChange(p0: DataSnapshot) {
                    val data = p0.child(type)

                    System.out.println(data)
                    for (i in 0 until data.childrenCount) {
                        val item = data.child(i.toString()).value as Map<String, String>
                        Log.d(TAG, item.toString())
                        val job = Job(
                            item["№ п%2E%2Fп%2E"]!!,
                            item["Стоимость (руб%2E, без НДС)"]!!,
                            Utils.removeSpecialSymbols(item["Наименование работ"]!!),
                            item["Количество"]!!,
                            Utils.removeSpecialSymbols(item["Ед%2E изм"]!!) ,
                            Utils.getCategory(item["Раздел"]!!)
                        )

                        Utils.jobs[job.category].add(job)
                        Log.d(TAG, Utils.jobs.size.toString())
                    }

                }
            })
        }
    }
}