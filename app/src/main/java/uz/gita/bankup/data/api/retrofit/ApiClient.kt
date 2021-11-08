package uz.gita.bankup.data.api.retrofit

import android.util.Log
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.gita.bankup.BuildConfig.BASE_URL
import uz.gita.bankup.BuildConfig.LOGGING
import uz.gita.bankup.app.App
import uz.gita.bankup.data.api.pref.SharedPref
import uz.gita.bankup.data.api.retrofit.respond.auth.VerifyResponse
import uz.gita.bankup.utils.myLog
import uz.gita.bankup.utils.timber

object ApiClient {


    val retrofit :Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(gettingHTTPClient())
        .build()

    private fun gettingHTTPClient():OkHttpClient{
        return OkHttpClient.Builder()
            .addLogging()
            .addInterceptor(refreshInterceptor())
            .build()

    }
}

fun  OkHttpClient.Builder.addLogging() :OkHttpClient.Builder{
    HttpLoggingInterceptor.Level.BODY
    val logging = object : HttpLoggingInterceptor.Logger{
        override fun log(message: String) {
            Log.d("KKK", message)
           // timber(message,"TTT")
        }
    }
    if (LOGGING){
        addInterceptor(ChuckInterceptor(App.instance))
        addInterceptor(HttpLoggingInterceptor(logging))
    }
    return this
}

fun refreshInterceptor() = Interceptor{chain ->
    val request = chain.request()
    val response =  chain.proceed(request)
    if (response.code == 401){
        response.close()
        val pref = SharedPref.getShared()
        val data = JSONObject()
        data.put("phone", "+998999244057")
        val body = data.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val requestRefresh = request.newBuilder()
            .addHeader("refresh_token", pref.refresh_token)
            .method("POST", body)
            .url("$BASE_URL/api/v1/auth/refresh")
            .build()
        val respondRefresh = chain.proceed(requestRefresh)
        myLog(respondRefresh.message)
        if (respondRefresh.code == 401){
            myLog("login navigate")
            return@Interceptor respondRefresh// refresh token ham eskirdi login screen navigate
        }
        if (respondRefresh.code == 200){
            respondRefresh.body?.let {
                val data = Gson().fromJson(it.string(), VerifyResponse::class.java)
                pref.access_token = data.data.access_token
                pref.refresh_token = data.data.refresh_token
            }
            respondRefresh.close()
            val requestTwo = request.newBuilder()
                .removeHeader("token")
                .addHeader("token", pref.access_token)
                .build()
            return@Interceptor chain.proceed(requestTwo)
        }
    }
    return@Interceptor response
}

