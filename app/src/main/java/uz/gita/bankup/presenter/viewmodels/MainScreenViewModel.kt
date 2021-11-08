package uz.gita.bankup.presenter.viewmodels

import androidx.lifecycle.LiveData

interface MainScreenViewModel {
    val progressLiveData:LiveData<Boolean>
    val errorLiveData :LiveData<String>
    val successLogoutLiveData:LiveData<Unit>
    val successMyCardsLiveData:LiveData<Unit>
    val successEditAccountLiveData:LiveData<Unit>

    fun openEditAccount()
    fun openMyCards()
    fun logOut()
}