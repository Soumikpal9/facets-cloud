package com.facets.cloud.virtualnode.exception;

import com.facets.cloud.virtualnode.enums.ErrorPriority;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

    private HttpStatus httpStatus;

    private String errorMessage;

    private ErrorPriority errorPriority;

    public ApiException(ApiException ex) {
        this(ex.getErrorMessage(), ex.getHttpStatus());
    }

    public ApiException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ApiException(HttpStatus httpStatus) {
        this(httpStatus.getReasonPhrase(), httpStatus);
    }

    public ApiException(String message, HttpStatus httpStatus) {
        this(message, httpStatus, null);
    }


    public ApiException(ErrorPriority errorPriority, ApiException ex) {
        this(ex.getErrorMessage(), ex.getHttpStatus(), errorPriority);
    }

    public ApiException(String message, HttpStatus httpStatus, ErrorPriority errorPriority) {
        super(message);
        this.errorMessage = message;
        this.httpStatus = httpStatus == null ? HttpStatus.INTERNAL_SERVER_ERROR : httpStatus;
        this.errorPriority = errorPriority;
    }

    public ApiException(ErrorPriority errorPriority, Exception ex) {
        super(ex);
        this.errorPriority = errorPriority;
        this.errorMessage = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
