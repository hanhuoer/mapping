package club.scoder.app.mapping.common.http.code;

/**
 * @author H
 * @link https://github.com/hanhuoer/Jusic-serve
 */
public enum FailureCode implements Code {

    /**
     * default
     */
    FAILURE("40000", "failure"),
    UNAUTHORIZED("40001", "UNAUTHORIZED"),
    JWT_INVALID_TOKEN("40002", "JWT_INVALID_TOKEN"),
    JWT_EXPIRED_TOKEN("40003", "JWT_EXPIRED_TOKEN"),
    JWT_WRONG_TOKEN("40004", "JWT_WRONG_TOKEN"),
    JWT_UNAUTHORIZED_TOKEN("40005", "JWT_UNAUTHORIZED_TOKEN"),
    JWT_NOT_ALLOWED_REFRESH_TOKEN("40006", "JWT_NOT_ALLOWED_REFRESH_TOKEN");

    private String code;
    private String message;

    FailureCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.message;
    }
}
