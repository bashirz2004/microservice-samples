package com.zamani.messagepackage;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyMessage implements Serializable {
    private String id;
    private String text; //include whole request (headers and params and url and body) in json stringify format

    @Override
    public String toString() {
        return "MyMessage{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
