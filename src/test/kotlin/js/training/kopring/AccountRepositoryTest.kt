package js.training.kopring

import js.training.kopring.model.dto.AccountDto
import js.training.kopring.repository.AccountRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
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

    @Test
    fun test_find_account_by_email() {
        val email = "test@gmail.com"
        val account: AccountDto? = accountRepository.findDtoByEmail(email)

        Assertions.assertEquals(null, account)
    }

    @Test
    fun test_find_all_account() {
        val account: List<AccountDto> = accountRepository.findAllDto()

        Assertions.assertEquals(true, account.isEmpty())
    }
}