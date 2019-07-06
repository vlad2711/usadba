package com.vkram2711.usadba.utils

import android.net.Uri
import android.os.Environment
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.database.*
import com.vkram2711.usadba.callback.OnDataReceivedCallback
import com.vkram2711.usadba.models.Job
import java.util.*
import kotlin.collections.ArrayList
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import com.vkram2711.usadba.callback.OnTabelDownloaded
import com.vkram2711.usadba.callback.OnUserLogined
import com.vkram2711.usadba.models.ActBufferModel
import com.vkram2711.usadba.models.BufferReportModel
import com.vkram2711.usadba.models.User
import org.greenrobot.eventbus.EventBus
import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.Exception
import java.nio.charset.Charset
import kotlin.collections.HashMap


class DatabaseUtils {
    companion object {
        private val TAG = this::class.java.simpleName

        fun getBts(id: Int, onDataReceivedCallback: OnDataReceivedCallback) {
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("bts/bts")

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

        fun getJobs(type: String, onDataReceivedCallback: OnDataReceivedCallback) {
            Utils.jobs = arrayOf(ArrayList<Job>(), ArrayList<Job>(), ArrayList<Job>(), ArrayList<Job>(), ArrayList<Job>(),ArrayList<Job>())
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference(type)

            myRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    p0.toException().printStackTrace()
                    onDataReceivedCallback.onReceived(null)
                }

                override fun onDataChange(p0: DataSnapshot) {
                    val data = p0.child(type)

                    for (i in 0 until data.childrenCount) {
                        val item = data.child(i.toString()).value as Map<String, String>
                        Log.d(TAG, item.toString())
                        val job = Job(
                            item["№ п%2E%2Fп%2E"]!!,
                            item["Стоимость (руб%2E, без НДС)"]!!,
                            Utils.removeSpecialSymbols(item["Наименование работ"]!!),
                            null,
                            Utils.removeSpecialSymbols(item["Ед%2E изм"]!!) ,
                            Utils.getCategory(item["Раздел"]!!)
                        )

                        Utils.jobs[job.category].add(job)
                        Log.d(TAG, Utils.jobs.size.toString())
                    }
                    onDataReceivedCallback.onReceived(mapOf())
                    EventBus.getDefault().post("")
                }
            })
        }

        fun uploadFileToStorage(act: ActBufferModel){
            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.reference.child("buffer/${act.category}/${act.id}-${Utils.getDate()}.json")
            val database = FirebaseDatabase.getInstance()
            val reg = database.getReference("buffer/${act.category}")
            var updated = false
            reg.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    p0.toException().printStackTrace()
                }
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(!updated) {
                        val s = reg.child((snapshot.childrenCount).toString()).setValue("${act.id}-${Utils.getDate()}")
                        s.addOnFailureListener { it.printStackTrace() }
                        updated = true
                    }
                }
            })

            val uploadTask = storageRef.putBytes(Gson().toJson(act).toByteArray(Charset.forName("UTF-8")))
            uploadTask.addOnFailureListener {
                it.printStackTrace()
            }.addOnSuccessListener {
                Log.d(TAG, "success")
            }

        }

        fun getFiles(category: String, onDataReceivedCallback: OnDataReceivedCallback){
            val database = FirebaseDatabase.getInstance()
            val reg = database.getReference("buffer/$category")
            reg.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    p0.toException().printStackTrace()
                    onDataReceivedCallback.onReceived(null)
                }
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.value != null) {
                        val reports = snapshot.value as ArrayList<String>
                        Utils.reports = Array(reports.size) { null }
                        for (i in 0 until reports.size) {
                            Utils.reports[i] = BufferReportModel(reports[i])
                        }
                    } else{
                        Utils.reports = Array(0) {null
                        }
                    }
                    onDataReceivedCallback.onReceived(mapOf())
                }
            })
        }

        fun uploadFileToStorage(files: File, filePos: String){
            val file = Uri.fromFile(files)
            val storage = FirebaseStorage.getInstance().reference

            val riversRef = storage.child("$filePos/${file.lastPathSegment}")
            val uploadTask = riversRef.putFile(file)

            uploadTask.addOnFailureListener {
                it.printStackTrace()
            }.addOnSuccessListener {
                Log.d(TAG, "file uploaded")
            }

        }


        fun deleteFilesFromBuffer(district: String){
            for(i in 0 until Utils.reports.size){
                if(Utils.reports[i]!!.selected){
                    val storageRef = FirebaseStorage.getInstance().reference

                    val act = Utils.reports[i]
                    val desertRef = storageRef.child("buffer/$district/$act")

                    desertRef.delete().addOnSuccessListener {

                    }.addOnFailureListener {
                        it.printStackTrace()
                    }

                    val database = FirebaseDatabase.getInstance()
                    val reg = database.getReference("buffer/$district").child("$i").removeValue()
                    reg.addOnFailureListener {
                        it.printStackTrace()
                    }.addOnSuccessListener {
                        Log.d(TAG, "removed from database")
                    }
                }
            }
        }

        fun login(login: String, password: String, onUserLogined: OnUserLogined){
            val database = FirebaseDatabase.getInstance().getReference("users/users")

            database.addValueEventListener(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    p0.toException().printStackTrace()
                }

                override fun onDataChange(p0: DataSnapshot) {
                    Utils.users = ArrayList()
                    for(i in 0 until p0.childrenCount){
                        val value = p0.child("$i").value as HashMap<String, String>

                        Log.d(TAG, value.toString())
                            val user = User(value["Логин"]!!,
                                value["Пароль"]!!,
                                value["Разрешения"]!!,
                                value["ФИО"]!!)
                            Utils.users.add(user)

                            if (user.login == login && user.password == password) {
                                Utils.user = user
                                onUserLogined.onLogined(user)
                                return
                            }

                    }
                    onUserLogined.onLogined(null)

                }
            })
        }


        fun getTabel(onTabelDownloaded: OnTabelDownloaded){
            val storageRef = FirebaseStorage.getInstance().reference.child("users/${Utils.user.name}.xls")

            storageRef.getBytes(15000000).addOnSuccessListener {
                onTabelDownloaded.onDownloaded(ExcelUtils.bytesToWb(it))

            }.addOnFailureListener {
                it.printStackTrace()
                onTabelDownloaded.onDownloaded(ExcelUtils().createWorkbook(Utils.user.name)!!)
            }.addOnCanceledListener {
                onTabelDownloaded.onDownloaded(ExcelUtils().createWorkbook(Utils.user.name)!!)
            }
        }

        fun uploadTabel(){
            uploadFileToStorage(File(Environment.getExternalStorageDirectory().absolutePath + "/" + "reports", Utils.user.name + ".xls"), "users/${Utils.user.name}.xls" )
        }
    }
}