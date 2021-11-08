package uz.gita.bankup.domain.repository.card

import kotlinx.coroutines.flow.Flow
import uz.gita.bankup.data.api.retrofit.request.card.EditCardRequest

interface EditCardRepository {
    fun editCard(data : EditCardRequest) :Flow<Result<String>>
}