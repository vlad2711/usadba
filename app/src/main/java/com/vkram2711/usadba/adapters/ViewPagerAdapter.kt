package com.vkram2711.usadba.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.vkram2711.usadba.fragments.ReportsFragment
import com.vkram2711.usadba.models.Job
import com.vkram2711.usadba.utils.Utils
import kotlinx.android.synthetic.main.activity_report.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ViewPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return Utils.additionalCategoriesSize.size
    }

    override fun getItem(position: Int): Fragment {
        return ReportsFragment.newInstance(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return Utils.getCategoryTitle(position)
    }

}