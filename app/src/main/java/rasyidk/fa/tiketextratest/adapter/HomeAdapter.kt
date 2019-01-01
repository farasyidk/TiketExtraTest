package rasyidk.fa.tiketextratest.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import rasyidk.fa.tiketextratest.fragment.BankFragment
import rasyidk.fa.tiketextratest.fragment.KeretaFragment

class HomeAdapterfm(fm: FragmentManager?, private var total: Int) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> {
                BankFragment()
            }
            1 -> KeretaFragment()
            else -> null
        }
    }

    override fun getCount(): Int {
        return total
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Bank"
            1 -> "Kereta"
            else -> null
        }
    }
}