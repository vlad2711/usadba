package com.vkram2711.usadba.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vkram2711.usadba.R
import com.vkram2711.usadba.adapters.ReportAdapter
import com.vkram2711.usadba.models.Job
import com.vkram2711.usadba.utils.Utils
import kotlinx.android.synthetic.main.report_fragment.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ReportsFragment: Fragment() {
    private final val TAG = this::class.java.canonicalName
    companion object{
        fun newInstance(category: Int): ReportsFragment{
            val reportsFragment = ReportsFragment()
            val bundle = Bundle()
            bundle.putInt("category", category)
            reportsFragment.arguments = bundle
            return reportsFragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.report_fragment, container, false)
        userInterfaceInit(view, arguments!!.getInt("category"))
        return view
    }

    private fun userInterfaceInit(view: View, category: Int){
        view.reports.layoutManager = LinearLayoutManager(context)
        view.reports.adapter = ReportAdapter(category)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAdded(string: String) {
        if(view != null) view!!.reports.adapter!!.notifyDataSetChanged()
        Log.d(TAG, Utils.additionalJobs.size.toString())
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(!EventBus.getDefault().isRegistered(this))
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }
}