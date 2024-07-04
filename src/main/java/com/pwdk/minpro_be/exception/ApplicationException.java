package com.pwdk.minpro_be.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
public class ApplicationException extends RuntimeException {

    private final List<String> errors;
    private HttpStatus httpStatus;
    private List<String> error;
    private Objects data;

    public ApplicationException(String message){
        this(HttpStatus.BAD_REQUEST, message);
    }

    public ApplicationException(HttpStatus httpStatus, String message) {
        this(httpStatus, message, Collections.singletonList(message), null);
    }

    public ApplicationException(HttpStatus httpStatus, String message, List<String> errors, Object data) {
        super(message);
        this.httpStatus = httpStatus;
        this.errors = errors;
        this.data = (Objects) data;
    }


}
