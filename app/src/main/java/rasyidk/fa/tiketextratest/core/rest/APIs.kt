package rasyidk.fa.tiketextratest.core.rest

import io.reactivex.Observable
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import javax.net.ssl.SSLSocketFactory

interface APIs {
    val retrofit: Retrofit

    fun createTrustManager(httpLoggingInterceptor: HttpLoggingInterceptor): SSLSocketFactory?
    fun authorizationTokenIsEmpty(): Boolean

    /**
     * There are 2 ways to pass parameters, either as a whole BODY or as individual param
     * @param username
     * @param password
     */
    fun getToken(username: String, password: String): Call<ResponseBody>

}