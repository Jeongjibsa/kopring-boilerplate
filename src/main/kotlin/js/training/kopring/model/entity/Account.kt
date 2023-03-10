package js.training.kopring.model.entity

import jakarta.persistence.*
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
    roles: Set<Role>
) : PrimaryKey() {

    @Column(name = "email")
    var email: String = email
        protected set

    @Column(name = "password")
    var password: String = password
        protected set

    @Column(name = "name")
    var name: String? = name
        protected set

    @Column(name = "phone")
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "account_role",
        joinColumns = [JoinColumn(name = "account_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    protected val mutableRoles: MutableSet<Role> = roles.toMutableSet()
    val roles: Set<Role> get() = mutableRoles.toSet()
}