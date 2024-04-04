package ch.bbcag.nfl_backend.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

public class FailedValidationException extends ResponseStatusException {
    private final Map<String, List<String>> errors;

    public FailedValidationException(Map<String, List<String>> errors) {
        super(HttpStatus.BAD_REQUEST, "Validation failed");
        this.errors = errors;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }
}
