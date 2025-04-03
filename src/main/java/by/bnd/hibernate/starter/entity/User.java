package by.bnd.hibernate.starter.entity;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamedQuery(name = "findUserByNameAndCompany", query = """
        select u from User u
        left join u.company c
        where u.personalInfo.firstname = :firstname
        and c.name = :company
        """)
@FetchProfile(name = "withCompany", fetchOverrides = {
        @FetchProfile.FetchOverride(entity = User.class, association = "company", mode = FetchMode.JOIN)
})
@EqualsAndHashCode(of = "username")
@ToString(exclude = {"company", "userChats", "userPayments"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "users", schema = "public")
@Inheritance(strategy = InheritanceType.JOINED)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "Users")
public class User implements Comparable<User>, BaseEntity<Long> {
    @Id
    @GeneratedValue(generator = "user_gen", strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "user_gen", sequenceName = "users_id_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Embedded
    @AttributeOverride(name = "birthDate", column = @Column(name = "birth_date"))
    private PersonalInfo personalInfo;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private Profile profile;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<UserChat> userChats = new ArrayList<>();

    //@BatchSize(size = 7)
    @Builder.Default
    @OneToMany(mappedBy = "receiver")
    private List<Payment> userPayments = new ArrayList<>();


    public String fullName() {
        return personalInfo.getFirstname() + " " + personalInfo.getLastname();
    }

    public long getAge(){
        return personalInfo.getAge();
    }

    @Override
    public int compareTo(User o) {
        return username.compareTo(o.username);
    }
}

