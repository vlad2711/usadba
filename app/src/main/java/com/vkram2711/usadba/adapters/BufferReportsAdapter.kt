package com.vkram2711.usadba.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.vkram2711.usadba.R
import com.vkram2711.usadba.utils.Utils
import kotlinx.android.synthetic.main.buffer_report_item.view.*
import android.graphics.Color


class BufferReportsAdapter: RecyclerView.Adapter<BufferReportsAdapter.Holder>() {
    private val TAG = this::class.java.canonicalName

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.buffer_report_item, parent, false))
    }

    override fun getItemCount(): Int {
        return Utils.reports.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }

    class Holder(view: View): RecyclerView.ViewHolder(view){
        fun bind(position: Int){
            itemView.report_name.text = Utils.reports[position]!!.name
            itemView.setOnClickListener {
                Utils.reports[position]!!.selected = !Utils.reports[position]!!.selected
                itemView.card_item.setBackgroundColor(if (Utils.reports[position]!!.selected) Color.MAGENTA else Color.WHITE )
            }
        }
    }
}
