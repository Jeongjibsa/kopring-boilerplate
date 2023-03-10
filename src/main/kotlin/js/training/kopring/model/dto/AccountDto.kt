package js.training.kopring.model.dto

import js.training.kopring.model.entity.Account

data class AccountDto(
    val email: String,
    val name: String? = null,
    val phone: String,
) {
    constructor(account: Account) : this(account.email, account.name, account.phone)
}