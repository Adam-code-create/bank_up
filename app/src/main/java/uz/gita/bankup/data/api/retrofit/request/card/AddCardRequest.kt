package uz.gita.bankup.data.api.retrofit.request.card



data class AddCardRequest(
	val cardName: String,
	val pan: String,
	val exp: String
)
