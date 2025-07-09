package org.bashtan.MyApps.exceptions.jwt;

public class JwtTokenInvalidException extends RuntimeException {
    public JwtTokenInvalidException(String message) {
        super(message);
    }
}