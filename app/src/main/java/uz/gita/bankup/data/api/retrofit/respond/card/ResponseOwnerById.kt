package uz.gita.bankup.data.api.retrofit.respond.card

import com.google.gson.annotations.SerializedName

data class ResponseOwnerById(
	val data: DataOwnerById
)

data class DataOwnerById(
	val fio: String
)
