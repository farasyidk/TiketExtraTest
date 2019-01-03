package rasyidk.fa.tiketextratest.core.rest

import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import rasyidk.fa.tiketextratest.model.Users
import retrofit2.Call
import retrofit2.http.*
import okhttp3.MultipartBody
import rasyidk.fa.tiketextratest.model.Bank


interface RestServices {

    @POST("auth/getToken")
    @FormUrlEncoded
    fun loginRequest(@Field("email") email: String,
                     @Field("password") password: String): Call<ResponseBody>

    @GET("auth/getUser")
    fun getProfile(): Observable<Users>

    @GET("test/getUserImage")
    fun getUserImage(): Call<ResponseBody>

    @POST("test/updateUserImage")
    @Multipart
    fun postImage(@Part userfile: MultipartBody.Part): Call<ResponseBody>

    @GET("test/bank")
    fun getBank(): Observable<ArrayList<Bank>>
}