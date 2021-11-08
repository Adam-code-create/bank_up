package uz.gita.bankup.domain.repository.auth

import kotlinx.coroutines.flow.Flow
import uz.gita.bankup.data.api.retrofit.request.auth.RequestLogin
import uz.gita.bankup.data.api.retrofit.respond.auth.ResponseLogin

interface LoginRepository {
    fun loginUser(data : RequestLogin) :Flow<Result<ResponseLogin>>
    fun resetPassword() : Flow<Result<String>>
}