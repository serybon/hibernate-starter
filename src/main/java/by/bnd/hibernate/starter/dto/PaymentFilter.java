package by.bnd.hibernate.starter.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PaymentFilter {
    String firstname;
    String lastname;
}
