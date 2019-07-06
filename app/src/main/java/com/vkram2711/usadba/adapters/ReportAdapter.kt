package com.vkram2711.usadba.adapters

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vkram2711.usadba.R
import com.vkram2711.usadba.utils.Utils
import kotlinx.android.synthetic.main.report_item.view.*

class ReportAdapter(val category: Int): RecyclerView.Adapter<ReportAdapter.Holder>() {
    private final val TAG = this::class.java.canonicalName

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        return Holder(LayoutInflater.from(p0.context).inflate(R.layout.report_item, p0, false))
    }

    override fun getItemCount(): Int {
        return Utils.jobs[category].size
    }

    override fun onBindViewHolder(p0: Holder, p1: Int)  = p0.bind(p1, category)

    class Holder(view: View): RecyclerView.ViewHolder(view) {
        private val TAG = this::class.java.canonicalName
        fun bind(position: Int, category: Int){
            itemView.title.text = Utils.jobs[category][position].jobTitle
            val count = Utils.jobs[category][position].count
            itemView.count.setText(count?.toString() ?: "0")
            itemView.count.addTextChangedListener(object : TextWatcher{
                override fun afterTextChanged(p0: Editable?) {
                    if(p0.toString() != "") {
                        Utils.jobs[category][position].count = Integer.parseInt(p0.toString())
                        Log.d(TAG, category.toString() + " " + position + " " + Utils.jobs[category][position].count)
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

            })
            itemView.count.onFocusChangeListener =  View.OnFocusChangeListener { p0, p1 -> itemView.count.setSelection(itemView.count.text.length) }
        }
    }
}