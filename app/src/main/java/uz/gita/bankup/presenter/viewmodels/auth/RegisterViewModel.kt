package uz.gita.bankup.presenter.viewmodels.auth

import androidx.lifecycle.LiveData
import uz.gita.bankup.data.api.retrofit.request.auth.RegisterRequest

interface RegisterViewModel {
     val enabledRegisterLiveData : LiveData<Unit>
    val  disabledRegisterLiveData :LiveData<Unit>
    val progressLivedata  : LiveData<Boolean>
    val errorLivaData :LiveData<String>
    val successLivaData :LiveData<String>
    fun registerLivaUser(data: RegisterRequest)
 }