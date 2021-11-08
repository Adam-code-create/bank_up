package uz.gita.bankup.data.api.retrofit.api


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import uz.gita.bankup.data.api.retrofit.request.auth.*
import uz.gita.bankup.data.api.retrofit.respond.auth.*

interface AuthApi    {

    @POST("/api/v1/auth/login")
    suspend fun login(@Body data: RequestLogin) : Response<ResponseLogin>

    @POST("/api/v1/auth/register")
    suspend fun register(@Body data : RegisterRequest) :Response<ResponseRegister>

    @POST("/api/v1/auth/verify")
    suspend fun verify (@Body data : VerifyCodeRequest) :Response<VerifyResponse>

    @POST("/api/v1/auth/logout")
    suspend fun logout (@Header("token")token :String) :Response<ResponseLogout>

    @POST("/api/v1/auth/resend")
    suspend fun resend (@Body data : ResendRequest) : Response<ResponseResend>

    @POST("/api/v1/auth/reset")
    suspend fun reset (@Body data : ResetRequest) :Response<ResetRespond>

    @POST("/api/v1/auth/newpassword")
    suspend fun newPassword (@Body data: NewPasswordRequest):Response<NewPasswordRespond>

}