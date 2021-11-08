package uz.gita.bankup.presenter.viewmodelimpls.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MultipartBody
import uz.gita.bankup.data.api.retrofit.request.profile.RequestProfileEdit
import uz.gita.bankup.data.api.retrofit.request.profile.ResponseProfileInfo
import uz.gita.bankup.domain.repository.profile.ProfileRepository
import uz.gita.bankup.presenter.viewmodels.profile.ProfileViewModel
import uz.gita.bankup.utils.isConnected
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModelImpl @Inject constructor(private val repository :ProfileRepository) :ViewModel(), ProfileViewModel{
    override val enableLiveData= MutableLiveData<Boolean>()
    override val errorLiveData=MutableLiveData<String>()
    override val successLiveData =MutableLiveData<String>()
    override val successAvatarLiveData =MutableLiveData<Unit>()
    override val successGetAvatarLiveData = MutableLiveData<MultipartBody.Companion>()
    override val successInfoLiveData =MutableLiveData<ResponseProfileInfo>()
    override val progressLiveData =MutableLiveData<Boolean>()

    override fun setAvatar(part: MultipartBody.Part) {
        if (!isConnected()){
           errorLiveData.value = "Internetga ulanish bilan bog'liq muammolar bor"
            return
        }

        progressLiveData.value = true
        repository.uploadImage(part).onEach {
            progressLiveData.value = false
            it.onSuccess {
                successAvatarLiveData.value = Unit
            }
            it.onFailure {throwable->
                errorLiveData.value = throwable.message
            }
        }.catch {
            errorLiveData.value = "Server bilan bog'liq muammo bor"
        }.launchIn(viewModelScope)

    }

    override fun getAvatar() {
        if (!isConnected()){
            errorLiveData.value = "Internetga ulanish bilan bog'liq muammolar bor"
            return
        }
        progressLiveData.value = true
        repository.getAvatar().onEach {
            progressLiveData.value = false
         it.onSuccess { multipart->
             successGetAvatarLiveData.value = multipart
         }
            it.onFailure { throwable->
                errorLiveData.value = throwable.message
            }
        }.catch {
            errorLiveData.value = "Server bilan bog'liq muammo bor"
        }.launchIn(viewModelScope)

    }

    override fun editProfile(data: RequestProfileEdit) {
        if (!isConnected()){
            errorLiveData.value = "Internetga ulanish bilan bog'liq muammolar bor"
            return
        }
        progressLiveData.value = true
        repository.editProfile(data).onEach {
            progressLiveData.value = false
            it.onSuccess {
                successLiveData.value = "Saved"
            }
            it.onFailure {throwable->
                errorLiveData.value = throwable.message
            }
        }.catch {
            errorLiveData.value = "Server bilan bog'liq muammo bor"
        }.launchIn(viewModelScope)


    }

    override fun getInfo() {
        if (!isConnected()){
            errorLiveData.value = "Internetga ulanish bilan bog'liq muammolar bor"
            return
        }

        progressLiveData.value = true
        repository.getInfo().onEach {
            progressLiveData.value = false
            it.onSuccess {responseInfo->
                successInfoLiveData.value = responseInfo
            }
            it.onFailure {throwable->
                errorLiveData.value = throwable.message
            }
        }.catch {
            errorLiveData.value = "Server bilan bog'liq muammo bor"
        }.launchIn(viewModelScope)

    }
}