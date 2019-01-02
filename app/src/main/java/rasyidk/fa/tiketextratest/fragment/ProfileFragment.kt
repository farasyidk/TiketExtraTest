package rasyidk.fa.tiketextratest.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_profile.view.*
import okhttp3.ResponseBody
import rasyidk.fa.tiketextratest.R
import rasyidk.fa.tiketextratest.core.rest.APIs
import rasyidk.fa.tiketextratest.core.rest.RestRepository
import rasyidk.fa.tiketextratest.helper.UserSession
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment: Fragment() {

    private lateinit var session: UserSession
    private lateinit var mAPIs: APIs

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        mAPIs = RestRepository(context!!)
        session = UserSession(context!!)

        mAPIs.getProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.inputNama.text = it.data.name.toEditable()
                    view.inputEmail.text = it.data.email.toEditable()
                    view.inputMobile.text = it.data.mobile.toEditable()
                }, {

                })

        mAPIs.getUserImage()
                .enqueue(object : Callback<ResponseBody>{
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("debug", "onFailure "+t.toString())
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                        Log.d("image", response.body()?.string())
                        val bmp = BitmapFactory.decodeStream(response.body()?.byteStream())
                        Glide.with(context!!).load(bmp).into(view.imgProfile)
//                        view.imgProfile.setImageBitmap(bmp)
                    }

                })

        view.btnLogout.setOnClickListener {
            activity?.finish()
            session.logoutUser()
        }

        return view
    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
}