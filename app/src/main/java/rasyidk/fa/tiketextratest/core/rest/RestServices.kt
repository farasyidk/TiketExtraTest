package rasyidk.fa.tiketextratest.core.rest

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface RestServices {

    @POST("auth/getToken")
    @FormUrlEncoded
//    @Headers("Content-Type: application/json")
    fun loginRequest(@Field("email") email: String,
                     @Field("password") password: String): Call<ResponseBody>
}