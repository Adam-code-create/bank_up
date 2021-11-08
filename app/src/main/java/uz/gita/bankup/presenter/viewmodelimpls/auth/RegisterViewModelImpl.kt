package uz.gita.bankup.presenter.viewmodelimpls.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.bankup.data.api.retrofit.request.auth.RegisterRequest
import uz.gita.bankup.domain.repository.auth.RegisterRepository
import uz.gita.bankup.presenter.viewmodels.auth.RegisterViewModel
import uz.gita.bankup.utils.isConnected
import javax.inject.Inject
@HiltViewModel
class RegisterViewModelImpl @Inject constructor(private val repository: RegisterRepository) :
    RegisterViewModel, ViewModel() {
    override val enabledRegisterLiveData  = MutableLiveData<Unit>()
    override val disabledRegisterLiveData = MutableLiveData<Unit>()
    override val progressLivedata = MutableLiveData<Boolean>()
    override val errorLivaData = MutableLiveData<String>()
    override val successLivaData  = MutableLiveData<String>()

    override fun registerLivaUser(data: RegisterRequest) {
        if (!isConnected()){
            errorLivaData.value = "Internetga ulanish bilan bog'liq muammolar bor"
            return
        }
        progressLivedata.value = true
        disabledRegisterLiveData.value = Unit
        repository.registerUser(data).onEach {
            progressLivedata.value = false
            enabledRegisterLiveData.value = Unit
            it.onFailure { throwable->
                errorLivaData.value = throwable.message
            }
            it.onSuccess {message->
                successLivaData.value = message
            }
        }.launchIn(viewModelScope)
    }
}