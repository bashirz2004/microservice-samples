package com.zamani.messagepackage;

import lombok.*;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;
import java.util.LinkedHashMap;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyHttpResponseMessage implements Serializable {
    private MyHttpRequestMessage request;
    private String text; //include whole response (headers and body) in json stringify format

    @Override
    public String toString() {
        return "MyHttpResponseMessage{" +
                "request=" + request.toString() +
                ", text='" + text + '\'' +
                '}';
    }
}
