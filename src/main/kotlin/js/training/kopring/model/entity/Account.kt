package js.training.kopring.model.entity

import jakarta.persistence.*
import js.training.kopring.model.enum.Authority
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
@Table(name = "account")
class Account(
    email: String,
    password: String,
    name: String? = null,
    phone: String,
    createdAt: Instant,
    updatedAt: Instant,
) : PrimaryKey() {

    @Column(name = "email", nullable = false)
    var email: String = email
        protected set

    @Column(name = "password", nullable = false)
    var password: String = password
        protected set

    @Column(name = "name")
    var name: String? = name
        protected set

    @Column(name = "phone", nullable = false)
    var phone: String = phone
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
    protected val mutableRoles: MutableSet<AccountRole> = mutableSetOf()
    val roles: Set<AccountRole> get() = mutableRoles.toSet()

    val authorities: Set<Authority> get() = this.roles.map { r -> r.role.authority }.toSet()
}