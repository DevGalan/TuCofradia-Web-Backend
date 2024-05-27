package com.devgalan.tucofradia.tool;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseWithMessage<T> {

    public ResponseWithMessage(String message) {
        this.message = message;
        data = null;
    }

    private String message;
    private T data;

}
