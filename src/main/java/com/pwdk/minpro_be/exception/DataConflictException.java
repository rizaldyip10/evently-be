package com.pwdk.minpro_be.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@EqualsAndHashCode(callSuper = false)
@Setter
@Data
public class DataConflictException extends RuntimeException {
    public DataConflictException(String message) {
        super(message);
    }
}
