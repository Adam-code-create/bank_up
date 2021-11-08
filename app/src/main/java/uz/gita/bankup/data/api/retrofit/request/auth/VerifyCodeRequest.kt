package uz.gita.bankup.data.api.retrofit.request.auth

data class VerifyCodeRequest(

	val code: String,
	val phone: String
)
