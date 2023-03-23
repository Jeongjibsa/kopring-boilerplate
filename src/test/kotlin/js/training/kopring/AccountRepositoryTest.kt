package js.training.kopring

import js.training.kopring.model.entity.Account
import js.training.kopring.model.mapper.AccountMapper
import js.training.kopring.repository.AccountRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@DataJpaTest(showSql = true)
@ActiveProfiles("dev")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTest(
    @Autowired val accountRepository: AccountRepository
) {
    private val accountMapper: AccountMapper = Mappers.getMapper(AccountMapper::class.java)

    @Test
    fun test_find_account_by_email() {
        val email = "test@gmail.com"
        val account: Account? = accountRepository.findByEmail(email)

        Assertions.assertEquals(null, account)
    }

    @Test
    fun test_find_all_account() {
        val accounts: List<Account> = accountRepository.findAll()

        Assertions.assertEquals(true, accounts.stream().map { account -> accountMapper.toDto(account) }.toList())
    }
}