package js.training.kopring.model.security

import js.training.kopring.model.entity.Account
import js.training.kopring.model._enum.Authority
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User

class AccountAdapter(
    account: Account
) : User(account.email, account.password, authorities(account.authorities)) {

    companion object {
        fun authorities(roles: Set<Authority>): Collection<GrantedAuthority> =
            roles.map { role -> SimpleGrantedAuthority(role.name) }.toSet()
    }

}