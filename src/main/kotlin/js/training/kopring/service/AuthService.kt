package js.training.kopring.service

import js.training.kopring.config.security.AccountAdapter
import js.training.kopring.model.enums.AuthStatus
import js.training.kopring.repository.AccountRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val accountRepository: AccountRepository,
) : UserDetailsService {

    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): UserDetails {
        val account = accountRepository.findByEmail(username)
            ?: throw UsernameNotFoundException(AuthStatus.USER_NOT_FOUND.describe)

        return AccountAdapter(account = account)
    }
}