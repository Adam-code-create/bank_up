package uz.gita.bankup.data.api.retrofit.respond.card

import com.google.gson.annotations.SerializedName

data class ResponseOwnerByPan(
	val data: DataOwner
)

data class DataOwner(
	val fio: String
)
