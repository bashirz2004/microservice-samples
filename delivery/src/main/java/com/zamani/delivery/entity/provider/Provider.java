package com.zamani.delivery.entity.provider;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zamani.delivery.entity.BaseEntity;
import com.zamani.delivery.vo.AddressVO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "del_provider")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Provider extends BaseEntity {

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "economicCode", length = 10, nullable = false)
    private String economicCode;

    @Embedded
    private AddressVO address;

    @JsonCreator
    public Provider(@NotNull @JsonProperty("name") String name,
                    @NotNull @JsonProperty("economicCode") String economicCode,
                    @NotNull @JsonProperty("address") AddressVO address) {
        super();
        this.name = name;
        if (economicCode.length() != 10)
            throw new IllegalArgumentException("کد اقتصادی باید 10 رقمی باشد.");
        this.economicCode = economicCode;
        this.address = address;
    }


}
