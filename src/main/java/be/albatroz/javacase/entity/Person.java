package be.albatroz.javacase.entity;

import be.albatroz.javacase.common.PersonType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "person", indexes = {
        @Index(name = "idx_person_name", columnList = "first_name,name")
})
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Person extends BaseEntity {

    @OneToOne(targetEntity = PersonAddress.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", foreignKey = @ForeignKey(name = "FK_person_address"))
    private PersonAddress address;

    @ManyToMany(targetEntity = Company.class)
    @JoinTable(name = "person_company", joinColumns = {
            @JoinColumn(name = "person_id", foreignKey = @ForeignKey(name = "FK_person_company_person"))
    },
            inverseJoinColumns = {
                    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "FK_person_company_company"))
            })
    private Set<Company> companies = new HashSet<>();

    @Column
    private String name;
    @Column(name = "first_name")
    private String firstName;

    @Column
    @Enumerated(EnumType.STRING)
    private PersonType type;
    @Column
    private String vat;


    public void addCompany(Company company) {
        if (companies == null) {
            companies = new HashSet<>();
        }
        companies.add(company);
    }

    public void removeCompany(Company company) {
        if (companies == null) {
            companies = new HashSet<>();
        } else {
            companies.remove(company);
        }
    }


}
