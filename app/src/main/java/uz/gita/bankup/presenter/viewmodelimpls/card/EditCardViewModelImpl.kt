package uz.gita.bankup.presenter.viewmodelimpls.card

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.bankup.data.api.retrofit.request.card.EditCardRequest
import uz.gita.bankup.domain.repository.card.EditCardRepository
import uz.gita.bankup.presenter.viewmodels.card.EditCardViewModel
import uz.gita.bankup.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class EditCardViewModelImpl @Inject constructor(private val repository : EditCardRepository) : ViewModel(),
    EditCardViewModel {
    override val progressLivedata = MutableLiveData<Boolean>()
    override val errorLivaData = MutableLiveData<String>()
    override val successLivaData = MutableLiveData<String>()


    override fun editCard(data: EditCardRequest) {
        if (!isConnected()){
            errorLivaData.value = "Internetga ulanish bilan bog'liq muammolar bor"
            return
        }
        progressLivedata.value = true
        repository.editCard(data).onEach {
            progressLivedata.value = false

            it.onFailure { throwable->
                errorLivaData.value = throwable.message
            }
            it.onSuccess {message->
                successLivaData.value = message

            }
        }.launchIn(viewModelScope)
    }
}