package js.training.kopring.service

import js.training.kopring.model.dto.AccountDto
import js.training.kopring.model.dto.SignUpDto
import js.training.kopring.model.entity.Account
import js.training.kopring.model.mapper.AccountMapper
import js.training.kopring.model.security.AccountAdapter
import js.training.kopring.repository.AccountRepository
import org.mapstruct.factory.Mappers
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) : UserDetailsService {
    private val accountMapper: AccountMapper = Mappers.getMapper(AccountMapper::class.java)

    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): UserDetails {
        val account = accountRepository.findByEmail(username)

        return AccountAdapter(account)
    }

    fun findAll(): List<AccountDto> {
        val accounts = accountRepository.findAll()
        return accounts.map { account -> accountMapper.toDto(account) }.toList()
    }

    fun findByEmail(email: String): AccountDto? {
        val account = accountRepository.findByEmail(email)
        return accountMapper.toDto(account)
    }

    fun save(signUp: SignUpDto) {
        signUp.password = passwordEncoder.encode(signUp.password)
        val account = Account(signUp)
//        account.roles.add()

        accountRepository.save(account)
    }
}