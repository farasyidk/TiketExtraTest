package rasyidk.fa.tiketextratest

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.content.SharedPreferences
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.ResponseBody
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONException
import org.json.JSONObject
import rasyidk.fa.tiketextratest.core.rest.APIs
import rasyidk.fa.tiketextratest.core.rest.RestRepository
import rasyidk.fa.tiketextratest.helper.UserSession
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    private val PREFER_NAME = "Reg"
    lateinit var session: UserSession
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var mAPIs: APIs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        session = UserSession(this)
        mAPIs = RestRepository(this)

        sharedPreferences = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE)

        btn_login.setOnClickListener {

            if (input_username.text.isEmpty()) {
                input_username.error = "email tidak boleh kosong"
                return@setOnClickListener
            } else if(!validEmail(input_username.text.toString())) {
                input_username.error = "format email salah"
                return@setOnClickListener
            }

            if (input_password.text.isEmpty()) {
                input_password.error = "password tidak boleh kosong"
                return@setOnClickListener
            }

            mAPIs.getToken(input_username.text.toString(), input_password.text.toString())
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Log.d("debug", "onFailure "+t.toString())
                        }

                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            Log.d("sampai", "response")
                            if (response.isSuccessful) {
                                try {
                                    val jsonResult = JSONObject(response.body()?.string())
                                    Log.d("hay", jsonResult.getString("success"))
                                    if (jsonResult.getString("success") == "true") {
                                        val key = jsonResult.getJSONObject("data").getString("token")
                                        Log.d("sampai", "truee $key")

                                        session.createUserLoginSession(key)
                                        startActivity<MainActivity>()
                                        finish()
                                    } else {
                                        Log.d("sampai", "salahh")
                                        toast("Username atau pass salah!")
                                    }
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                    Log.d("sampai", e.printStackTrace().toString())
                                } catch (e: IOException) {
                                    e.printStackTrace()
                                    Log.d("sampai", e.printStackTrace().toString())
                                }
                            } else {
                                Log.d("err", "${response.message()} ${response.errorBody()} ${response.code()}")
                            }
                        }

                    })
        }
    }

    private fun validEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}


