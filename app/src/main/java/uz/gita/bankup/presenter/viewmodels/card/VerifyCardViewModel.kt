package uz.gita.bankup.presenter.viewmodels.card

import androidx.lifecycle.LiveData
import uz.gita.bankup.data.api.retrofit.request.card.VerifyCardRequest

interface VerifyCardViewModel {
    val errorLivedata : LiveData<String>
    val progressLiveData: LiveData<Boolean>
    val enabledLiveData : LiveData<Boolean>
    val successLiveData : LiveData<Boolean>

    fun verifyCode(data: VerifyCardRequest)
}