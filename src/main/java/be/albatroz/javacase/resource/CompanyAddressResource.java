package be.albatroz.javacase.resource;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CompanyAddressResource extends AddressResource {

    private boolean hq;

}
