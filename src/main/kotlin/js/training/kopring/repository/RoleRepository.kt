package js.training.kopring.repository

import js.training.kopring.model.entity.PrimaryKey
import js.training.kopring.model.entity.Role
import js.training.kopring.model.enums.Authority
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : JpaRepository<Role, PrimaryKey> {

    fun findByAuthority(authority: Authority): Role?
}