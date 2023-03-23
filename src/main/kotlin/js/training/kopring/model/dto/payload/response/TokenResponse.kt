package js.training.kopring.model.dto.payload.response

import com.fasterxml.jackson.annotation.JsonInclude

data class TokenResponse(

    val accessToken: String,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    val refreshToken: String?
) {
    constructor(accessToken: String) : this(accessToken, null)
}