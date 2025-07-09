//package org.bashtan.MyApps.api.exceptions.handler;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.security.SignatureException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class JwtExceptionHandler {
//
//    @ExceptionHandler(SignatureException.class)
//    public ResponseEntity<String> handleInvalidSignature(SignatureException e) {
//        return new ResponseEntity<>("Invalid JWT signature: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(ExpiredJwtException.class)
//    public ResponseEntity<String> handleExpiredJwt(ExpiredJwtException e) {
//        return new ResponseEntity<>("JWT token has expired: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(MalformedJwtException.class)
//    public ResponseEntity<String> handleMalformedJwt(MalformedJwtException e) {
//        return new ResponseEntity<>("Invalid JWT token: " + e.getMessage(), HttpStatus.BAD_REQUEST);
//    }
//}
