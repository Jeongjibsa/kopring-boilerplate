package js.training.kopring.model.entity

import jakarta.persistence.*
import js.training.kopring.model._enum.Authority
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant


@Entity
@Table(name = "role")
class Role(
    authority: Authority,
) : PrimaryKey() {

    @Column(name = "authority")
    @Enumerated
    var authority: Authority = authority
        protected set

    @CreationTimestamp
    @Column(name = "created_at")
    var createdAt: Instant = Instant.now()
        protected set

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: Instant = Instant.now()
        protected set

    @OneToMany(mappedBy = "role", cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    val accounts: MutableSet<AccountRole> = mutableSetOf()
}