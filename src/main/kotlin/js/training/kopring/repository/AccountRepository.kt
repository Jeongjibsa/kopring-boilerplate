package js.training.kopring.repository

import js.training.kopring.model.entity.Account
import js.training.kopring.model.entity.PrimaryKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account, PrimaryKey> {

    fun findByEmail(email: String): Account
}