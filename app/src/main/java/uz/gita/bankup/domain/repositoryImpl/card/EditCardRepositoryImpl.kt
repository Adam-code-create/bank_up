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
import uz.gita.bankup.data.api.retrofit.request.card.EditCardRequest
import uz.gita.bankup.data.api.retrofit.respond.card.EditCardResponse
import uz.gita.bankup.domain.repository.card.EditCardRepository
import javax.inject.Inject
import kotlin.Exception

class EditCardRepositoryImpl @Inject constructor(private val pref :SharedPref): EditCardRepository {
    val api = ApiClient.retrofit.create(CardApi::class.java)
    val gson = Gson()
    override fun editCard( data: EditCardRequest) : Flow<Result<String>> = flow {
        try {
            val response = api.editCard(pref.access_token, data)
            if (response.code() == 200){
                emit(Result.success(response.body()!!.message))
            }else{
                var st = "Serverga ulanishda xatolik bo'ldi"
                ResponseBody
                response.errorBody()?.let {
                    st = gson.fromJson(it.string(), EditCardResponse::class.java).toString()
                }
                emit(Result.failure(Throwable(st)))
            }
        }
        catch (e : Exception){
            emit(Result.failure<String>(Throwable("Serverga ulanishda xatolik bo'ldi(Catch)")))

        }

    }.flowOn(Dispatchers.IO)
}