package js.training.kopring.service

import js.training.kopring.model.dto.AccountDto
import js.training.kopring.model.security.AccountAdapter
import js.training.kopring.repository.AccountRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountService(
    private val accountRepository: AccountRepository,
) : UserDetailsService {

    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): UserDetails {
        val account = accountRepository.findByEmail(username)

        return AccountAdapter(account)
    }

    fun findAll(): List<AccountDto> {
        return accountRepository.findAllDto()
    }

    fun findByEmail(email: String): AccountDto? {
        return accountRepository.findDtoByEmail(email)
    }
}