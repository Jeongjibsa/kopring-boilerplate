package js.training.kopring.model.dto.payload.request

import jakarta.validation.constraints.NotBlank

data class SignInRequest(
    @field:NotBlank
    val email: String,

    @field: NotBlank
    val password: String,
)


