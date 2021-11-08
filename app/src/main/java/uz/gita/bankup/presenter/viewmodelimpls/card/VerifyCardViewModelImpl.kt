package uz.gita.bankup.presenter.viewmodelimpls.card

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.bankup.data.api.retrofit.request.card.VerifyCardRequest
import uz.gita.bankup.domain.repository.card.VerifyCardRepository
import uz.gita.bankup.presenter.viewmodels.card.VerifyCardViewModel
import uz.gita.bankup.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class VerifyCardViewModelImpl @Inject constructor(private val repository: VerifyCardRepository) : ViewModel(),
    VerifyCardViewModel {
    override val errorLivedata= MutableLiveData<String>()
    override val progressLiveData = MutableLiveData<Boolean>()
    override val enabledLiveData =MutableLiveData<Boolean>()
    override val successLiveData =MutableLiveData<Boolean>()

    override fun verifyCode(data: VerifyCardRequest) {
        if (!isConnected()){
            errorLivedata.value = "Internetga ulanish bilan bog'liq muammolar bor"
            return
        }

        progressLiveData.value = true
        enabledLiveData.value = false
        repository.verifyCode(data).onEach {
            progressLiveData.value = false
            enabledLiveData.value = true
            it.onFailure { throwable->
                errorLivedata.value = throwable.message
            }
            it.onSuccess {
                successLiveData.value = true
            }
        }.launchIn(viewModelScope)

    }
}