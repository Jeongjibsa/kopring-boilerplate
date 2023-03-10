package js.training.kopring.model.entity

import jakarta.persistence.*


@Entity
@Table(name = "account_role")
class AccountRole(
    account: Account,
    role: Role
) : PrimaryKey() {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, name = "account_id")
    var account: Account = account
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, name = "role_id")
    var role: Role = role
        protected set
}
