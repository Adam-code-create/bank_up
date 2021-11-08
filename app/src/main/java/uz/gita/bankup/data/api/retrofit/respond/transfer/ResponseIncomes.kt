package uz.gita.bankup.data.api.retrofit.respond.transfer

import com.google.gson.annotations.SerializedName

data class ResponseIncomes(

	@field:SerializedName("data")
	val data: DataIncomePage
)

data class DataIncomePage(

	val pageNumber: Int,
	val data: List<DataIncome>,
	val pageSize: Int,
	val totalCount: Int
)

data class DataIncome(
	val amount: Double,
	val sender: Int,
	val fee: Double,
	val time: Long
)
