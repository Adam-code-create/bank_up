package uz.gita.bankup.data.api.retrofit.request.profile

import com.google.gson.annotations.SerializedName

data class ResponseProfileInfo(
	val data: DataUser
)

data class DataUser(

	val firstName: String,
	val lastName: String,
	val password: String,
	val phone: String
)
