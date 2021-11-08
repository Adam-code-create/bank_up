package uz.gita.bankup.domain.repository.card

import kotlinx.coroutines.flow.Flow
import uz.gita.bankup.data.api.retrofit.request.card.AddCardRequest

interface AddBankCardRepository {
    fun addCard(data : AddCardRequest) : Flow<Result<String>>
}