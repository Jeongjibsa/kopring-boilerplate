package js.training.kopring.model.dto.payload.request

import jakarta.validation.constraints.NotBlank

data class TokenRequest(

    @field: NotBlank
    val refreshToken: String,
)