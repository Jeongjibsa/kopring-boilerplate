package js.training.kopring.model.dto

import js.training.kopring.model._enum.Authority

data class SignUpDto(
    val email: String,
    var password: String,
    val phone: String,
    val name: String,
    var role: Authority,
)