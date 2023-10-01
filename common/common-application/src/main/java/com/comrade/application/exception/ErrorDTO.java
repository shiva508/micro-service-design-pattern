package com.comrade.application.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class ErrorDTO implements Serializable {

    private final String code;

    private final String message;

}
