package com.zamani.messagepackage;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyHttpResponseMessage implements Serializable {
    private MyHttpRequestMessage request;
    private String text; //include whole response (headers and body) in json stringify format
}
