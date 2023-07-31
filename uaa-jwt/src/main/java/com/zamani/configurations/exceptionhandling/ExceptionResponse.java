package com.zamani.configurations.exceptionhandling;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExceptionResponse {
    public String message;
    public int code;
}
