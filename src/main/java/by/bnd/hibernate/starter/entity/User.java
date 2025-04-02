package by.bnd.hibernate.starter.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamedQuery(name = "findUserByNameAndCompany", query = """
        select u from User u
        left join u.company c
        where u.personalInfo.firstname = :firstname
        and c.name = :company
        """)
@EqualsAndHashCode(of = "username")
@ToString(exclude = {"company", "userChats"})
//@ToString(exclude = {"company", "profile", "userChats"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "users", schema = "public")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
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

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Payment> userPayments = new ArrayList<>();


    public String fullName() {
        return personalInfo.getFirstname() + " " + personalInfo.getLastname();
    }

    public long getAge(){
        return personalInfo.getAge();
    }

}

