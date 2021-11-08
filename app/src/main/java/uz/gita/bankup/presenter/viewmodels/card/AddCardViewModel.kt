package uz.gita.bankup.presenter.viewmodels.card

import androidx.lifecycle.LiveData
import uz.gita.bankup.data.api.retrofit.request.card.AddCardRequest

interface AddCardViewModel {
    val enabledRegisterLiveData : LiveData<Boolean>
    val progressLivedata  : LiveData<Boolean>
    val errorLivaData : LiveData<String>
    val successLivaData : LiveData<String>
    fun addCard(data : AddCardRequest)

}