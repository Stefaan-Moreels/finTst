package be.albatroz.javacase.entity;

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
@Table(name = "company", indexes = {
        @Index(name = "idx_company_name", columnList = "name")
})
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Company extends BaseEntity {

    @ManyToMany(targetEntity = Person.class, mappedBy = "companies")
    private Set<Person> people = new HashSet<>();

    @OneToMany(targetEntity = CompanyAddress.class, mappedBy = "company", cascade = CascadeType.REMOVE)
    private Set<CompanyAddress> addresses = new HashSet<>();

    @Column
    private String name;

    @Column
    private String vat;


}
