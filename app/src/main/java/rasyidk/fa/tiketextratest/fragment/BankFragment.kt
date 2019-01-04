package rasyidk.fa.tiketextratest.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_bank.*
import kotlinx.android.synthetic.main.fragment_bank.view.*
import rasyidk.fa.tiketextratest.R
import rasyidk.fa.tiketextratest.adapter.BankAdapter
import rasyidk.fa.tiketextratest.core.rest.APIs
import rasyidk.fa.tiketextratest.core.rest.RestRepository
import rasyidk.fa.tiketextratest.model.Bank
import java.util.ArrayList

class BankFragment: Fragment() {

    lateinit var mApis: APIs
    var list = ArrayList<Bank>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_bank, container, false)

        Log.d("sampai", "home")
        (context as AppCompatActivity).supportActionBar?.title = "Pembayaran"
        mApis = RestRepository(context!!)

        mApis.getBank()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("sampai", "cekk")
                    view.rv_bank.layoutManager = LinearLayoutManager(context)
                    view.rv_bank.adapter = BankAdapter(context, it)
                },{

                })



        return view
    }



}