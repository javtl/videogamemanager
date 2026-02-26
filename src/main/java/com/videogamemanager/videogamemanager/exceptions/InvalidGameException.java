package com.videogamemanager.videogamemanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidGameException extends RuntimeException {
    public InvalidGameException(String message) {
        super(message);
    }
}