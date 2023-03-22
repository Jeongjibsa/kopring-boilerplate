package js.training.kopring.model.dto.payload.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import js.training.kopring.model.enums.Authority

data class SignUpRequest(

    @field:NotBlank
    @field:Email
    val email: String,

    @field:NotBlank
    var password: String,

    val phone: String,

    val name: String,

    @field:NotNull
    val role: Authority,
)