package uz.gita.bankup.data.api.retrofit.request.card

import com.google.gson.annotations.SerializedName

data class EditCardRequest(

	val newName: String,
	val cardNumber: String
)
