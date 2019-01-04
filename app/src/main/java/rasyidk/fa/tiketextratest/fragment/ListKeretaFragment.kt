package rasyidk.fa.tiketextratest.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_bank.view.*
import kotlinx.android.synthetic.main.fragment_listkereta.view.*
import rasyidk.fa.tiketextratest.R
import rasyidk.fa.tiketextratest.adapter.BankAdapter
import rasyidk.fa.tiketextratest.adapter.KeretaAdapter
import rasyidk.fa.tiketextratest.core.rest.APIs
import rasyidk.fa.tiketextratest.core.rest.RestRepository
import rasyidk.fa.tiketextratest.model.Kereta

class ListKeretaFragment: Fragment() {

    lateinit var mApis: APIs
    lateinit var mGo: ArrayList<Kereta.Go>
    lateinit var mBack: ArrayList<Kereta.Back>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_listkereta, container, false)

        (context as AppCompatActivity).supportActionBar?.title = "List Kereta"

        val idDari = arguments?.getString("dari")
        val idKe = arguments?.getString("ke")
        val idSdt = arguments?.getString("sdt")
        var idEdt = arguments?.getString("edt")
        var idBayi = arguments?.getString("bayi")
        val idDewasa = arguments?.getString("dewasa")
        var kembali = 0

        mApis = RestRepository(context!!)

        if (!idEdt?.isNullOrEmpty()!!) {
            kembali = 1
            idEdt = ""
        }

        if (idBayi?.isNullOrEmpty()!!) { idBayi = "0" }
        Log.d("cek", "hasill $idDari $idKe $idSdt $idEdt $idBayi $idDewasa $kembali")
        mApis.getKereta(idDari!!, idKe!!, idSdt!!, idEdt, idDewasa!!, idBayi!!, kembali.toString())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("cek", "hasil ${it.data.go.size}")
                    view.rv_kereta.layoutManager = LinearLayoutManager(context)
                    view.rv_kereta.adapter = KeretaAdapter(context, it.data.go)
                },{

                })

        return view
    }

}