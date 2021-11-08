package uz.gita.bankup.data.api.retrofit.respond.profile

import com.google.gson.annotations.SerializedName

data class ResponseProfileEdit(
	val data: Data
)

data class Data(

	val firstName: String,
	val lastName: String,
	val password: String,
	val phone: String
)
