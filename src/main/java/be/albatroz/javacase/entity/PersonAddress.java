package be.albatroz.javacase.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("PERSON")
public class PersonAddress extends Address {

    @OneToOne(targetEntity = Person.class, mappedBy = "address")
    private Person person;

}
