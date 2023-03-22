package js.training.kopring.repository

import js.training.kopring.model.entity.Account
import js.training.kopring.model.entity.PrimaryKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account, PrimaryKey> {

    @Query(value = "" +
            "SELECT a " +
            "FROM Account a " +
            "         JOIN FETCH AccountRole ar on a = ar.account " +
            "         LEFT OUTER JOIN Role r on ar.role = r")
    fun findAllByFetch(): List<Account>

    fun findByEmail(email: String): Account?

    fun existsByEmail(email: String): Boolean

    fun deleteByEmail(email: String)
}