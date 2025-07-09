package org.bashtan.MyApps.exceptions.jwt;

public class JwtTokenMissingException extends RuntimeException {
    public JwtTokenMissingException(String message) {
        super(message);
    }
}