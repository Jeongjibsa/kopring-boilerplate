package js.training.kopring.model.dto

data class AccountDto(
    val email: String,
    val name: String? = null,
    val phone: String,
    val roleString: String? = null,
) {
    private constructor() : this(email = "", phone = "")

    companion object {
        @JvmField
        val EMPTY = AccountDto()
    }
}