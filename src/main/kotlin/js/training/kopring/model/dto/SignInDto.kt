package js.training.kopring.model.dto

import java.beans.ConstructorProperties

data class SignInDto
@ConstructorProperties("email", "password")
constructor(
    val email: String,
    val password: String
)

