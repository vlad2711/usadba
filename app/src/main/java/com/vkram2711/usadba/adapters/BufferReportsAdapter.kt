package com.vkram2711.usadba.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vkram2711.usadba.R
import com.vkram2711.usadba.utils.Utils
import kotlinx.android.synthetic.main.buffer_report_item.view.*

class BufferReportsAdapter: RecyclerView.Adapter<BufferReportsAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.buffer_report_item, parent, false))
    }

    override fun getItemCount(): Int {
        return Utils.reports.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind(position)



    class Holder(view: View): RecyclerView.ViewHolder(view){
        fun bind(position: Int){
            itemView.report_name.text = Utils.reports[position]
        }
    }
}