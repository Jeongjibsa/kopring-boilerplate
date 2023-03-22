package js.training.kopring.model.entity

import jakarta.persistence.*
import js.training.kopring.model.enums.Authority
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant


@Entity
@Table(name = "role")
class Role(

    @Column(name = "authority")
    @Enumerated(value = EnumType.STRING)
    val authority: Authority,


    @CreationTimestamp
    @Column(name = "created_at")
    val createdAt: Instant = Instant.now(),


    @UpdateTimestamp
    @Column(name = "updated_at")
    val updatedAt: Instant = Instant.now(),


    @OneToMany(mappedBy = "role", cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    val accounts: MutableSet<AccountRole> = mutableSetOf()
) : PrimaryKey()