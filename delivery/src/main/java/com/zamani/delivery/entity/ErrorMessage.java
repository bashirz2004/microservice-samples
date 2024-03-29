package com.zamani.delivery.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorMessage {
    private String message = "";
    private int severity;
    private String code = "";
}
