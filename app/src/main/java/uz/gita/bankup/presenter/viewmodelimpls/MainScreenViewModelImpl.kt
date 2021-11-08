package uz.gita.bankup.presenter.viewmodelimpls


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.bankup.domain.repository.MainScreenRepository
import uz.gita.bankup.presenter.viewmodels.MainScreenViewModel
import uz.gita.bankup.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModelImpl @Inject constructor(private val repository: MainScreenRepository): ViewModel(), MainScreenViewModel {
    override val progressLiveData = MutableLiveData<Boolean>()
    override val errorLiveData = MutableLiveData<String>()
    override val successLogoutLiveData = MutableLiveData<Unit>()
    override val successMyCardsLiveData = MutableLiveData<Unit>()
    override val successEditAccountLiveData = MutableLiveData<Unit>()
    override fun openEditAccount() {
        successEditAccountLiveData.value = Unit
    }

    override fun openMyCards() {
        successMyCardsLiveData.value = Unit
    }

    override fun logOut() {
            if (!isConnected()) {
                errorLiveData.value = "Internetga ulanish bilan bog'liq muammolar bor"
                return
            }
            progressLiveData.value = true
            repository.logout().onEach {
                progressLiveData.value = false
                it.onFailure { throwable ->
                    errorLiveData.value = throwable.message
                }
                it.onSuccess {
                    successLogoutLiveData.value = Unit
                }
            }.launchIn(viewModelScope)
    }
}