package be.albatroz.javacase.resource;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = {"id"})
public abstract class BaseResource<P> implements Serializable {

    protected P id;

}