package be.albatroz.javacase.resource;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AddressResource extends BaseResource<Long> {

    @NotEmpty
    private String street;
    @NotEmpty
    private String number;
    @NotEmpty
    private String zipCode;
    @NotEmpty
    private String municipality;
    @NotEmpty
    private String country;

}
