package by.bnd.hibernate.starter.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
@Setter
@Getter
@MappedSuperclass
public abstract class AuditableEntity<T extends Serializable> implements BaseEntity<T>{
    private Instant createdAt;

    private String createdBy;
}
