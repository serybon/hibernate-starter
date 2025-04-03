package by.bnd.hibernate.starter.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
//@ToString(exclude = "receiver")
@Entity
@Table(name = "payment", schema = "public")
public class Payment implements BaseEntity<Long> {
    @Id
    @GeneratedValue(generator = "payment_gen", strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer amount;

    @Version
    private Long version;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;
}