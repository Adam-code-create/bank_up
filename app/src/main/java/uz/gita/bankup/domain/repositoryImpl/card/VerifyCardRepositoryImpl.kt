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
import uz.gita.bankup.data.api.retrofit.request.card.VerifyCardRequest
import uz.gita.bankup.data.api.retrofit.respond.card.VerifyCardResponse
import uz.gita.bankup.domain.repository.card.VerifyCardRepository
import java.lang.Exception
import javax.inject.Inject

class VerifyCardRepositoryImpl @Inject constructor(private val pref :SharedPref) : VerifyCardRepository {
   private val api = ApiClient.retrofit.create(CardApi::class.java)
    private val gson = Gson()

    override fun verifyCode(data: VerifyCardRequest): Flow<Result<VerifyCardResponse>> = flow{
       try {
           val response = api.verifyCard("cda7b3f4112dc993e8e9e9f35696de54", data)
           if (response.code() == 200){
               emit(Result.success(response.body()!!))
           }else{
               var st = "Serverga ulanishda xatolik bo'ldi"
               ResponseBody
               response.errorBody()?.let {
                   st = gson.fromJson(it.string(), VerifyCardResponse::class.java).toString()
               }
               emit(Result.failure(Throwable(st)))
           }

       }catch (e :Exception){
           emit(Result.failure(Throwable("Serverga ulanishda xatolik bo'ldi (catch")))
       }
    }.flowOn(Dispatchers.IO)
}