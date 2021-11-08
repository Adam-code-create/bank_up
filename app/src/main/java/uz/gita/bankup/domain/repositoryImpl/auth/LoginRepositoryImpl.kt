package uz.gita.bankup.domain.repositoryImpl.auth

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import uz.gita.bankup.data.api.pref.SharedPref
import uz.gita.bankup.data.api.retrofit.ApiClient
import uz.gita.bankup.data.api.retrofit.api.AuthApi
import uz.gita.bankup.data.api.retrofit.request.auth.RequestLogin
import uz.gita.bankup.data.api.retrofit.request.auth.ResetRequest
import uz.gita.bankup.data.api.retrofit.respond.auth.ResponseLogin
import uz.gita.bankup.domain.repository.auth.LoginRepository
import java.lang.Exception
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val pref :SharedPref) : LoginRepository {
    private val api = ApiClient.retrofit.create(AuthApi::class.java)

    override fun loginUser(data: RequestLogin): Flow<Result<ResponseLogin>> = flow {
        val gson = Gson()
        val response = api.login(data)
            if (response.isSuccessful){
                emit(Result.success(response.body()!!))
            } else {
                var st = "Serverga ulanishda xatolik bo'ldi"
                ResponseBody
                response.errorBody()?.let {
                 //    st = gson.fromJson(it.toString(), ResponseLogin::class.java).message
                }
                emit(Result.failure(Throwable(st)))
            }
    }.flowOn(Dispatchers.IO)

    override fun resetPassword(): Flow<Result<String>> = flow {
        val response = api.reset(ResetRequest(pref.phoneNumber))
        try {
            if (response.code() in 200..299){
                emit(Result.success(response.body()!!.message))
            }else{
                emit(Result.failure(Throwable(response.errorBody().toString())))
            }
        }catch (e:Exception){
            emit(Result.failure(Throwable("Serverga ulanishda xatolik bo'ldi(catch)")))
        }
    }.flowOn(Dispatchers.IO)

}