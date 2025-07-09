
//    @ExceptionHandler(NoHandlerFoundException.class)
//    public ResponseEntity<String> handleNotFound(NoHandlerFoundException ex) {
//        return new ResponseEntity<>("Endpoint not found", HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
//        Map<String, String> response = new HashMap<>();
//        response.put("error", ex.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }

//    @ExceptionHandler(MissingServletRequestParameterException.class)
//    public ResponseEntity<Map<String, String>> handleMissingParamException(MissingServletRequestParameterException ex) {
//        Map<String, String> response = new HashMap<>();
//        response.put("error", "Required parameter '" + ex.getParameterName() + "' is missing.");
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex) {
//        Map<String, String> response = new HashMap<>();
//        response.put("Authentication failed: {}",ex.getMessage());
//        return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
//    }
