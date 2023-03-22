package js.training.kopring.model.entity

import jakarta.persistence.*


@Entity
@Table(name = "account_role")
class AccountRole(

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinColumn(nullable = false, name = "account_id")
    val account: Account,

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinColumn(nullable = false, name = "role_id")
    val role: Role,
) : PrimaryKey()
