package js.training.kopring.model.dto

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

data class AccountDto(
    val email: String,
    var password: String,
    val name: String? = null,
    val phone: String,
    val passwordEncoder: BCryptPasswordEncoder
) {
    init {
        this.password = passwordEncoder.encode(password)
    }
}