package uz.gita.bankup.domain.repositoryImpl.auth

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import uz.gita.bankup.data.api.pref.SharedPref
import uz.gita.bankup.data.api.retrofit.ApiClient
import uz.gita.bankup.data.api.retrofit.api.AuthApi
import uz.gita.bankup.data.api.retrofit.request.auth.RegisterRequest
import uz.gita.bankup.data.api.retrofit.respond.auth.ResponseRegister
import uz.gita.bankup.domain.repository.auth.RegisterRepository
import java.lang.Exception
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val pref :SharedPref) : RegisterRepository {
private val api = ApiClient.retrofit.create(AuthApi::class.java)
    override fun registerUser(data: RegisterRequest): Flow<Result<String>> = flow {
        pref.phoneNumber = data.phone
        pref.firstname = data.firstName
        pref.lastName = data.lastName
        pref.password = data.password

        val gson = Gson()
        try {
            val response = api.register(data)
            if (response.code() in 200..299){
                emit(Result.success(response.body()!!.message))
            }else{
                var st = "Serverga ulanishda xatolik bo'ldi"
                ResponseBody
                response.errorBody()?.let {
                    st = gson.fromJson(it.string(), ResponseRegister::class.java).message
                }
                emit(Result.failure<String>(Throwable(st)))
            }

        }catch (e :Exception){
            emit(Result.failure<String>(Throwable("Serverga ulanishda xatolik bo'ldi")))
        }
    }.flowOn(Dispatchers.Default)
}