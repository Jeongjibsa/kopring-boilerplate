package js.training.kopring.repository

import js.training.kopring.model.entity.RefreshToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository : CrudRepository<RefreshToken, String> {

    fun findByToken(token: String): RefreshToken?
}