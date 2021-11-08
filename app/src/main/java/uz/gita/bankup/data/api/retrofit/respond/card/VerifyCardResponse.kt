package uz.gita.bankup.data.api.retrofit.respond.card

data class VerifyCardResponse(
	val data: Data
)

data class Data(
	val id : Int,
	val owner: String,
	val cardName: String,
	val balance: Float,
	val pan: String,
	val exp: String,
	val status: Int
)
