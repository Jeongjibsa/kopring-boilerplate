package js.training.kopring.model.dto

data class AccountDto(
    val email: String,
    val name: String? = null,
    val phone: String,
    val roleString: String? = null,
)