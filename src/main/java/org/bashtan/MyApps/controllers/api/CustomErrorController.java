package org.bashtan.MyApps.controllers.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<String> handleError(HttpServletRequest request) {
        Object status = request.getAttribute("javax.servlet.error.status_code");

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return new ResponseEntity<>("Endpoint not found", HttpStatus.NOT_FOUND);
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);

    }
}