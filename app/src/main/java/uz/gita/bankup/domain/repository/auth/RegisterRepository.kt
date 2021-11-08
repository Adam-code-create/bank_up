package uz.gita.bankup.domain.repository.auth

import kotlinx.coroutines.flow.Flow
import uz.gita.bankup.data.api.retrofit.request.auth.RegisterRequest


interface RegisterRepository {
    fun registerUser (data : RegisterRequest) :Flow<Result<String>>

}