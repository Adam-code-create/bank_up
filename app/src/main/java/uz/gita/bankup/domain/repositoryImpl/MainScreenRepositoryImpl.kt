package uz.gita.bankup.domain.repositoryImpl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.bankup.data.api.pref.SharedPref
import uz.gita.bankup.data.api.retrofit.ApiClient
import uz.gita.bankup.data.api.retrofit.api.AuthApi
import uz.gita.bankup.data.api.retrofit.respond.ResponseMessage
import uz.gita.bankup.domain.repository.MainScreenRepository
import uz.gita.bankup.utils.parseTo
import javax.inject.Inject

class MainScreenRepositoryImpl @Inject constructor(private val pref :SharedPref) :MainScreenRepository {
    override fun openEditAccount()  {}

    override fun openMyCards(){}

    override fun logout(): Flow<Result<Unit>> = flow {
      val api = ApiClient.retrofit.create(AuthApi::class.java)
        val response = api.logout(pref.access_token)
        when{
            response.isSuccessful ->{
                emit(Result.success(Unit))
            }
            response.errorBody() != null->{
                val errorBody = response.errorBody()!!.string().parseTo<ResponseMessage>()
                emit(Result.failure(Throwable(errorBody.message)))
            }else ->{
            emit(Result.failure(Throwable("Serverga ulanishda xatolik bo'ldi")))
            }
        }
    }.flowOn(Dispatchers.IO)

}