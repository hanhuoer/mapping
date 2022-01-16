package club.scoder.app.mapping.common.http;

import club.scoder.app.mapping.common.http.code.Code;

import java.io.Serializable;

/**
 * @author H
 * @link https://github.com/hanhuoer/Jusic-serve
 */
public interface Response<T> extends Serializable {

    /**
     * <p>默认方式</p>
     * {
     * "code": "20000",
     * "message": "success"
     * }
     *
     * @return Response
     */
    static Response<Void> success() {
        return DefaultResponse.success();
    }

    /**
     * <p>默认的成功方式，外加一个 data </p>
     * {
     * "code": "20000",
     * "message": "success",
     * "data": data
     * }
     *
     * @param data data
     * @return Response
     */
    static <T> Response<T> success(T data) {
        return DefaultResponse.success(data);
    }

    static <T> Response<T> success(T data, String message) {
        return DefaultResponse.success(data, message);
    }

    /**
     * <p>自定义，只返回 code message</p>
     * {
     * "code": code,
     * "message": message
     * }
     *
     * @param code    code
     * @param message message
     * @return Response
     */
    static <T> Response<T> success(Code code, String message) {
        return DefaultResponse.success(code, message);
    }

    /**
     * <p>自定义，只返回 code，data</p>
     * {
     * "code": code,
     * "message": data
     * }
     *
     * @param code code
     * @param data data
     * @return Response
     */
    static <T> Response<T> success(Code code, T data) {
        return DefaultResponse.success(code, data);
    }

    /**
     * <p>自定义，code，message，data 全自定义</p>
     * {
     * "code": code,
     * "message": message,
     * "data": data
     * }
     *
     * @param code    code
     * @param message message
     * @param data    data
     * @return Response
     */
    static <T> Response<T> success(Code code, String message, T data) {
        return DefaultResponse.success(code, message, data);
    }

    /**
     * <p>默认失败结果</p>
     * {
     * "code": "40000",
     * "message": "failure"
     * }
     *
     * @param <T> -
     * @return -
     */
    static <T> Response<T> failure() {
        return DefaultResponse.failure();
    }

    /**
     * failure
     *
     * @param data -
     * @param <T>  -
     * @return -
     */
    static <T> Response<T> failure(T data) {
        return DefaultResponse.failure(data);
    }

    static <T> Response<T> failure(T data, String message) {
        return DefaultResponse.failure(data, message);
    }

    /**
     * failure
     *
     * @param code    code
     * @param message message
     * @param <T>     data
     * @return -
     */
    static <T> Response<T> failure(Code code, String message) {
        return DefaultResponse.failure(code, message);
    }

    /**
     * failure
     *
     * @param code code
     * @param data data
     * @param <T>  data
     * @return -
     */
    static <T> Response<T> failure(Code code, T data) {
        return DefaultResponse.failure(code, data);
    }

    /**
     * failure
     *
     * @param code    code
     * @param message message
     * @param data    data
     * @param <T>     data
     * @return -
     */
    static <T> Response<T> failure(Code code, String message, T data) {
        return DefaultResponse.failure(code, message, data);
    }

    /**
     * <p>默认失败结果</p>
     * {
     * "code": "50000",
     * "message": "error"
     * }
     *
     * @param <T> -
     * @return -
     */
    static <T> Response<T> error() {
        return DefaultResponse.error();
    }

    /**
     * error
     *
     * @param data -
     * @param <T>  -
     * @return -
     */
    static <T> Response<T> error(T data) {
        return DefaultResponse.error(data);
    }

    static <T> Response<T> error(T data, String message) {
        return DefaultResponse.error(data, message);
    }

    /**
     * error
     *
     * @param code    code
     * @param message message
     * @param <T>     data
     * @return -
     */
    static <T> Response<T> error(Code code, String message) {
        return DefaultResponse.error(code, message);
    }

    /**
     * error
     *
     * @param code code
     * @param data data
     * @param <T>  data
     * @return -
     */
    static <T> Response<T> error(Code code, T data) {
        return DefaultResponse.error(code, data);
    }

    /**
     * error
     *
     * @param code    code
     * @param message message
     * @param data    data
     * @param <T>     data
     * @return -
     */
    static <T> Response<T> error(Code code, String message, T data) {
        return DefaultResponse.error(code, message, data);
    }

    /**
     * get code
     *
     * @return -
     */
    String getCode();

    /**
     * get message
     *
     * @return -
     */
    String getMessage();

    /**
     * get data
     *
     * @return -
     */
    T getData();

}
