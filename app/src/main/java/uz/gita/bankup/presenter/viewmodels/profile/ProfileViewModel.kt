package uz.gita.bankup.presenter.viewmodels.profile

import androidx.lifecycle.LiveData
import okhttp3.MultipartBody
import uz.gita.bankup.data.api.retrofit.request.profile.RequestProfileEdit
import uz.gita.bankup.data.api.retrofit.request.profile.ResponseProfileInfo

interface ProfileViewModel {
    val enableLiveData : LiveData<Boolean>
    val errorLiveData : LiveData<String>
    val successLiveData : LiveData<String>
    val successAvatarLiveData : LiveData<Unit>
    val successGetAvatarLiveData : LiveData<MultipartBody.Companion>
    val successInfoLiveData : LiveData<ResponseProfileInfo>
    val progressLiveData: LiveData<Boolean>

    fun setAvatar(part:MultipartBody.Part)
    fun getAvatar()
    fun editProfile(data:RequestProfileEdit)
    fun getInfo()

}