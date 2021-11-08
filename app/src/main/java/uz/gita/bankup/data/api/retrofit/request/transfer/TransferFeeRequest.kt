package uz.gita.bankup.data.api.retrofit.request.transfer



data class TransferFeeRequest(
	val amount: Int,
	val receiverPan: String,
	val sender: Int
)
