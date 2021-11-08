package uz.gita.bankup.data.api.retrofit.request.auth

import com.google.gson.annotations.SerializedName

data class ResendRequest(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("phone")
	val phone: String
)
