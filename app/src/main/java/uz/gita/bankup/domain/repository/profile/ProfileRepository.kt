package uz.gita.bankup.domain.repository.profile

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import uz.gita.bankup.data.api.retrofit.request.profile.RequestProfileEdit
import uz.gita.bankup.data.api.retrofit.request.profile.ResponseProfileInfo
import uz.gita.bankup.data.api.retrofit.respond.profile.ResponseProfileEdit
import java.io.File

interface ProfileRepository {
    fun uploadImage(part:MultipartBody.Part): Flow<Result<Unit>>
    fun getAvatar():Flow<Result<MultipartBody.Companion>>
    fun editProfile(data:RequestProfileEdit):Flow<Result<Unit>>
    fun getInfo():Flow<Result<ResponseProfileInfo>>
}