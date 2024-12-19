package ru.pominov.lenkamessenger.exception.model;

import org.springframework.http.HttpStatus;

import java.util.Objects;

public class ApiError {

    public ApiError() {}

    public ApiError(HttpStatus status, String reason, String message, String timestamp) {
        this.status = status;
        this.reason = reason;
        this.message = message;
        this.timestamp = timestamp;
    }

    private HttpStatus status;
    private String reason;
    private String message;
    private String timestamp;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiError apiError = (ApiError) o;
        return status == apiError.status && Objects.equals(reason, apiError.reason) && Objects.equals(message, apiError.message) && Objects.equals(timestamp, apiError.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, reason, message, timestamp);
    }
}