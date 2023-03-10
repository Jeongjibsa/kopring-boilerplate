package js.training.kopring.model.entity

import jakarta.persistence.*
import js.training.kopring.model.enum.Authority
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant


@Entity
@Table(name = "role")
class Role(
    authority: Authority,
    createdAt: Instant,
    updatedAt: Instant,
) : PrimaryKey() {

    @Column(name = "authority")
    @Enumerated
    var authority: Authority = authority
        protected set

    @CreationTimestamp
    @Column(name = "created_at")
    var createdAt: Instant = createdAt
        protected set

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: Instant = updatedAt
        protected set

    @ElementCollection
    @CollectionTable(name = "account_role")
    protected val mutableAccounts: MutableSet<AccountRole> = mutableSetOf()
    val accounts: Set<AccountRole> get() = mutableAccounts.toSet()
}