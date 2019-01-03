package rasyidk.fa.tiketextratest.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_bank.view.*
import rasyidk.fa.tiketextratest.R
import rasyidk.fa.tiketextratest.model.Bank

class BankAdapter(private val context: Context?, private var items: ArrayList<Bank>): RecyclerView.Adapter<BankAdapter.BankViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankAdapter.BankViewHolder {
        return BankViewHolder(LayoutInflater.from(context).inflate(R.layout.item_bank, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BankAdapter.BankViewHolder, position: Int) {
        holder.view.txtCode.text = items[position].bank_code
        holder.view.txtNmBank.text = items[position].bank_name
    }

    class BankViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bindItem() {

        }
    }
}