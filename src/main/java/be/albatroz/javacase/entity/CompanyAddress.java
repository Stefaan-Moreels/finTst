package be.albatroz.javacase.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@ToString(callSuper = true, exclude = {"company"})
@EqualsAndHashCode(callSuper = true, exclude = {"company"})
@DiscriminatorValue("COMPANY")
public class CompanyAddress extends Address implements Serializable {

    @ManyToOne(targetEntity = Company.class)
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "FK_address_company"))
    private Company company;

    @Column
    private boolean hq = false;

}
