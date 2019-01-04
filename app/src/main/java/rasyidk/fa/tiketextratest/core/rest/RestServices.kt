package rasyidk.fa.tiketextratest.core.rest

import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import rasyidk.fa.tiketextratest.model.Users
import retrofit2.Call
import retrofit2.http.*
import okhttp3.MultipartBody
import rasyidk.fa.tiketextratest.model.Bank
import rasyidk.fa.tiketextratest.model.Kereta


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

    @POST("test/getAvailability")
    @FormUrlEncoded
    fun getKereta(@Field("origin") origin: String,
                  @Field("destination") destination: String,
                  @Field("date") date: String,
                  @Field("adult") adult: String,
                  @Field("infant") infant: String,
                  @Field("is_cheap_class") is_cheap_class: String,
                  @Field("class") clas: String,
                  @Field("is_return") is_return: String,
                  @Field("date_back") date_back: String): Observable<Kereta>
}