package uz.gita.bankup.presenter.viewmodelimpls

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.bankup.domain.repository.SplashRepository
import uz.gita.bankup.data.api.enum.StartScreenEnum
import uz.gita.bankup.presenter.viewmodels.SplashViewModel
import javax.inject.Inject

@HiltViewModel
class SplashSCreenViewModelImpl @Inject constructor(private val repository : SplashRepository) : ViewModel(),
    SplashViewModel {
    override val openAuthScreenLiveData = MutableLiveData<Unit>()

    override val openMainScreenLiveData = MutableLiveData<Unit>()

    init {
        viewModelScope.launch {
            delay(1000)
            if (repository.openScreen() == StartScreenEnum.AUTH){
                openAuthScreenLiveData.value = Unit
            }else{
                openMainScreenLiveData.value = Unit
            }
        }
    }

}