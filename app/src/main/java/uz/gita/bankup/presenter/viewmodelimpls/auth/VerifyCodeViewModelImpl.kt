package uz.gita.bankup.presenter.viewmodelimpls.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.bankup.data.api.retrofit.request.auth.VerifyCodeRequest
import uz.gita.bankup.domain.repository.auth.VerifySmsRepository
import uz.gita.bankup.presenter.viewmodels.auth.VerifyCodeViewModel
import uz.gita.bankup.utils.isConnected
import javax.inject.Inject
@HiltViewModel
class VerifyCodeViewModelImpl @Inject constructor(private val repository : VerifySmsRepository) : ViewModel(),
    VerifyCodeViewModel {
    override val errorLivedata = MutableLiveData<String>()
    override val resendLiveData = MutableLiveData<Boolean>()
    override val progressLiveData = MutableLiveData<Boolean>()
    override val enabledLiveData = MutableLiveData<Boolean>()
    override val successLiveData = MutableLiveData<Unit>()


    override fun verifyCode(data: VerifyCodeRequest) {
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
                successLiveData.value = Unit
            }
        }.launchIn(viewModelScope)

    }
}