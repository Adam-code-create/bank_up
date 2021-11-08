package uz.gita.bankup.presenter.viewmodels

import androidx.lifecycle.LiveData

interface SplashViewModel {
    val openAuthScreenLiveData:LiveData<Unit>
    val openMainScreenLiveData:LiveData<Unit>

}