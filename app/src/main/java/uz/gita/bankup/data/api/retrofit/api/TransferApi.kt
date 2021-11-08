package uz.gita.bankup.data.api.retrofit.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import uz.gita.bankup.data.api.retrofit.request.transfer.RequestMoneyTransfer
import uz.gita.bankup.data.api.retrofit.request.transfer.TransferFeeRequest
import uz.gita.bankup.data.api.retrofit.respond.transfer.*

interface TransferApi {

    @POST("/api/v1/money-transfer/send-money")
    suspend fun sendMoney(@Header("token")token:String,@Body data: RequestMoneyTransfer) :Response<ResponseMoneySend>

    @POST("/api/v1/money-transfer/fee")
    suspend fun transferFee (@Header("token")token: String, @Body data : TransferFeeRequest) :Response<ResponseTransferFee>

    @GET("/api/v1/money-transfer/history")
    suspend fun history (@Header("token")token:String) : Response<ResponseTransferHistory>

    @GET("/api/v1/money-transfer/incomes")
    suspend fun incomes(@Header("token")token: String) :Response<ResponseIncomes>

    @GET("/api/v1/money-transfer/outcomes")
    suspend fun outcomes(@Header("token")token: String) :Response<ResponseOutcomes>


}