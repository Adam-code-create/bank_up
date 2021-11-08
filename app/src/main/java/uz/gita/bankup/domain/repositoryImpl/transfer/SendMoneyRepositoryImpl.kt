package uz.gita.bankup.domain.repositoryImpl.transfer

import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.bankup.data.api.pref.SharedPref
import uz.gita.bankup.data.api.retrofit.ApiClient
import uz.gita.bankup.data.api.retrofit.api.CardApi
import uz.gita.bankup.data.api.retrofit.api.TransferApi
import uz.gita.bankup.data.api.retrofit.request.card.RequestOwnerByPan
import uz.gita.bankup.data.api.retrofit.request.transfer.RequestMoneyTransfer
import uz.gita.bankup.data.api.retrofit.request.transfer.TransferFeeRequest
import uz.gita.bankup.data.api.retrofit.respond.ResponseMessage
import uz.gita.bankup.data.api.retrofit.respond.transfer.ResponseMoneySend
import uz.gita.bankup.data.api.retrofit.respond.transfer.ResponseTransferFee
import uz.gita.bankup.domain.repository.transfer.SendMoneyRepository
import uz.gita.bankup.utils.parseTo
import java.lang.Exception
import javax.inject.Inject

class SendMoneyRepositoryImpl @Inject constructor(private val pref:SharedPref) :
    SendMoneyRepository {
   private val api = ApiClient.retrofit.create(TransferApi::class.java)
   private val gson = Gson()
    override fun sendMoney(data: RequestMoneyTransfer): Flow<Result<ResponseMoneySend>> = flow{

        try {
            val response = api.sendMoney(pref.access_token, data)
            if (response.code() in 200..299){
                emit(Result.success(response.body()!!))
            }
            else{
                emit(Result.failure(Throwable(response.errorBody().toString())))
            }
        }catch (e :Exception){
            emit(Result.failure(Throwable("Serverga ulanishda xatolik bo'ldi(catch)")))
        }
    }.flowOn(Dispatchers.IO)

    override fun transferFee(data: TransferFeeRequest): Flow<Result<ResponseTransferFee>> = flow{
        try {
            val response = api.transferFee(pref.access_token, data)
            if (response.code() in 200..299){
                emit(Result.success(response.body()!!))
            }else{
                emit(Result.failure(Throwable(response.errorBody().toString())))
            }
        }catch (e:Exception){
            emit(Result.failure(Throwable("Serverga ulanishda xatolik bo'ldi(catch)")))
        }

    }.flowOn(Dispatchers.IO)

    override fun getName(data: RequestOwnerByPan): Flow<Result<String>> = flow {
        val api2 = ApiClient.retrofit.create(CardApi::class.java)
        val response = api2.getOwnerByPan(pref.access_token, data)
        when {
            response.isSuccessful ->{
                emit(Result.success(response.body()!!.data.fio))
            }
            response.errorBody() != null->{
                val errorBody = response.errorBody()!!.string().parseTo<ResponseMessage>()
                emit(Result.failure(Throwable(errorBody.message)))
            }
            else ->{
                emit(Result.failure(Throwable("Serverga ulanishda xatolik bo'ldi")))
            }
        }
    }.flowOn(Dispatchers.IO)

}