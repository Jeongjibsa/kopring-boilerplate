package js.training.kopring.model.dto.payload.response

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String?
) {
    constructor(accessToken: String) : this(accessToken, null)
}