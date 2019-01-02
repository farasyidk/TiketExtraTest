package rasyidk.fa.tiketextratest.core.rest

import io.reactivex.Observable
import okhttp3.ResponseBody
import rasyidk.fa.tiketextratest.model.Users
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface RestServices {

    @POST("auth/getToken")
    @FormUrlEncoded
    fun loginRequest(@Field("email") email: String,
                     @Field("password") password: String): Call<ResponseBody>

    @GET("auth/getUser")
    fun getProfile(): Observable<Users>

    @GET("test/getUserImage")
    fun getUserImage(): Call<ResponseBody>
}