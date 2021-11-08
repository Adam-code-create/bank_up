package uz.gita.bankup.domain.repositoryImpl.card

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import uz.gita.bankup.data.api.pref.SharedPref
import uz.gita.bankup.data.api.retrofit.ApiClient
import uz.gita.bankup.data.api.retrofit.api.CardApi
import uz.gita.bankup.data.api.retrofit.request.card.AddCardRequest
import uz.gita.bankup.data.api.retrofit.respond.card.AddCardResponse
import uz.gita.bankup.domain.repository.card.AddBankCardRepository
import javax.inject.Inject

class AddBankCardRepositoryImpl @Inject constructor(private val pref:SharedPref) :
    AddBankCardRepository {
    val api = ApiClient.retrofit.create(CardApi::class.java)
    val gson = Gson()
    override fun addCard(data: AddCardRequest): Flow<Result<String>> = flow{

        try {
            val response = api.addCard(pref.access_token, data)
            if (response.code() == 200){
                emit(Result.success(response.body()!!.message))
            }else{
                var st = "Serverga ulanishda xatolik bo'ldi"
                ResponseBody
                response.errorBody()?.let {
                    st = gson.fromJson(it.string(), AddCardResponse::class.java).toString()
                }
                emit(Result.failure(Throwable(st)))
            }

        }catch (e :Exception){
            emit(Result.failure(Throwable("Serverga ulanishda xatolik bo'ldi(catch)")))
        }
    }.flowOn(Dispatchers.IO)
}