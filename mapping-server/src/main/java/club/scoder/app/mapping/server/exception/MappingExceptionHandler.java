package club.scoder.app.mapping.server.exception;

import club.scoder.app.mapping.common.http.Response;
import club.scoder.app.mapping.common.http.code.FailureCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception handler for mapping server.
 *
 * @author H
 */
@RestControllerAdvice
@Slf4j
public class MappingExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    private Response<Void> handleException(Exception e) {
        log.error("exception", e);
        return Response.error();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    private Object handleRuntimeException(RuntimeException e) {
        return Response.failure(FailureCode.FAILURE, e.getMessage());
    }

}
