package rasyidk.fa.tiketextratest

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import rasyidk.fa.tiketextratest.fragment.HomeFragment
import rasyidk.fa.tiketextratest.fragment.ProfileFragment
import rasyidk.fa.tiketextratest.helper.UserSession

class MainActivity : AppCompatActivity() {

    private lateinit var session: UserSession
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                initFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                initFragment(ProfileFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        session = UserSession(this)

        if (session.checkLogin()) {
            finish()
        }
        initFragment(HomeFragment())

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun initFragment(classFragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.dashboard_main, classFragment)
        transaction.commit()
    }
}