package uz.gita.bankup.data.api.retrofit.respond.transfer

import com.google.gson.annotations.SerializedName

data class ResponseTransferHistory(

	val data: DataHistoryPage
)

data class DataHistoryPage(
	val pageNumber: Int,
	val data: List<DataHistory>,
	val pageSize: Int,
	val totalCount: Int
)

data class DataHistory(
	val amount: Double,
	val sender: Int,
	val fee: Double,
	val time: Long
)
