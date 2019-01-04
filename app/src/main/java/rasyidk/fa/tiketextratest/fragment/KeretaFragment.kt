package rasyidk.fa.tiketextratest.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import rasyidk.fa.tiketextratest.R
import android.widget.ArrayAdapter
import android.widget.DatePicker
import kotlinx.android.synthetic.main.fragment_kereta.view.*
import java.text.SimpleDateFormat
import java.util.*
import android.app.DatePickerDialog.OnDateSetListener
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast


class KeretaFragment: Fragment() {

    val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Jakarta"))
    internal var sdf = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_kereta, container, false)

        view.spinBerangkat.setItems("Yogyakarta", "Gambir", "Purwosari", "Bandung")
        view.spinTujuan.setItems("Yogyakarta", "Gambir", "Purwosari", "Bandung")

        view.inputSdt.setOnClickListener {
            datePicker(view.inputSdt)
        }

        view.inputEdt.setOnClickListener {
            datePicker(view.inputEdt)
        }

        view.btnCariKereta.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("idTeam", "kirimm")
            ListKeretaFragment().arguments = bundle
            val manager = (context as AppCompatActivity).supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.dashboard_main, ListKeretaFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view
    }

    private fun datePicker(input: EditText) {
        val mDatePicker = DatePickerDialog(
                context, OnDateSetListener { datepicker, selectedyear, selectedmonth, selectedday ->
            calendar.set(Calendar.YEAR, selectedyear)
            calendar.set(Calendar.MONTH, selectedmonth)
            calendar.set(Calendar.DAY_OF_MONTH,
                    selectedday)
            val sdf = SimpleDateFormat("dd-MM-yyyy",
                    Locale.ENGLISH)

            input.text = sdf.format(calendar.time).toEditable()
        }, year, month, day)

        mDatePicker.datePicker.minDate = Date().time
        mDatePicker.show()
    }


    private fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
}