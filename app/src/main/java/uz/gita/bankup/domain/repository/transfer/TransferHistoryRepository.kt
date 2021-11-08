package uz.gita.bankup.domain.repository.transfer

import kotlinx.coroutines.flow.Flow
import uz.gita.bankup.data.api.retrofit.request.transfer.RequestTransferHistory
import uz.gita.bankup.data.api.retrofit.respond.transfer.ResponseTransferHistory

interface TransferHistoryRepository {
    fun transferHistory(data:RequestTransferHistory) :Flow<Result<ResponseTransferHistory>>
}