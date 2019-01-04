package rasyidk.fa.tiketextratest.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import kotlinx.android.synthetic.main.fragment_kereta.view.*
import kotlinx.android.synthetic.main.item_kereta.view.*
import rasyidk.fa.tiketextratest.R
import rasyidk.fa.tiketextratest.fragment.BankFragment
import rasyidk.fa.tiketextratest.fragment.ListKeretaFragment
import rasyidk.fa.tiketextratest.model.Bank
import rasyidk.fa.tiketextratest.model.Kereta

class KeretaAdapter(private val context: Context?, private var items: ArrayList<Kereta.Go>) : RecyclerView.Adapter<KeretaAdapter.KeretaViewHolder>() {

    val ctx = context

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): KeretaAdapter.KeretaViewHolder {
        return KeretaViewHolder(LayoutInflater.from(context).inflate(R.layout.item_kereta, p0, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: KeretaAdapter.KeretaViewHolder, p1: Int) {
        Log.d("cek", "hasil ${items.size}")
        holder.view.txtNmStasiun.text = "${items[p1].trainName} ${items[p1].trainNo}"
        holder.view.txtKelas.text = "${items[p1].classCategory} - ${items[p1].code}"
        holder.view.txtWktDari.text = items[p1].departureTime
        holder.view.txtKdDari.text = items[p1].arrivalStationCode
        holder.view.txtWktTujuan.text = items[p1].arrivalTime
        holder.view.txtKdTujuan.text = items[p1].arrivalStationCode
        holder.view.txtHarga.text = "Rp ${items[p1].fare}"
        holder.view.txtKursi.text = "${items[p1].seatAvail} kursi tersedia"

        holder.bindItem()
    }

    inner class KeretaViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bindItem() {
            itemView.setOnClickListener {
                initFragment(BankFragment())
            }
        }
    }

    private fun initFragment(classFragment: Fragment) {
        val manager = (context as AppCompatActivity).supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.dashboard_main, classFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}