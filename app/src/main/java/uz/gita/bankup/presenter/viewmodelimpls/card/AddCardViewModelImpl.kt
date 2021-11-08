package uz.gita.bankup.presenter.viewmodelimpls.card


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.bankup.data.api.retrofit.request.card.AddCardRequest
import uz.gita.bankup.domain.repository.card.AddBankCardRepository
import uz.gita.bankup.presenter.viewmodels.card.AddCardViewModel
import uz.gita.bankup.utils.isConnected
import javax.inject.Inject
@HiltViewModel
class AddCardViewModelImpl @Inject constructor(private val repository: AddBankCardRepository) : ViewModel(),
    AddCardViewModel {
    override val enabledRegisterLiveData =MutableLiveData<Boolean>()
    override val progressLivedata = MutableLiveData<Boolean>()
    override val errorLivaData = MutableLiveData<String>()
    override val successLivaData = MutableLiveData<String>()



    override fun addCard(data: AddCardRequest) {
        if (!isConnected()){
            errorLivaData.value = "Internetga ulanish bilan bog'liq muammolar bor"
            return
        }
        progressLivedata.value = true
        enabledRegisterLiveData.value = false
        repository.addCard(data).onEach {
            progressLivedata.value = false

            it.onFailure { throwable->
                errorLivaData.value = throwable.message
            }
            it.onSuccess {message->
                enabledRegisterLiveData.value = true
                successLivaData.value = message

            }
        }.launchIn(viewModelScope)
    }

}