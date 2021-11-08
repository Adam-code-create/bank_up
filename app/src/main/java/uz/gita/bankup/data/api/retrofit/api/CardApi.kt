package uz.gita.bankup.data.api.retrofit.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import uz.gita.bankup.data.api.retrofit.request.card.*
import uz.gita.bankup.data.api.retrofit.respond.*
import uz.gita.bankup.data.api.retrofit.respond.card.*

interface CardApi {

    @POST("/api/v1/card/add-card")
    suspend fun addCard(@Header("token") token :String, @Body data : AddCardRequest) :Response<AddCardResponse>

    @POST("/api/v1/card/verify")
    suspend fun verifyCard(@Header("token")token: String, @Body data: VerifyCardRequest) :Response<VerifyCardResponse>

    @POST("/api/v1/card/edit-card")
    suspend fun editCard(@Header("token") token: String, @Body data: EditCardRequest) :Response<EditCardResponse>

    @POST("/api/v1/card/delete-card")
    suspend fun deleteCard(@Header("token") token: String, @Body data: DeleteCardRequest) :Response<DeleteCardResponse>

    @GET ("/api/v1/card/all")
    suspend fun getAllCards(@Header("token") token: String) :Response<GetAllCardsResponse>

    @GET("/api/v1/card/owner-by-pan")
    suspend fun getOwnerByPan(@Header("token")token:String, @Body data: RequestOwnerByPan): Response<ResponseOwnerByPan>

    @GET("/api/v1/card/owner-by-id")
    suspend fun getOwnerById(@Header("token")token:String, @Body data:RequestOwnerById):Response<ResponseOwnerById>

}