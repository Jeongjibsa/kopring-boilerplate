package js.training.kopring

import js.training.kopring.model.dto.AccountDto
import js.training.kopring.service.AccountService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles(profiles = ["dev"])
class KopringApplicationTests(

    @Autowired
    private val accountService: AccountService
) {

    @Test
    fun contextLoads() {
    }

    @Test
    fun getAccounts() {
        val accounts: List<AccountDto> = accountService.findAll()
        println(accounts)
    }

    @Test
    fun findAccountByEmail() {
        val email = "test@gmail.com"
        val account: AccountDto? = accountService.findByEmail(email)
        println(account)
    }
}
