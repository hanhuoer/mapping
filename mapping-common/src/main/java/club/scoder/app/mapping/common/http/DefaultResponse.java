package club.scoder.app.mapping.common.http;


import club.scoder.app.mapping.common.http.code.Code;
import club.scoder.app.mapping.common.http.code.ErrorCode;
import club.scoder.app.mapping.common.http.code.FailureCode;
import club.scoder.app.mapping.common.http.code.SuccessCode;

import java.io.Serializable;

/**
 * @author H
 * @link https://github.com/hanhuoer/Jusic-serve
 */
public class DefaultResponse<T> implements Response<T>, Serializable {

    private static final long serialVersionUID = -5508341719984417455L;

    private String code;
    private String message;
    private T data;

    public DefaultResponse() {
    }

    private DefaultResponse(Code code, T data) {
        this.code = code.code();
        this.data = data;
    }

    private DefaultResponse(Code code, String message) {
        this.code = code.code();
        this.message = message;
    }

    private DefaultResponse(Code code, String message, T data) {
        this.code = code.code();
        this.message = message;
        this.data = data;
    }

    static DefaultResponse<Void> success() {
        return new DefaultResponse<>(SuccessCode.SUCCESS, SuccessCode.SUCCESS.message());
    }

    static <T> DefaultResponse<T> success(T data) {
        return new DefaultResponse<>(SuccessCode.SUCCESS, SuccessCode.SUCCESS.message(), data);
    }

    static <T> DefaultResponse<T> success(T data, String message) {
        return new DefaultResponse<>(SuccessCode.SUCCESS, message, data);
    }

    static <T> DefaultResponse<T> success(Code code, String message) {
        return new DefaultResponse<>(code, message);
    }

    static <T> DefaultResponse<T> success(Code code, T data) {
        return new DefaultResponse<>(code, data);
    }

    static <T> DefaultResponse<T> success(Code code, String message, T data) {
        return new DefaultResponse<>(code, message, data);
    }

    static <T> DefaultResponse<T> failure() {
        return new DefaultResponse<>(FailureCode.FAILURE, FailureCode.FAILURE.message());
    }

    static <T> DefaultResponse<T> failure(T data) {
        return new DefaultResponse<>(FailureCode.FAILURE, FailureCode.FAILURE.message(), data);
    }

    static <T> DefaultResponse<T> failure(T data, String message) {
        return new DefaultResponse<>(FailureCode.FAILURE, message, data);
    }

    static <T> DefaultResponse<T> failure(Code code, String message) {
        return new DefaultResponse<>(code, message);
    }

    static <T> DefaultResponse<T> failure(Code code, T data) {
        return new DefaultResponse<>(code, data);
    }

    static <T> DefaultResponse<T> failure(Code code, String message, T data) {
        return new DefaultResponse<>(code, message, data);
    }

    static <T> DefaultResponse<T> error() {
        return new DefaultResponse<>(ErrorCode.CODE, ErrorCode.CODE.message());
    }

    static <T> DefaultResponse<T> error(T data) {
        return new DefaultResponse<>(ErrorCode.CODE, ErrorCode.CODE.message(), data);
    }

    static <T> DefaultResponse<T> error(T data, String message) {
        return new DefaultResponse<>(ErrorCode.CODE, message, data);
    }

    static <T> DefaultResponse<T> error(Code code, String message) {
        return new DefaultResponse<>(code, message);
    }

    static <T> DefaultResponse<T> error(Code code, T data) {
        return new DefaultResponse<>(code, data);
    }

    static <T> DefaultResponse<T> error(Code code, String message, T data) {
        return new DefaultResponse<>(code, message, data);
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public T getData() {
        return this.data;
    }

}
