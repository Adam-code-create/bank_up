package uz.gita.bankup.data.api.retrofit.request.auth

import com.google.gson.annotations.SerializedName

data class NewPasswordRequest(

	val code: String,
	val phone: String,
	val newPassword: String
)
