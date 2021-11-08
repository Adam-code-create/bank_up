package uz.gita.bankup.data.api.retrofit.respond.transfer



data class ResponseMoneySend(
	val data: DataMoney
)

data class DataMoney(
	val amount: Float,
	val receiver: Int,
	val sender: Int,
	val fee: Float,
	val id: Int,
	val time: Long,
	val status: Int
)
