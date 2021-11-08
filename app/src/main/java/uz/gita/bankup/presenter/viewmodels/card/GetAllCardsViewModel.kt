package uz.gita.bankup.presenter.viewmodels.card

import androidx.lifecycle.LiveData
import uz.gita.bankup.data.api.retrofit.request.card.DeleteCardRequest
import uz.gita.bankup.data.api.retrofit.respond.CardData

interface GetAllCardsViewModel {
    val progressLivedata  : LiveData<Boolean>
    val errorLivaData : LiveData<String>
    val successLivaData : LiveData<List<CardData>>

    val successDeleteLiveData :  LiveData<String>
    val failureDeleteLiveData :  LiveData<String>
    val openSendMoneyLiveData :LiveData<Boolean>

    fun getAllCards()
    fun deleteCard(data: DeleteCardRequest)
    fun openSendMoney()

}