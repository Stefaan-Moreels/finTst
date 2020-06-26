package be.albatroz.javacase.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "address")
@ToString(callSuper = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@EqualsAndHashCode(callSuper = true)
public class Address extends BaseEntity {

    @Column
    private String street;
    @Column
    private String number;

    @Column
    private String zipCode;
    @Column
    private String municipality;

    @Column
    private String country;

}
