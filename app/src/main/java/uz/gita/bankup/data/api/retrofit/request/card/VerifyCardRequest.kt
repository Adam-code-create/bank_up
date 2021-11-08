package uz.gita.bankup.data.api.retrofit.request.card

import com.google.gson.annotations.SerializedName

data class VerifyCardRequest(

	val code: String,
	val pan: String
)
