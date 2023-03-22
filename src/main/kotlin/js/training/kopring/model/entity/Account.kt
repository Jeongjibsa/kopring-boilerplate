package js.training.kopring.model.entity

import jakarta.persistence.*
import js.training.kopring.model.dto.payload.request.SignUpRequest
import js.training.kopring.model.enums.Authority
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
@Table(name = "account")
class Account(

    @Column(name = "email", nullable = false)
    var email: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "name")
    var name: String?,

    @Column(name = "phone", nullable = false)
    var phone: String,

    @CreationTimestamp
    @Column(name = "created_at")
    val createdAt: Instant = Instant.now(),

    @UpdateTimestamp
    @Column(name = "updated_at")
    val updatedAt: Instant = Instant.now(),

    @OneToMany(
        mappedBy = "account",
        cascade = [CascadeType.PERSIST, CascadeType.MERGE],
        orphanRemoval = true
    )
    val roles: MutableSet<AccountRole> = mutableSetOf()
) : PrimaryKey() {

    constructor(signUp: SignUpRequest) : this(
        email = signUp.email,
        password = signUp.password,
        name = signUp.name,
        phone = signUp.phone
    )

    val authorities: Set<Authority> get() = this.roles.map { r -> r.role.authority }.toSet()

    fun getRoleString(): String = this.authorities.joinToString(", ")
}