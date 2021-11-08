package uz.gita.bankup.domain.repositoryImpl.profile


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import uz.gita.bankup.data.api.pref.SharedPref
import uz.gita.bankup.data.api.retrofit.ApiClient
import uz.gita.bankup.data.api.retrofit.api.ProfileApi
import uz.gita.bankup.data.api.retrofit.request.profile.DataUser
import uz.gita.bankup.data.api.retrofit.request.profile.RequestProfileEdit
import uz.gita.bankup.data.api.retrofit.request.profile.ResponseProfileInfo
import uz.gita.bankup.data.api.retrofit.respond.ResponseMessage
import uz.gita.bankup.data.api.retrofit.respond.profile.ResponseProfileEdit
import uz.gita.bankup.domain.repository.profile.ProfileRepository
import uz.gita.bankup.utils.parseTo

import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val pref: SharedPref) :ProfileRepository {
   private val api = ApiClient.retrofit.create(ProfileApi::class.java)
    override fun uploadImage(part: MultipartBody.Part): Flow<Result<Unit>> = flow {

        val response = api.setAvatar(pref.access_token, part)
        when{
            response.isSuccessful->emit(Result.success(Unit))
            response.errorBody() != null -> {
                val errorBody = response.errorBody()!!.string().parseTo<ResponseMessage>()
            emit(Result.failure<Unit>(Throwable(errorBody.message))) }
            else -> emit(Result.failure<Unit>(Throwable("Server bilan bo'gliq muammo bo'ldi")))
        }
    }.flowOn(Dispatchers.IO)

    override fun getAvatar(): Flow<Result<MultipartBody.Companion>> = flow {
       val response = api.getAvatar(pref.access_token)
        when{
            response.isSuccessful->emit(Result.success(MultipartBody))
            response.errorBody() != null ->{
                val errorBody =response.errorBody()!!.string().parseTo<ResponseMessage>()
            }
            else -> emit(Result.failure(Throwable("Server bilan bo'gliq muammo bo'ldi")))
        }
    }.flowOn(Dispatchers.IO)

    override fun editProfile(data: RequestProfileEdit): Flow<Result<Unit>> = flow {
        val response = api.editProfile(pref.access_token, data)
        when{
            response.isSuccessful -> emit(Result.success(Unit))
            response.errorBody() != null ->{
                val errorBody =response.errorBody()!!.string().parseTo<ResponseMessage>()
            }
            else -> emit(Result.failure(Throwable("Server bilan bo'gliq muammo bo'ldi")))
        }
    }.flowOn(Dispatchers.IO)

    override fun getInfo(): Flow<Result<ResponseProfileInfo>> = flow {
        val response = api.profileInfo(pref.access_token)
        when{
            response.isSuccessful -> emit(Result.success(ResponseProfileInfo(DataUser(pref.firstname, pref.lastName, pref.password, pref.phoneNumber))))
            response.errorBody() != null ->{
                val errorBody =response.errorBody()!!.string().parseTo<ResponseMessage>()
            }
            else -> emit(Result.failure(Throwable("Server bilan bo'gliq muammo bo'ldi")))
        }
    }.flowOn(Dispatchers.IO)

}