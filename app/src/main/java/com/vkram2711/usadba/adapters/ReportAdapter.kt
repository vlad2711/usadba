package com.vkram2711.usadba.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vkram2711.usadba.R
import com.vkram2711.usadba.utils.Utils
import kotlinx.android.synthetic.main.report_item.view.*

class ReportAdapter(val category: Int): RecyclerView.Adapter<ReportAdapter.Holder>() {
    private final val TAG = this::class.java.canonicalName

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        return Holder(LayoutInflater.from(p0.context).inflate(R.layout.report_item, p0, false))
    }

    override fun getItemCount(): Int {
        return Utils.additionalJobs[category].size
    }

    override fun onBindViewHolder(p0: Holder, p1: Int)  = p0.bind(p1, category)

    class Holder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(position: Int, category: Int){
            itemView.title.text = Utils.additionalJobs[category][position].jobTitle
            itemView.count.text = Utils.additionalJobs[category][position].count + " " + Utils.additionalJobs[category][position].unit
            itemView.delete.setOnClickListener {
                Utils.additionalJobs[category].removeAt(position)
                //TODO: notify data set changed
            }
        }
    }
}