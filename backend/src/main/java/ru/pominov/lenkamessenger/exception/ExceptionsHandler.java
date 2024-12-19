package ru.pominov.lenkamessenger.exception;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.pominov.lenkamessenger.exception.model.ApiError;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionsHandler {

    private static final Logger log = LoggerFactory.getLogger(ExceptionsHandler.class);

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequest(final MethodArgumentNotValidException ex) {
        return badRequest(ex);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequest(final ConstraintViolationException ex) {
        return badRequest(ex);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequest(final HttpMessageNotReadableException ex) {
        return badRequest(ex);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleIllegalArgumentException(final IllegalArgumentException validException) {
        return badRequest(validException);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequest(final MethodArgumentTypeMismatchException mismatchException) {
        return badRequest(mismatchException);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequest(final MissingServletRequestParameterException missingParameterException) {
        return badRequest(missingParameterException);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflict(final DataIntegrityViolationException violationException) {
        return conflict(violationException);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleNotFound(final BadRequestException badRequestException) {
        return badRequest(badRequestException);
    }

    private ApiError badRequest(final Exception e) {
        log.warn("400 {}", e.getMessage());

        ApiError error = new ApiError();
        error.setStatus(HttpStatus.BAD_REQUEST);
        error.setReason("Incorrectly made request.");
        error.setMessage(e.getMessage());
        error.setTimestamp(LocalDateTime.now().toString());

        return error;
    }

    private ApiError conflict(final Exception e) {
        log.warn("409 {}", e.getMessage());

        ApiError error = new ApiError();
        error.setStatus(HttpStatus.CONFLICT);
        error.setReason("Integrity constraint has been violated.");
        error.setMessage(e.getMessage());
        error.setTimestamp(LocalDateTime.now().toString());

        return error;
    }
}
