package uz.gita.bankup.presenter.viewmodelimpls.auth


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.bankup.data.api.retrofit.request.auth.RequestLogin
import uz.gita.bankup.domain.repository.auth.LoginRepository
import uz.gita.bankup.presenter.viewmodels.auth.LoginViewModel
import uz.gita.bankup.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class LoginViewModelImpl @Inject constructor(private val repository : LoginRepository) : ViewModel(),
    LoginViewModel {
    override val enableLiveData = MutableLiveData<Boolean>()
    override val errorLiveData = MutableLiveData<String>()
    override val successLiveData = MutableLiveData<String>()
    override val successResetLiveData = MutableLiveData<String>()
    override val progressLiveData = MutableLiveData<Boolean>()
    override fun loginUser(data: RequestLogin) {
        if (!isConnected()){
           errorLiveData.value = "Internetga ulanish bilan bog'liq muammolar bor"
            return
        }
        progressLiveData.value = true
        repository.loginUser(data).onEach {
            progressLiveData.value = false
            enableLiveData.value = true
            it.onSuccess { data->
                successLiveData.value = data.message
            }
            it.onFailure { Throwable ->
                errorLiveData.value = Throwable.message
            }
        }.launchIn(viewModelScope)
    }

    override fun resetPassword() {
        if (!isConnected()){
            errorLiveData.value = "Internetga ulanish bilan bog'liq muammolar bor"
            return
        }
        progressLiveData.value = true
        repository.resetPassword().onEach {
            progressLiveData.value = false
            it.onSuccess { message->
                successResetLiveData.value = message
            }
            it.onFailure { throwable->
                errorLiveData.value = throwable.message
            }
        }.launchIn(viewModelScope)
    }
}