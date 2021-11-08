package uz.gita.bankup.data.api.retrofit.request.transfer


data class RequestMoneyTransfer(
	val amount: Int,
	val receiverPan: String,
	val sender: Int
)
