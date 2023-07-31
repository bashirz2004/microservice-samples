package com.zamani.delivery.configs.exception;

import lombok.*;

import java.util.LinkedHashMap;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExceptionResponse {
    public String message;
    public int code;
}
