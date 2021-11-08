package uz.gita.bankup.presenter.viewmodelimpls.card


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.bankup.data.api.pref.SharedPref
import uz.gita.bankup.data.api.retrofit.request.card.DeleteCardRequest
import uz.gita.bankup.data.api.retrofit.respond.CardData
import uz.gita.bankup.domain.repository.card.AddAllCardRepository
import uz.gita.bankup.presenter.viewmodels.card.GetAllCardsViewModel
import uz.gita.bankup.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class GetAllCardsViewModelImpl @Inject constructor(
    private val repository : AddAllCardRepository,
    val pref: SharedPref) : ViewModel(),
    GetAllCardsViewModel {
    override val progressLivedata = MutableLiveData<Boolean>()
    override val errorLivaData = MutableLiveData<String>()
    override val successLivaData = MutableLiveData<List<CardData>>()

    override val successDeleteLiveData = MutableLiveData<String>()
    override val failureDeleteLiveData = MutableLiveData<String>()
    override val openSendMoneyLiveData = MutableLiveData<Boolean>()


    override fun getAllCards() {
        if (!isConnected()){
            errorLivaData.value = "Internetga ulanish bilan bog'liq muammolar bor"
            return
        }
        progressLivedata.value = true
        repository.addAllCard().onEach {
            progressLivedata.value = false
            it.onFailure { throwable->
                errorLivaData.value = throwable.message
            }
            it.onSuccess {list->
                successLivaData.value = list
            }
        }.launchIn(viewModelScope)
    }

    override fun deleteCard(data: DeleteCardRequest) {
        if (!isConnected()){
            errorLivaData.value = "Internetga ulanish bilan bog'liq muammolar bor"
            return
        }
        repository.deleteCard( data).onEach {
            it.onFailure { Throwable ->
                failureDeleteLiveData.value = Throwable.message
            }
            it.onSuccess {st->
                successDeleteLiveData.value = st
            }
        }.launchIn(viewModelScope)
    }

    override fun openSendMoney() {
      openSendMoneyLiveData.value = repository.openSendMoney()
    }

}