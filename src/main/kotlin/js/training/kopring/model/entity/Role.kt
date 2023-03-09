package js.training.kopring.model.entity

import jakarta.persistence.*
import js.training.kopring.model.enum.Authority
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant


@Entity
@Table(name = "role")
class Role(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "authority")
    @Enumerated
    val authority: Authority,

    @CreationTimestamp
    @Column(name = "created_at")
    var createdAt: Instant,

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: Instant,

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    val users: Set<Account> = emptySet()
)