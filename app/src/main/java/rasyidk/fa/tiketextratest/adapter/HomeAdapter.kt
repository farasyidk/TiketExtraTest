package rasyidk.fa.tiketextratest.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import rasyidk.fa.tiketextratest.fragment.BankFragment
import rasyidk.fa.tiketextratest.fragment.KeretaFragment
import rasyidk.fa.tiketextratest.fragment.ProfileFragment

class HomeAdapterfm(fm: FragmentManager?, private var total: Int) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> {
                Log.d("sampai", "bankkkk")
                BankFragment()
            }
            1 -> {
                Log.d("sampai", "keretaa")
                KeretaFragment()
            }
            else -> {
                Log.d("sampai", "null")
                null
            }
        }
    }

    override fun getCount(): Int {
        return total
    }
}