package com.jyora.cs.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseEntity {

    private String message;
    private String statusCode;
    private Object data;

    public ResponseEntity(String message, String statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
