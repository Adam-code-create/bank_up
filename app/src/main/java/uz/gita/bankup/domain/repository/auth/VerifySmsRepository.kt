package uz.gita.bankup.domain.repository.auth

import kotlinx.coroutines.flow.Flow
import uz.gita.bankup.data.api.retrofit.request.auth.VerifyCodeRequest

interface VerifySmsRepository {
    fun verifyCode (data : VerifyCodeRequest) :Flow<Result<Unit>>

}