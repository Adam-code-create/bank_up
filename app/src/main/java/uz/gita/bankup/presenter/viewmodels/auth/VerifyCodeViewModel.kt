package uz.gita.bankup.presenter.viewmodels.auth

import androidx.lifecycle.LiveData
import uz.gita.bankup.data.api.retrofit.request.auth.VerifyCodeRequest

interface VerifyCodeViewModel {
    val errorLivedata : LiveData<String>
    val resendLiveData :LiveData<Boolean>
    val progressLiveData: LiveData<Boolean>
    val enabledLiveData :LiveData<Boolean>
    val successLiveData:LiveData<Unit>

    fun verifyCode(data: VerifyCodeRequest)

}