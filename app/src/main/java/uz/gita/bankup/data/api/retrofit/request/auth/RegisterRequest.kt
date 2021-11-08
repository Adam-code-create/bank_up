package uz.gita.bankup.data.api.retrofit.request.auth


data class RegisterRequest (
	val firstName: String,
	val lastName: String,
	val password: String,
	val phone: String,
	val status: String
)
