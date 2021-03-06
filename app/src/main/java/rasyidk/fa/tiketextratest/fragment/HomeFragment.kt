package rasyidk.fa.tiketextratest.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import rasyidk.fa.tiketextratest.R
import rasyidk.fa.tiketextratest.adapter.HomeAdapterfm

class HomeFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val pager = view.viewpager_main
        val tabs = view.tabs_main

        tabs.addTab(tabs.newTab().setText("Bank"))
        tabs.addTab(tabs.newTab().setText("Kereta"))
        tabs.tabGravity = TabLayout.GRAVITY_FILL

        pager.adapter = HomeAdapterfm(activity?.supportFragmentManager, tabs.tabCount)
        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                pager.currentItem = tab.position
                val fm = activity?.supportFragmentManager
                val ft = fm?.beginTransaction()
                val count = fm?.backStackEntryCount
                if (count != null) {
                    if (count >= 1) {
                        activity?.supportFragmentManager?.popBackStack()
                    }
                }
                ft?.commit()
                Log.d("sampai", tab.position.toString())
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        return view
    }
}