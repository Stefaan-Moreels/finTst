package be.albatroz.javacase.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@Getter
@Setter
@ToString
@MappedSuperclass
@EqualsAndHashCode
public abstract class BaseEntity {

    private static final long serialVersionUID = -5554308939380869754L;
    @Id
    @GeneratedValue
    @Nullable
    private Long id;

    @Transient
    public boolean isNew() {
        return null == this.getId();
    }

}
