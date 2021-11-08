package uz.gita.bankup.presenter.viewmodelimpls.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.bankup.presenter.viewmodels.auth.AuthViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModelImpl @Inject constructor() :ViewModel(), AuthViewModel {
    override val openLoginLiveData = MutableLiveData<Unit>()
    override val openRegisterLiveData = MutableLiveData<Unit>()
    override fun openRegister() {
       openRegisterLiveData.value = Unit
    }

    override fun openLogin() {
       openLoginLiveData.value = Unit
    }


}