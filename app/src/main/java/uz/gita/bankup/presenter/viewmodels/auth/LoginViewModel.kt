package uz.gita.bankup.presenter.viewmodels.auth

import androidx.lifecycle.LiveData
import uz.gita.bankup.data.api.retrofit.request.auth.RequestLogin

interface LoginViewModel {
    val enableLiveData : LiveData<Boolean>
    val errorLiveData : LiveData<String>
    val successLiveData :LiveData<String>
    val successResetLiveData :LiveData<String>
    val progressLiveData:LiveData<Boolean>

    fun loginUser(data: RequestLogin)
    fun resetPassword()
}