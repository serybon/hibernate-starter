package by.bnd.hibernate.starter.entity;

import by.bnd.hibernate.starter.converter.BirthdayConverter;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class PersonalInfo {

    private String firstname;
    private String lastname;
    @Convert(converter = BirthdayConverter.class)
    @Column(name = "birth_date")
    private Birthday birthDate;

    public long getAge(){
        return ChronoUnit.YEARS.between(birthDate.birthDate(), LocalDate.now());
    }
}
