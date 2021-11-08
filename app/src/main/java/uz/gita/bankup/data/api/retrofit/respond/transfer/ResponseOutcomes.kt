package uz.gita.bankup.data.api.retrofit.respond.transfer

import com.google.gson.annotations.SerializedName

data class ResponseOutcomes(
	val data: DataOutcomePage
)
data class DataOutcome(
	val amount: Float,
	val receiver: Int,
	val fee: Float,
	val time: Long,
	val status: Int
)

data class DataOutcomePage(
	val pageNumber: Int,
	val data: List<DataOutcome>,
	val pageSize: Int,
	val totalCount: Int
)
