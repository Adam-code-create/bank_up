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
import uz.gita.bankup.data.api.retrofit.request.card.DeleteCardRequest
import uz.gita.bankup.data.api.retrofit.respond.CardData
import uz.gita.bankup.data.api.retrofit.respond.card.DeleteCardResponse
import uz.gita.bankup.domain.repository.card.AddAllCardRepository
import javax.inject.Inject

class AddAllCardRepositoryImpl @Inject constructor(private val pref :SharedPref) :
    AddAllCardRepository {
    private val api = ApiClient.retrofit.create(CardApi::class.java)
    private val gson = Gson()
    override fun addAllCard(): Flow<Result<List<CardData>>> = flow{
     try {
         val response = api.getAllCards(pref.access_token)
         if (response.code() == 200){
             emit(Result.success(response.body()!!.data.data))
         }else{
             var st = "Serverga ulanishda xatolik bo'ldi"
             ResponseBody
             response.errorBody()?.let {
              //   st = gson.fromJson(it.string(), GetAllCardsResponse::class.java).toString()
             }
             emit(Result.failure(Throwable(st)))
         }
     }
     catch (e:Exception){

         emit(Result.failure(Throwable("Serverga ulanishda xatolik bo'ldi(Catch)")))
     }
    }.flowOn(Dispatchers.IO)

    override fun deleteCard(data: DeleteCardRequest): Flow<Result<String>> = flow{
      try {
          val response = api.deleteCard(pref.access_token, data)
          if (response.code() == 200){
              emit(Result.success(response.body()!!.message))
          } else{
              var st = "Serverga ulanishda xatolik bo'ldi"
              ResponseBody
              response.errorBody()?.let {
                  st = gson.fromJson(it.string(), DeleteCardResponse::class.java).toString()
              }
              emit(Result.failure(Throwable(st)))
          }
      }catch (e:Exception){
          emit(Result.failure(Throwable("Serverga ulanishda xatolik bo'ldi (catch)")))
      }
    }.flowOn(Dispatchers.IO)

    override fun openSendMoney(): Boolean = true


}