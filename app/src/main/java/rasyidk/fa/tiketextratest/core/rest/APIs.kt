package rasyidk.fa.tiketextratest.core.rest

import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import rasyidk.fa.tiketextratest.model.Bank
import rasyidk.fa.tiketextratest.model.Users
import retrofit2.Call
import retrofit2.Retrofit
import javax.net.ssl.SSLSocketFactory

interface APIs {
    val retrofit: Retrofit

    fun createTrustManager(httpLoggingInterceptor: HttpLoggingInterceptor): SSLSocketFactory?

    fun getToken(username: String, password: String): Call<ResponseBody>

    fun getProfile(): Observable<Users>

    fun getUserImage(): Call<ResponseBody>

    fun setUserImage(userfile: MultipartBody.Part): Call<ResponseBody>

    fun getBank(): Observable<ArrayList<Bank>>
}