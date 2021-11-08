package uz.gita.bankup.domain.repositoryImpl.auth

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.bankup.data.api.pref.SharedPref
import uz.gita.bankup.data.api.retrofit.ApiClient
import uz.gita.bankup.data.api.retrofit.api.AuthApi
import uz.gita.bankup.data.api.retrofit.request.auth.VerifyCodeRequest
import uz.gita.bankup.domain.repository.auth.VerifySmsRepository
import java.lang.Exception
import javax.inject.Inject

class VerifyCodeRepositoryImpl @Inject constructor(private val pref : SharedPref) :
    VerifySmsRepository {
    override fun verifyCode(data : VerifyCodeRequest): Flow<Result<Unit>> = flow {
        val api = ApiClient.retrofit.create(AuthApi::class.java)
        try {
            val response = api.verify(data)
            if (response.code() == 200){
                response.body()?.let {
                    pref.access_token = it.data.access_token

                    pref.refresh_token = it.data.refresh_token
                }
                emit(Result.success(Unit))
            }else {
                emit(Result.failure<Unit>(Throwable(response.errorBody().toString())))
            }
        }catch (e : Exception){
            emit(Result.failure<Unit>(Throwable("Serverga ulanishda xatolik bo'ldi")))
        }
    }
}