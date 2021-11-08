package uz.gita.bankup.data.api.retrofit.respond


data class GetAllCardsResponse(
	val data: CardList
)

data class CardList(
	val data: List<CardData>
)

data class CardData(
	val owner: String,
	val cardName: String,
	val balance: Int,
	val pan: String,
	val exp: String,
	val status: Int
)
