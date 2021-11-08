package uz.gita.bankup.presenter.viewmodels.card

import androidx.lifecycle.LiveData
import uz.gita.bankup.data.api.retrofit.request.card.EditCardRequest

interface EditCardViewModel {
    val progressLivedata  : LiveData<Boolean>
    val errorLivaData : LiveData<String>
    val successLivaData : LiveData<String>
    fun editCard( data: EditCardRequest)

}