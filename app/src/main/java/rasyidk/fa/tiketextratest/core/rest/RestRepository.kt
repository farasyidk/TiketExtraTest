package rasyidk.fa.tiketextratest.core.rest

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import rasyidk.fa.tiketextratest.core.AppPreferences
import rasyidk.fa.tiketextratest.helper.UserSession
import rasyidk.fa.tiketextratest.model.Users
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class RestRepository(context: Context) : APIs {

    override val retrofit: Retrofit
    private lateinit var session: UserSession
    private var mRestServices: RestServices

    init {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.serializeNulls()
        /**
         * You may want to create an adapter for any nested JSON here
         * just pass the nested Model class and the Adapter class into it.
         * Google up GSON type adapter to create new adapter classes.
         */
        val gson = gsonBuilder
                //                .registerTypeAdapter(SomeNestedModelClass, new AdapterModelClass)
                .create()
        /**
         * A logging interceptor for that you can see all outbound and inbound connections
         */
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        /**
         * The trustmanager is for incase you have a self signed SSL or TLS cert connection.
         * Else, just ignore this
         */
        createTrustManager(loggingInterceptor)

        val clientLIVE = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    val originalRequest = chain.request()

                    val requestBuilder = originalRequest.newBuilder()
                            .addHeader("Accept", "application/json")
                            .addHeader("Content-type", "application/json")
                            .method(originalRequest.method(), originalRequest.body())
                    session = UserSession(context)
                    // Add JWT Token to the header if not empty
                    if (session.getUserDetails()["token"]?.isNotEmpty()!!) {
                        requestBuilder.addHeader("Authorization", "Bearer ${session.getUserDetails()["token"]}")
                    }

                    val request = requestBuilder.build()

                    chain.proceed(request)
                }
                //If you want to add the SSL Socket,
//                .sslSocketFactory(createTrustManager(loggingInterceptor))
                .build()
        val appPreferences = AppPreferences()
        val baseURL = appPreferences.getURL()
        Log.d("Pref", baseURL)
        /**
         * This is for the putting together the Retrofit client with GSON
         * and the RestServices APIs urls. We put it seperate because this is the main connector for multiple services
         */
        retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .client(clientLIVE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        mRestServices = retrofit.create(RestServices::class.java)

    }

    override fun createTrustManager(httpLoggingInterceptor: HttpLoggingInterceptor): SSLSocketFactory? {
        // Install the all-trusting trust manager
        val sslContext: SSLContext
        val sslSocketFactory: SSLSocketFactory

        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }

            @SuppressLint("TrustAllX509TrustManager")
            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                return arrayOf()
            }
        })
        try {
            sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            sslSocketFactory = sslContext.socketFactory
            return sslSocketFactory
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()

        } catch (e: KeyManagementException) {
            e.printStackTrace()
            return null
        }

        return null
    }

    override fun getToken(username: String, password: String): Call<ResponseBody> {
        return mRestServices.loginRequest(username, password)
    }

    override fun getProfile(): Observable<Users> {
        return mRestServices.getProfile()
    }

    override fun getUserImage(): Call<ResponseBody> {
        return mRestServices.getUserImage()
    }

    override fun setUserImage(userFile: MultipartBody.Part): Call<ResponseBody> {
        return mRestServices.postImage(userFile)
    }
}