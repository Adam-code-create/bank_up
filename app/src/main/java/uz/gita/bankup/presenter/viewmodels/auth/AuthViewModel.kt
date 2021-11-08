package uz.gita.bankup.presenter.viewmodels.auth

import androidx.lifecycle.LiveData

interface AuthViewModel {
    val openLoginLiveData :LiveData<Unit>
    val openRegisterLiveData:LiveData<Unit>

    fun openRegister()
    fun openLogin()
}