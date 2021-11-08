package uz.gita.bankup.domain.repository.card

import kotlinx.coroutines.flow.Flow
import uz.gita.bankup.data.api.retrofit.request.card.VerifyCardRequest
import uz.gita.bankup.data.api.retrofit.respond.card.VerifyCardResponse

interface VerifyCardRepository {
    fun verifyCode(data: VerifyCardRequest) : Flow<Result<VerifyCardResponse>>
}