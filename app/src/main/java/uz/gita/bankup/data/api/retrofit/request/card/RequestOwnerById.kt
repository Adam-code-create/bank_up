package uz.gita.bankup.data.api.retrofit.request.card

import com.google.gson.annotations.SerializedName

data class RequestOwnerById(

	@field:SerializedName("id")
	val id: String
)
