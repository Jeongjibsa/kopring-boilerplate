package js.training.kopring.model.entity

import jakarta.persistence.*
import js.training.kopring.model._enum.Authority
import js.training.kopring.model.dto.SignUpDto
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
    var createdAt: Instant = Instant.now()
        protected set

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: Instant = Instant.now()
        protected set

    @OneToMany(mappedBy = "account", cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    val roles: MutableSet<AccountRole> = mutableSetOf()

    val authorities: Set<Authority> get() = this.roles.map { r -> r.role.authority }.toSet()

    val roleString: String get() = this.authorities.map { author -> author.name }.joinToString { ", " }

    constructor(signUp: SignUpDto) : this(signUp.email, signUp.password, signUp.name, signUp.phone)
}