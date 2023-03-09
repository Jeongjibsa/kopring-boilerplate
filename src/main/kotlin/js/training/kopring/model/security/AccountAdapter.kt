package js.training.kopring.model.security

import js.training.kopring.model.entity.Account
import js.training.kopring.model.entity.Role
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User

class AccountAdapter(
    account: Account
) : User(account.email, account.password, authorities(account.roles)) {

    companion object {
        fun authorities(roles: Set<Role>): Collection<GrantedAuthority> =
            roles.map { role -> SimpleGrantedAuthority(role.authority.name) }.toSet()
    }

}