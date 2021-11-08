package uz.gita.bankup.domain.repositoryImpl.transfer

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.bankup.data.api.pref.SharedPref
import uz.gita.bankup.data.api.retrofit.ApiClient
import uz.gita.bankup.data.api.retrofit.api.TransferApi
import uz.gita.bankup.data.api.retrofit.request.transfer.RequestTransferHistory
import uz.gita.bankup.data.api.retrofit.respond.transfer.ResponseTransferHistory
import uz.gita.bankup.domain.repository.transfer.TransferHistoryRepository
import javax.inject.Inject

class TransferHistoryRepositoryImpl @Inject constructor(private val pref:SharedPref): TransferHistoryRepository {
    override fun transferHistory(data: RequestTransferHistory): Flow<Result<ResponseTransferHistory>> = flow {
        val api = ApiClient.retrofit.create(TransferApi::class.java)
        val gson = Gson()
        val response = api.history(pref.access_token)
     //   if (response.isSuccessful)


    }


}