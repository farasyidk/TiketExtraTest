package rasyidk.fa.tiketextratest.fragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
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
import okhttp3.MediaType
import okhttp3.MultipartBody
import rasyidk.fa.tiketextratest.R
import rasyidk.fa.tiketextratest.core.rest.APIs
import rasyidk.fa.tiketextratest.core.rest.RestRepository
import rasyidk.fa.tiketextratest.helper.DocumentHelper
import rasyidk.fa.tiketextratest.helper.UserSession
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.util.ArrayList
import java.util.HashMap
import java.util.Map
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.os.Build
import android.provider.MediaStore
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import okhttp3.RequestBody
import rasyidk.fa.tiketextratest.helper.NotificationHelper


class ProfileFragment: Fragment() {

    private lateinit var session: UserSession
    private lateinit var mAPIs: APIs
    private var PICK_IMAGE_REQUEST = 1
    lateinit var documentHelper: DocumentHelper
    private lateinit var returnUri: Uri
    private lateinit var chosenFile: File
    lateinit var vw: View
    var READ_WRITE_EXTERNAL = 1003

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        (context as AppCompatActivity).supportActionBar?.title = "Profil"

        vw = view
        mAPIs = RestRepository(context!!)
        session = UserSession(context!!)
        documentHelper = DocumentHelper()

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

        view.imgProfile.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Pilih fotomu yak"), PICK_IMAGE_REQUEST)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {

            returnUri = data.data

            try {
                val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, returnUri)


                vw.imgProfile.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            super.onActivityResult(requestCode, resultCode, data)


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val permissionsList = ArrayList<String>()
                addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE)
                addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE)

                if (!permissionsList.isEmpty())
                    ActivityCompat.requestPermissions(activity?.parent!!,
                            permissionsList.toTypedArray(),
                            READ_WRITE_EXTERNAL)
                else
                    getFilePath()
            } else {
                getFilePath()
            }

            onUpload()
        }
    }

    private fun getFilePath() {
        val filePath = documentHelper.getPath(context!!, this.returnUri)
        //Safety check to prevent null pointer exception
        if (filePath == null || filePath?.isEmpty()) return
        chosenFile = File(filePath)
        Log.d("FilePath", filePath)
    }

    private fun onUpload() {
        if (chosenFile == null) {
            Toast.makeText(context!!, "Pilih gambar sebelum upload.", Toast.LENGTH_SHORT)
                    .show()
            return
        }

        val notificationHelper = NotificationHelper(context!!)
        notificationHelper.createUploadingNotification()

        mAPIs.setUserImage(MultipartBody.Part.createFormData(
                "userfile",
                chosenFile.name,
                RequestBody.create(MediaType.parse("image/*"), chosenFile)
        )).enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(context, "An unknown error has occured.", Toast.LENGTH_SHORT)
                        .show()
                notificationHelper.createFailedUploadNotification()
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response == null) {
                    notificationHelper.createFailedUploadNotification()
                    return
                }
                if (response.isSuccessful) {
                    Toast.makeText(context, "Upload successful !", Toast.LENGTH_SHORT)
                            .show()
                    notificationHelper.createUploadedNotification()
                }
            }

        })

    }

    private fun addPermission(permissionsList: ArrayList<String>, permission: String) {
        if (ActivityCompat.checkSelfPermission(context!!, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission)
            shouldShowRequestPermissionRationale(permission)
        }
    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
}