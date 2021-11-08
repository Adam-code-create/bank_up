package uz.gita.bankup.domain.repository.transfer

import kotlinx.coroutines.flow.Flow
import uz.gita.bankup.data.api.retrofit.request.card.RequestOwnerByPan
import uz.gita.bankup.data.api.retrofit.request.transfer.RequestMoneyTransfer
import uz.gita.bankup.data.api.retrofit.request.transfer.TransferFeeRequest
import uz.gita.bankup.data.api.retrofit.respond.transfer.ResponseMoneySend
import uz.gita.bankup.data.api.retrofit.respond.transfer.ResponseTransferFee

interface SendMoneyRepository {
    fun sendMoney(data : RequestMoneyTransfer) :Flow<Result<ResponseMoneySend>>
    fun transferFee(data: TransferFeeRequest) :Flow<Result<ResponseTransferFee>>
    fun getName(data :RequestOwnerByPan) :Flow<Result<String>>

}