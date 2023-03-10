package js.training.kopring.repository

import js.training.kopring.model.dto.AccountDto
import js.training.kopring.model.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface AccountRepository : JpaRepository<Account, Long> {

    fun findByEmail(email: String): Account

    @Transactional(readOnly = true)
    @Query(
        value = "SELECT new js.training.kopring.model.dto.AccountDto(" +
                "   a.email," +
                "   a.name," +
                "   a.phone" +
                ")" +
                "FROM Account a " +
                "WHERE a.email = :email"
    )
    fun findDtoByEmail(email: String): AccountDto?

    @Transactional(readOnly = true)
    @Query(
        value = "SELECT new js.training.kopring.model.dto.AccountDto(" +
                "   a.email," +
                "   a.name," +
                "   a.phone" +
                ")" +
                "FROM Account a"
    )
    fun findAllDto(): List<AccountDto>
}