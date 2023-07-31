package com.zamani.delivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "create_date", nullable = false)
    private Timestamp createDate = new Timestamp(System.currentTimeMillis());

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
