package uz.gita.bankup.presenter.viewmodelimpls.transfer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.bankup.data.api.retrofit.request.card.RequestOwnerByPan
import uz.gita.bankup.data.api.retrofit.request.transfer.RequestMoneyTransfer
import uz.gita.bankup.data.api.retrofit.request.transfer.TransferFeeRequest
import uz.gita.bankup.data.api.retrofit.respond.transfer.ResponseTransferFee
import uz.gita.bankup.domain.repository.transfer.SendMoneyRepository
import uz.gita.bankup.presenter.viewmodels.transfer.SendMoneyViewModel
import uz.gita.bankup.utils.isConnected
import javax.inject.Inject
@HiltViewModel
class SendMoneyViewModelImpl @Inject constructor(private val repository: SendMoneyRepository):ViewModel(), SendMoneyViewModel {
    override val progressLiveData = MutableLiveData<Boolean>()
    override val errorLiveData = MutableLiveData<String>()
    override val successLiveData = MutableLiveData<Unit>()
    override val successNameLiveData = MutableLiveData<String>()
    override val enabledLiveData = MutableLiveData<Boolean>()
    override val successTransferFeeLiveData = MutableLiveData<ResponseTransferFee>()

    override fun sendMoney(data: RequestMoneyTransfer) {
        if (!isConnected()) {
            errorLiveData.value = "Internetga ulanish bilan bog'liq muammolar bor"
            return
        }

        progressLiveData.value = true
        enabledLiveData.value = false
        repository.sendMoney(data).onEach {
            progressLiveData.value = false
            enabledLiveData.value = true
            it.onSuccess {
                successLiveData.value = Unit
            }
            it.onFailure { throwable ->
                errorLiveData.value = throwable.message
            }
        }.launchIn(viewModelScope)
    }

    override fun transferFee(data: TransferFeeRequest) {
        if (!isConnected()) {
            errorLiveData.value = "Internetga ulanish bilan bog'liq muammolar bor"
            return
        }

        progressLiveData.value = true
        repository.transferFee(data).onEach {
            progressLiveData.value = false
            it.onSuccess { res ->
               successTransferFeeLiveData.value= res
            }
            it.onFailure { throwable ->
                errorLiveData.value = throwable.message
            }
        }.launchIn(viewModelScope)
    }

    override fun getName(data: RequestOwnerByPan) {
        if (!isConnected()) {
            errorLiveData.value = "Internetga ulanish bilan bog'liq muammolar bor"
            return
        }
        progressLiveData.value = true
        repository.getName(data).onEach {
            progressLiveData.value = false
            it.onSuccess { name ->
                successNameLiveData.value = name
            }
            it.onFailure { throwable ->
                errorLiveData.value = throwable.message
            }
        }.catch {
            errorLiveData.value = "Server bilan bog'liq muammo bor"
        }.launchIn(viewModelScope)
    }
}
