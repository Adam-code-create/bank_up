package uz.gita.bankup.data.api.retrofit.respond.auth


data class VerifyResponse(
    val data : TokenData
)

data class TokenData(
    val access_token : String,
    val refresh_token : String,
)