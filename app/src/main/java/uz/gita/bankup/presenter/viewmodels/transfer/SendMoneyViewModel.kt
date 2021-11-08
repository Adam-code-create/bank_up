package uz.gita.bankup.presenter.viewmodels.transfer

import androidx.lifecycle.LiveData
import uz.gita.bankup.data.api.retrofit.request.card.RequestOwnerByPan
import uz.gita.bankup.data.api.retrofit.request.transfer.RequestMoneyTransfer
import uz.gita.bankup.data.api.retrofit.request.transfer.TransferFeeRequest
import uz.gita.bankup.data.api.retrofit.respond.transfer.ResponseMoneySend
import uz.gita.bankup.data.api.retrofit.respond.transfer.ResponseTransferFee

interface SendMoneyViewModel {
    val progressLiveData :LiveData<Boolean>
    val errorLiveData :LiveData<String>
    val successLiveData :LiveData<Unit>
    val successNameLiveData :LiveData<String>
    val enabledLiveData :LiveData<Boolean>
    val successTransferFeeLiveData :LiveData<ResponseTransferFee>

    fun sendMoney(data :RequestMoneyTransfer)
    fun transferFee(data :TransferFeeRequest)
    fun getName(data:RequestOwnerByPan)

}