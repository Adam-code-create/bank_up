package uz.gita.bankup.domain.repository.card

import kotlinx.coroutines.flow.Flow
import uz.gita.bankup.data.api.retrofit.request.card.DeleteCardRequest
import uz.gita.bankup.data.api.retrofit.respond.CardData

interface AddAllCardRepository {
    fun addAllCard() : Flow<Result<List<CardData>>>
    fun deleteCard( data: DeleteCardRequest) : Flow<Result<String>>
    fun openSendMoney() :Boolean
}